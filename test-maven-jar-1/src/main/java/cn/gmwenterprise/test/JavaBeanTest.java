package cn.gmwenterprise.test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

class School {
    private String name;
    private Boolean foreign;
    private boolean good;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getForeign() {
        return foreign;
    }

    public void setForeign(Boolean foreign) {
        this.foreign = foreign;
    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }
}

public class JavaBeanTest {
    public static void main(String[] args) throws IntrospectionException {
        var beanInfo = Introspector.getBeanInfo(School.class);
        for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
            System.out.printf("name: %s, readMethod: %s, writeMethod: %s%n", descriptor.getName(),
                descriptor.getReadMethod(), descriptor.getWriteMethod());
        }
    }
}
