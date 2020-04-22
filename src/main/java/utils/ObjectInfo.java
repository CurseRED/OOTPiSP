package main.java.utils;

public class ObjectInfo {

    private String objectName;
    private String className;

    public ObjectInfo(String objectName, String className) {
        this.objectName = objectName;
        this.className = className;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
