/**
 * Copyright 2009-2019 the original author or authors.
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
package org.apache.ibatis.reflection.property;

import org.apache.ibatis.reflection.Reflector;

import java.lang.reflect.Field;

/**
 * @author Clinton Begin
 */
public final class PropertyCopier {

  private PropertyCopier() {
    // Prevent Instantiation of Static Class
  }

  /**
   * 相同类型的两个对象之间的属性拷贝
   *
   * @param type            类型
   * @param sourceBean      源对象
   * @param destinationBean 目标对象
   */
  public static void copyBeanProperties(Class<?> type, Object sourceBean, Object destinationBean) {
    while (type != null) {

      // 获取parent类定义的属性字段
      final Field[] fields = type.getDeclaredFields();

      // 遍历
      for (Field field : fields) {
        try {
          try {
            field.set(destinationBean, field.get(sourceBean));
          } catch (IllegalAccessException e) {
            // field可能是private的
            if (Reflector.canControlMemberAccessible()) {
              field.setAccessible(true);
              field.set(destinationBean, field.get(sourceBean));
            } else {
              throw e;
            }
          }
        } catch (Exception e) {
          // Nothing useful to do, will only fail on final fields, which will be ignored.
        }
      }

      // 继续遍历父类
      type = type.getSuperclass();
    }
  }

}
