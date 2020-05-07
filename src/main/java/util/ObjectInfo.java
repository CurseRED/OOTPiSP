package main.java.util;

import jdk.jfr.Description;

public class ObjectInfo {

    private String objectName;
    private String classNameRus;
    private Class<?> className;
    private Object object;

    public ObjectInfo(String objectName, Class<?> className, Object object) {
        this.objectName = objectName;
        this.className = className;
        this.object = object;
        this.classNameRus = className.getAnnotation(Description.class).value();
    }

    public String getClassNameRus() {
        return classNameRus;
    }

    public void setClassNameRus(String classNameRus) {
        this.classNameRus = classNameRus;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public Class<?> getClassName() {
        return className;
    }

    public void setClassName(Class<?> className) {
        this.className = className;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
