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

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * @author Iwao AVE!
 */
public class TypeParameterResolver {

    /**
     * Resolve field type.
     * 解析成员属性的类型.
     *
     * @param field   成员属性
     * @param srcType 成员属性所属的 Class 向上转型为 Type
     * @return 成员属性的Type. The field type as {@link Type}. If it has type parameters in the declaration,<br>
     * they will be resolved to the actual runtime {@link Type}s.
     */
    public static Type resolveFieldType(Field field, Type srcType) {

        // 获取字段的类型，可能是 Class、ParameterizedType、GenericArrayType
        Type fieldType = field.getGenericType();

        // 获取字段定义所在的类的Class对象，也就是成员属性所属的 Class
        Class<?> declaringClass = field.getDeclaringClass();

        // (字段类型fieldType，成员所属的Class向上转型后的Type，成员所属的Class)
        return resolveType(fieldType, srcType, declaringClass);
    }

    /**
     * Resolve return type.
     *
     * @param method  the method
     * @param srcType the src type
     * @return The return type of the method as {@link Type}. If it has type parameters in the declaration,<br>
     * they will be resolved to the actual runtime {@link Type}s.
     */
    public static Type resolveReturnType(Method method, Type srcType) {
        Type returnType = method.getGenericReturnType();
        Class<?> declaringClass = method.getDeclaringClass();
        return resolveType(returnType, srcType, declaringClass);
    }

    /**
     * Resolve param types.
     *
     * @param method  the method
     * @param srcType the src type
     * @return The parameter types of the method as an array of {@link Type}s. If they have type parameters in the
     * declaration,<br>
     * they will be resolved to the actual runtime {@link Type}s.
     */
    public static Type[] resolveParamTypes(Method method, Type srcType) {
        Type[] paramTypes = method.getGenericParameterTypes();
        Class<?> declaringClass = method.getDeclaringClass();
        Type[] result = new Type[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            result[i] = resolveType(paramTypes[i], srcType, declaringClass);
        }
        return result;
    }

    private static Type resolveType(Type type, Type srcType, Class<?> declaringClass) {
        if (type instanceof TypeVariable) {
            // type属于类型变量
            return resolveTypeVar((TypeVariable<?>) type, srcType, declaringClass);

        } else if (type instanceof ParameterizedType) {
            // type属于参数化类型
            return resolveParameterizedType((ParameterizedType) type, srcType, declaringClass);

        } else if (type instanceof GenericArrayType) {
            // type属于数组类型且元素类型为 「类型变量」或「参数化类型」
            return resolveGenericArrayType((GenericArrayType) type, srcType, declaringClass);

        } else {
            // 都不属于，说明就属于普通的Class类型，可能是基本类型可能是对象类型也可能是常规数组类型
            // 直接返回类型
            return type;
        }
    }

    private static Type resolveGenericArrayType(GenericArrayType genericArrayType, Type srcType, Class<?> declaringClass) {
        Type componentType = genericArrayType.getGenericComponentType();
        Type resolvedComponentType = null;
        if (componentType instanceof TypeVariable) {
            resolvedComponentType = resolveTypeVar((TypeVariable<?>) componentType, srcType, declaringClass);
        } else if (componentType instanceof GenericArrayType) {
            resolvedComponentType = resolveGenericArrayType((GenericArrayType) componentType, srcType, declaringClass);
        } else if (componentType instanceof ParameterizedType) {
            resolvedComponentType = resolveParameterizedType((ParameterizedType) componentType, srcType, declaringClass);
        }
        if (resolvedComponentType instanceof Class) {
            return Array.newInstance((Class<?>) resolvedComponentType, 0).getClass();
        } else {
            return new GenericArrayTypeImpl(resolvedComponentType);
        }
    }

    private static ParameterizedType resolveParameterizedType(ParameterizedType parameterizedType, Type srcType, Class<?> declaringClass) {
        Class<?> rawType = (Class<?>) parameterizedType.getRawType();
        Type[] typeArgs = parameterizedType.getActualTypeArguments();
        Type[] args = new Type[typeArgs.length];
        for (int i = 0; i < typeArgs.length; i++) {
            if (typeArgs[i] instanceof TypeVariable) {
                args[i] = resolveTypeVar((TypeVariable<?>) typeArgs[i], srcType, declaringClass);
            } else if (typeArgs[i] instanceof ParameterizedType) {
                args[i] = resolveParameterizedType((ParameterizedType) typeArgs[i], srcType, declaringClass);
            } else if (typeArgs[i] instanceof WildcardType) {
                args[i] = resolveWildcardType((WildcardType) typeArgs[i], srcType, declaringClass);
            } else {
                args[i] = typeArgs[i];
            }
        }
        return new ParameterizedTypeImpl(rawType, null, args);
    }

    private static Type resolveWildcardType(WildcardType wildcardType, Type srcType, Class<?> declaringClass) {
        Type[] lowerBounds = resolveWildcardTypeBounds(wildcardType.getLowerBounds(), srcType, declaringClass);
        Type[] upperBounds = resolveWildcardTypeBounds(wildcardType.getUpperBounds(), srcType, declaringClass);
        return new WildcardTypeImpl(lowerBounds, upperBounds);
    }

    private static Type[] resolveWildcardTypeBounds(Type[] bounds, Type srcType, Class<?> declaringClass) {
        Type[] result = new Type[bounds.length];
        for (int i = 0; i < bounds.length; i++) {
            if (bounds[i] instanceof TypeVariable) {
                result[i] = resolveTypeVar((TypeVariable<?>) bounds[i], srcType, declaringClass);
            } else if (bounds[i] instanceof ParameterizedType) {
                result[i] = resolveParameterizedType((ParameterizedType) bounds[i], srcType, declaringClass);
            } else if (bounds[i] instanceof WildcardType) {
                result[i] = resolveWildcardType((WildcardType) bounds[i], srcType, declaringClass);
            } else {
                result[i] = bounds[i];
            }
        }
        return result;
    }

    private static Type resolveTypeVar(TypeVariable<?> typeVar, Type srcType, Class<?> declaringClass) {
        Type result;
        Class<?> clazz;
        if (srcType instanceof Class) {
            // Reflector只会进这里
            clazz = (Class<?>) srcType;
        } else if (srcType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) srcType;
            clazz = (Class<?>) parameterizedType.getRawType();
        } else {
            throw new IllegalArgumentException(String.format(
                    "The 2nd arg must be Class or ParameterizedType, but was: %s",
                    srcType.getClass()));
        }

        if (clazz == declaringClass) {
            Type[] bounds = typeVar.getBounds();
            if (bounds.length > 0) {
                return bounds[0];
            }
            return Object.class;
        }

        Type superclass = clazz.getGenericSuperclass();
        result = scanSuperTypes(typeVar, srcType, declaringClass, clazz, superclass);
        if (result != null) {
            return result;
        }

        Type[] superInterfaces = clazz.getGenericInterfaces();
        for (Type superInterface : superInterfaces) {
            result = scanSuperTypes(typeVar, srcType, declaringClass, clazz, superInterface);
            if (result != null) {
                return result;
            }
        }
        return Object.class;
    }

    private static Type scanSuperTypes(TypeVariable<?> typeVar, Type srcType, Class<?> declaringClass, Class<?> clazz, Type superclass) {
        if (superclass instanceof ParameterizedType) {
            ParameterizedType parentAsType = (ParameterizedType) superclass;
            Class<?> parentAsClass = (Class<?>) parentAsType.getRawType();
            TypeVariable<?>[] parentTypeVars = parentAsClass.getTypeParameters();
            if (srcType instanceof ParameterizedType) {
                parentAsType = translateParentTypeVars((ParameterizedType) srcType, clazz, parentAsType);
            }
            if (declaringClass == parentAsClass) {
                for (int i = 0; i < parentTypeVars.length; i++) {
                    if (typeVar.equals(parentTypeVars[i])) {
                        return parentAsType.getActualTypeArguments()[i];
                    }
                }
            }
            if (declaringClass.isAssignableFrom(parentAsClass)) {
                return resolveTypeVar(typeVar, parentAsType, declaringClass);
            }
        } else if (superclass instanceof Class && declaringClass.isAssignableFrom((Class<?>) superclass)) {
            return resolveTypeVar(typeVar, superclass, declaringClass);
        }
        return null;
    }

    private static ParameterizedType translateParentTypeVars(ParameterizedType srcType, Class<?> srcClass, ParameterizedType parentType) {
        Type[] parentTypeArgs = parentType.getActualTypeArguments();
        Type[] srcTypeArgs = srcType.getActualTypeArguments();
        TypeVariable<?>[] srcTypeVars = srcClass.getTypeParameters();
        Type[] newParentArgs = new Type[parentTypeArgs.length];
        boolean noChange = true;
        for (int i = 0; i < parentTypeArgs.length; i++) {
            if (parentTypeArgs[i] instanceof TypeVariable) {
                for (int j = 0; j < srcTypeVars.length; j++) {
                    if (srcTypeVars[j].equals(parentTypeArgs[i])) {
                        noChange = false;
                        newParentArgs[i] = srcTypeArgs[j];
                    }
                }
            } else {
                newParentArgs[i] = parentTypeArgs[i];
            }
        }
        return noChange ? parentType : new ParameterizedTypeImpl((Class<?>) parentType.getRawType(), null, newParentArgs);
    }

    private TypeParameterResolver() {
        super();
    }

    static class ParameterizedTypeImpl implements ParameterizedType {
        private Class<?> rawType;

        private Type ownerType;

        private Type[] actualTypeArguments;

        public ParameterizedTypeImpl(Class<?> rawType, Type ownerType, Type[] actualTypeArguments) {
            super();
            this.rawType = rawType;
            this.ownerType = ownerType;
            this.actualTypeArguments = actualTypeArguments;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return actualTypeArguments;
        }

        @Override
        public Type getOwnerType() {
            return ownerType;
        }

        @Override
        public Type getRawType() {
            return rawType;
        }

        @Override
        public String toString() {
            return "ParameterizedTypeImpl [rawType=" + rawType + ", ownerType=" + ownerType + ", actualTypeArguments=" + Arrays.toString(actualTypeArguments) + "]";
        }
    }

    static class WildcardTypeImpl implements WildcardType {
        private Type[] lowerBounds;

        private Type[] upperBounds;

        WildcardTypeImpl(Type[] lowerBounds, Type[] upperBounds) {
            super();
            this.lowerBounds = lowerBounds;
            this.upperBounds = upperBounds;
        }

        @Override
        public Type[] getLowerBounds() {
            return lowerBounds;
        }

        @Override
        public Type[] getUpperBounds() {
            return upperBounds;
        }
    }

    static class GenericArrayTypeImpl implements GenericArrayType {
        private Type genericComponentType;

        GenericArrayTypeImpl(Type genericComponentType) {
            super();
            this.genericComponentType = genericComponentType;
        }

        @Override
        public Type getGenericComponentType() {
            return genericComponentType;
        }
    }
}
