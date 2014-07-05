package com.facemail.server.mobile.tool;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/2/13
 * Time: 12:01 PM
 */
public class TestParameter {
    private String key;
    private String value;

    public TestParameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
