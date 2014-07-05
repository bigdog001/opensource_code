package com.facemail.server.mobile.action.bigdog;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/24/13
 * Time: 2:32 AM
 * To change this template use File | Settings | File Templates.
 */
public class Bigdog extends ActionSupport {

    private String username;
    private String password;
    String [] names;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public void validate(){
        /*if ("".equals(getUsername())||null==getPassword()) {
          addFieldError("username",getText("username.require"));
        }if("".equals(getUsername())||null==getPassword()) {
            addFieldError("password",getText("password.require"));
        }*/
    }

    public String execute() throws Exception {
        System.out.println(username + "-->" + password);
        if ("scott".equals(getUsername())&&"tiger".equals(getPassword())) {
            String [] ok=new String[2];
            ok[0] = username;
            ok[1] = password;
            setNames(ok);
            return SUCCESS;
        }else {
            return INPUT;
        }
    }

}
