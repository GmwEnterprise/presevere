/**
 * Copyright 2009-2020 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.reflection;

import org.apache.ibatis.reflection.invoker.*;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.lang.reflect.*;
import java.text.MessageFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * This class represents a cached set of class definition information that
 * allows for easy mapping between property names and getter/setter methods.
 *
 * @author Clinton Begin
 */
public class Reflector {

  private final Class<?> type;
  private final String[] readablePropertyNames; // 可读属性
  private final String[] writablePropertyNames; // 可写属性
  private final Map<String, Invoker> setMethods = new HashMap<>(); // 写方法
  private final Map<String, Invoker> getMethods = new HashMap<>(); // 读方法
  private final Map<String, Class<?>> setTypes = new HashMap<>(); // 写方法入参类型
  private final Map<String, Class<?>> getTypes = new HashMap<>(); // 读方法出参类型
  private Constructor<?> defaultConstructor; // 默认无参构造函数

  // 记录了所有属性名称的集合
  private Map<String, String> caseInsensitivePropertyMap = new HashMap<>();

  public Reflector(Class<?> clazz) {
    type = clazz;
    addDefaultConstructor(clazz);
    addGetMethods(clazz); // 处理clazz中的getter,填充getMethods和getTypes
    addSetMethods(clazz); // 处理clazz中的setter,填充setMethods和setTypes
    addFields(clazz); // 处理没有getter和setter的成员属性,并且仍然为该field添加上一对get和set方法

    // 处理名称
    readablePropertyNames = getMethods.keySet().toArray(new String[0]);
    writablePropertyNames = setMethods.keySet().toArray(new String[0]);

    // 大写名称 -> 原始名称
    for (String propName : readablePropertyNames)
      caseInsensitivePropertyMap.put(propName.toUpperCase(Locale.ENGLISH), propName);
    for (String propName : writablePropertyNames)
      caseInsensitivePropertyMap.put(propName.toUpperCase(Locale.ENGLISH), propName);
  }

  // 找到无参构造函数然后赋给defaultConstructor
  private void addDefaultConstructor(Class<?> clazz) {
    Constructor<?>[] constructors = clazz.getDeclaredConstructors();
    Arrays.stream(constructors).filter(constructor -> constructor.getParameterTypes().length == 0)
        .findAny().ifPresent(constructor -> this.defaultConstructor = constructor);
  }

  // 添加读方法
  private void addGetMethods(Class<?> clazz) {
    Map<String, List<Method>> conflictingGetters = new HashMap<>();
    Method[] methods = getClassMethods(clazz);
    Arrays.stream(methods).filter(m -> m.getParameterTypes().length == 0 && PropertyNamer.isGetter(m.getName()))
        .forEach(m -> addMethodConflict(conflictingGetters, PropertyNamer.methodToProperty(m.getName()), m));
    resolveGetterConflicts(conflictingGetters);
  }

  private void resolveGetterConflicts(Map<String, List<Method>> conflictingGetters) {
    for (Entry<String, List<Method>> entry : conflictingGetters.entrySet()) {
      Method winnerGetterMethod = null;
      String propertyName = entry.getKey();
      boolean isAmbiguous = false;
      List<Method> allGettersForThisProperty = entry.getValue();
      for (Method candidateMethod/*候选方法*/ : allGettersForThisProperty) {
        if (winnerGetterMethod == null) {
          winnerGetterMethod = candidateMethod;
          continue;
        }
        Class<?> winnerReturnType = winnerGetterMethod.getReturnType();
        Class<?> candidateReturnType = candidateMethod.getReturnType();

        if (candidateReturnType.equals(winnerReturnType)) {
          // 当前候选getter的返回值类型与当前winnerGetter一致

          if (!boolean.class.equals(candidateReturnType)) {
            // 候选getter返回值类型不是boolean

            isAmbiguous = true; // 标记这个属性的getter方法有「二义性」，然后结束循环
            break;
          } else if (candidateMethod.getName().startsWith("is")) {
            // 候选getter为is开头则采用该方法
            winnerGetterMethod = candidateMethod;
          }
        } else if (candidateReturnType.isAssignableFrom(winnerReturnType)) {
          // candidateReturnType与winnerReturnType一致，或前者是后者的父类或父接口
          // OK getter type is descendant
        } else if (winnerReturnType.isAssignableFrom(candidateReturnType)) {
          // 优先返回更具体的子类接口
          winnerGetterMethod = candidateMethod;
        } else {
          // 其他情况下, 同样也返回「二义性」
          isAmbiguous = true;
          break;
        }
      }

      // 如果有「二义性」,那么添加的getter方法是一个总会抛出异常的Invoker实现类,即AmbiguousMethodInvoker
      addGetMethod(propertyName, winnerGetterMethod, isAmbiguous);
    }
  }

  private void addGetMethod(String name, Method method, boolean isAmbiguous) {
    MethodInvoker invoker = isAmbiguous
        ? new AmbiguousMethodInvoker(method, MessageFormat.format(
        "Illegal overloaded getter method with ambiguous type for property ''{0}'' in class ''{1}''. This breaks the JavaBeans specification and can cause unpredictable results.",
        name, method.getDeclaringClass().getName()))
        : new MethodInvoker(method);
    getMethods.put(name, invoker);
    Type returnType = TypeParameterResolver.resolveReturnType(method, type);

    // 置入读方法的「出参」类型
    getTypes.put(name, typeToClass(returnType));
  }

  // 添加写方法
  private void addSetMethods(Class<?> clazz) {
    Map<String, List<Method>> conflictingSetters = new HashMap<>();
    Method[] methods = getClassMethods(clazz);
    Arrays.stream(methods).filter(m -> m.getParameterTypes().length == 1 && PropertyNamer.isSetter(m.getName()))
        .forEach(m -> addMethodConflict(conflictingSetters, PropertyNamer.methodToProperty(m.getName()), m));
    resolveSetterConflicts(conflictingSetters);
  }

  private void addMethodConflict(Map<String, List<Method>> conflictingMethods, String name, Method method) {
    if (isValidPropertyName(name)) {
      List<Method> list = conflictingMethods.computeIfAbsent(name, k -> new ArrayList<>());
      list.add(method);
    }
  }

  private void resolveSetterConflicts(Map<String, List<Method>> conflictingSetters) {
    for (Entry<String, List<Method>> entry : conflictingSetters.entrySet()) {
      String propName = entry.getKey();
      List<Method> setters = entry.getValue();
      Class<?> getterType = getTypes.get(propName);
      boolean isGetterAmbiguous = getMethods.get(propName) instanceof AmbiguousMethodInvoker;
      boolean isSetterAmbiguous = false;
      Method match = null;
      for (Method setter : setters) {
        if (!isGetterAmbiguous && setter.getParameterTypes()[0].equals(getterType)) {
          // should be the best match
          match = setter;
          break;
        }
        if (!isSetterAmbiguous) {
          match = pickBetterSetter(match, setter, propName);
          isSetterAmbiguous = match == null;
        }
      }
      if (match != null) {
        addSetMethod(propName, match);
      }
    }
  }

  private Method pickBetterSetter(Method setter1, Method setter2, String property) {
    if (setter1 == null) {
      return setter2;
    }
    Class<?> paramType1 = setter1.getParameterTypes()[0];
    Class<?> paramType2 = setter2.getParameterTypes()[0];
    if (paramType1.isAssignableFrom(paramType2)) {
      return setter2;
    } else if (paramType2.isAssignableFrom(paramType1)) {
      return setter1;
    }
    MethodInvoker invoker = new AmbiguousMethodInvoker(setter1,
        MessageFormat.format(
            "Ambiguous setters defined for property ''{0}'' in class ''{1}'' with types ''{2}'' and ''{3}''.",
            property, setter2.getDeclaringClass().getName(), paramType1.getName(), paramType2.getName()));
    setMethods.put(property, invoker);
    Type[] paramTypes = TypeParameterResolver.resolveParamTypes(setter1, type);
    setTypes.put(property, typeToClass(paramTypes[0]));
    return null;
  }

  private void addSetMethod(String name, Method method) {
    MethodInvoker invoker = new MethodInvoker(method);
    setMethods.put(name, invoker);
    Type[] paramTypes = TypeParameterResolver.resolveParamTypes(method, type);
    setTypes.put(name, typeToClass(paramTypes[0]));
  }

  private Class<?> typeToClass(Type src) {
    Class<?> result = null;
    if (src instanceof Class) {
      result = (Class<?>) src;
    } else if (src instanceof ParameterizedType) {
      result = (Class<?>) ((ParameterizedType) src).getRawType();
    } else if (src instanceof GenericArrayType) {
      Type componentType = ((GenericArrayType) src).getGenericComponentType();
      if (componentType instanceof Class) {
        result = Array.newInstance((Class<?>) componentType, 0).getClass();
      } else {
        Class<?> componentClass = typeToClass(componentType);
        result = Array.newInstance(componentClass, 0).getClass();
      }
    }
    // 如果泛型变量未指定，也就是说实际上要使用的实体类带有范型变量，那么这里result就会为空
    // 因为上面并未对TypeVariable作任何判断
    if (result == null) {
      result = Object.class;
    }
    return result;
  }

  // 处理没有getter和setter方法的字段
  private void addFields(Class<?> clazz) {
    Field[] fields = clazz.getDeclaredFields();
    for (Field field : fields) {
      if (!setMethods.containsKey(field.getName())) {
        // 该field没有setter

        // issue #379 - removed the check for final because JDK 1.5 allows
        // modification of final fields through reflection (JSR-133).
        // (JGB) pr #16 - final static can only be set by the classloader

        // 以整数形式返回由此 Field 对象表示的字段的 Java 语言修饰符
        // 应该使用 Modifier 类对这些修饰符进行解码
        int modifiers = field.getModifiers();
        if (!(Modifier.isFinal(modifiers) && Modifier.isStatic(modifiers))) {
          // final static can only be set by the classloader
          addSetField(field);
        }
      }
      if (!getMethods.containsKey(field.getName())) {
        addGetField(field);
      }
    }
    if (clazz.getSuperclass() != null) {
      addFields(clazz.getSuperclass());
    }
  }

  private void addSetField(Field field) {
    if (isValidPropertyName(field.getName())) {
      // 是合法属性名
      setMethods.put(field.getName(), new SetFieldInvoker(field));

      Type fieldType = TypeParameterResolver.resolveFieldType(field, type);
      setTypes.put(field.getName(), typeToClass(fieldType));
    }
  }

  private void addGetField(Field field) {
    if (isValidPropertyName(field.getName())) {
      getMethods.put(field.getName(), new GetFieldInvoker(field));
      Type fieldType = TypeParameterResolver.resolveFieldType(field, type);
      getTypes.put(field.getName(), typeToClass(fieldType));
    }
  }

  // 验证属性名是否合法
  private boolean isValidPropertyName(String name) {
    boolean val = name.startsWith("$") || "serialVersionUID".equals(name) || "class".equals(name);
    return !val;
  }

  /**
   * This method returns an array containing all methods
   * declared in this class and any superclass.
   * We use this method, instead of the simpler <code>Class.getMethods()</code>,
   * because we want to look for private methods as well.
   *
   * @param clazz The class
   * @return An array containing all methods in this class
   */
  private Method[] getClassMethods(Class<?> clazz) {
    Map<String, Method> uniqueMethods = new HashMap<>();
    Class<?> currentClass = clazz;
    while (currentClass != null && currentClass != Object.class) {

      // 添加方法
      addUniqueMethods(uniqueMethods, currentClass.getDeclaredMethods());

      // we also need to look for interface methods -
      // because the class may be abstract
      Class<?>[] interfaces = currentClass.getInterfaces();
      for (Class<?> anInterface : interfaces) {
        addUniqueMethods(uniqueMethods, anInterface.getMethods());
      }

      // 自底向上一层一层获取所有方法
      currentClass = currentClass.getSuperclass();
    }

    Collection<Method> methods = uniqueMethods.values();
    return methods.toArray(new Method[0]);
  }

  private void addUniqueMethods(Map<String, Method> uniqueMethods, Method[] methods) {
    for (Method currentMethod : methods) {

      // 泛型抽象方法的实现方法，jvm会生成一个桥接方法来匹配父类或接口中被擦除的类型匹配
      // 在这里要排除掉桥接方法，因为我们只需要获取真实参数类型的方法
      if (!currentMethod.isBridge()) {

        // 获取该方法的唯一签名，用来区别是否重复
        String signature = getSignature(currentMethod);

        // check to see if the method is already known
        // if it is known, then an extended class must have
        // overridden a method
        // 如果uniqueMethods已经包含了该signature，那么说明该类的子类一定已经覆盖过这个方法了，这里就不添加了
        if (!uniqueMethods.containsKey(signature)) {
          uniqueMethods.put(signature, currentMethod);
        }
      }
    }
  }

  /**
   * 这个方法返回一个自定义的指定方法的唯一签名
   * 比如Object类所有declaredMethods的签名就是：
   * <p>
   * void#finalize
   * void#wait:long
   * void#wait:long,int
   * void#wait
   * boolean#equals:java.lang.Object
   * java.lang.String#toString
   * int#hashCode
   * java.lang.Class#getClass
   * java.lang.Object#clone
   * void#notify
   * void#notifyAll
   * void#registerNatives
   */
  private String getSignature(Method method) {
    StringBuilder sb = new StringBuilder();
    Class<?> returnType = method.getReturnType();
    if (returnType != null) {
      sb.append(returnType.getName()).append('#');
    }
    sb.append(method.getName());
    Class<?>[] parameters = method.getParameterTypes();
    for (int i = 0; i < parameters.length; i++) {
      sb.append(i == 0 ? ':' : ',').append(parameters[i].getName());
    }
    return sb.toString();
  }

  /**
   * Checks whether can control member accessible.
   *
   * @return If can control member accessible, it return {@literal true}
   * @since 3.5.0
   */
  public static boolean canControlMemberAccessible() {
    try {
      SecurityManager securityManager = System.getSecurityManager();
      if (null != securityManager) {
        securityManager.checkPermission(new ReflectPermission("suppressAccessChecks"));
      }
    } catch (SecurityException e) {
      return false;
    }
    return true;
  }

  /**
   * Gets the name of the class the instance provides information for.
   *
   * @return The class name
   */
  public Class<?> getType() {
    return type;
  }

  public Constructor<?> getDefaultConstructor() {
    if (defaultConstructor != null) {
      return defaultConstructor;
    } else {
      throw new ReflectionException("There is no default constructor for " + type);
    }
  }

  public boolean hasDefaultConstructor() {
    return defaultConstructor != null;
  }

  public Invoker getSetInvoker(String propertyName) {
    Invoker method = setMethods.get(propertyName);
    if (method == null) {
      throw new ReflectionException("There is no setter for property named '" + propertyName + "' in '" + type + "'");
    }
    return method;
  }

  public Invoker getGetInvoker(String propertyName) {
    Invoker method = getMethods.get(propertyName);
    if (method == null) {
      throw new ReflectionException("There is no getter for property named '" + propertyName + "' in '" + type + "'");
    }
    return method;
  }

  /**
   * Gets the type for a property setter.
   *
   * @param propertyName - the name of the property
   * @return The Class of the property setter
   */
  public Class<?> getSetterType(String propertyName) {
    Class<?> clazz = setTypes.get(propertyName);
    if (clazz == null) {
      throw new ReflectionException("There is no setter for property named '" + propertyName + "' in '" + type + "'");
    }
    return clazz;
  }

  /**
   * Gets the type for a property getter.
   *
   * @param propertyName - the name of the property
   * @return The Class of the property getter
   */
  public Class<?> getGetterType(String propertyName) {
    Class<?> clazz = getTypes.get(propertyName);
    if (clazz == null) {
      throw new ReflectionException("There is no getter for property named '" + propertyName + "' in '" + type + "'");
    }
    return clazz;
  }

  /**
   * Gets an array of the readable properties for an object.
   *
   * @return The array
   */
  public String[] getGetablePropertyNames() {
    return readablePropertyNames;
  }

  /**
   * Gets an array of the writable properties for an object.
   *
   * @return The array
   */
  public String[] getSetablePropertyNames() {
    return writablePropertyNames;
  }

  /**
   * Check to see if a class has a writable property by name.
   *
   * @param propertyName - the name of the property to check
   * @return True if the object has a writable property by the name
   */
  public boolean hasSetter(String propertyName) {
    return setMethods.containsKey(propertyName);
  }

  /**
   * Check to see if a class has a readable property by name.
   *
   * @param propertyName - the name of the property to check
   * @return True if the object has a readable property by the name
   */
  public boolean hasGetter(String propertyName) {
    return getMethods.containsKey(propertyName);
  }

  public String findPropertyName(String name) {
    return caseInsensitivePropertyMap.get(name.toUpperCase(Locale.ENGLISH));
  }
}
