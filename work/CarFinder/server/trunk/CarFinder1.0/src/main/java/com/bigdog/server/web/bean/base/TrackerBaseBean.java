package com.bigdog.server.web.bean.base;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 8/1/13
 * Time: 8:15 AM
 */
public class TrackerBaseBean {

    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "TrackerBaseBean{" +
                "method='" + method + '\'' +
                '}';
    }
}
