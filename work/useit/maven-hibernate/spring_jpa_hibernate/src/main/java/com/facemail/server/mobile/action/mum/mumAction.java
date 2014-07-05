package com.facemail.server.mobile.action.mum;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import javax.annotation.Resource;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 5/29/13
 * Time: 5:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class mumAction extends ActionSupport implements ModelDriven<User> {
    private User user = new User();
    private String item_id;
    private List<String> pizzas = new ArrayList<String>();

    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    @Resource
    private UserDao mumUserDaoImpl;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<String> pizzas) {
        this.pizzas = pizzas;
    }

    public UserDao getMumUserDaoImpl() {
        return mumUserDaoImpl;
    }

    public void setMumUserDaoImpl(UserDao mumUserDaoImpl) {
        this.mumUserDaoImpl = mumUserDaoImpl;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    @Override
    public User getModel() {
        return user;
    }

    public String welcome() {
        String username = ActionContext.getContext().getSession().get("username") == null ? "" : (String) ActionContext.getContext().getSession().get("username");
        if ("".equals(username) || username == null) {
            return "login";
        }
        return SUCCESS;
    }

    public String reg() {
        System.out.println(user);
        if (user == null) {
            return "error_reg";
        }
        mumUserDaoImpl.save(user);
        ActionContext.getContext().getSession().put("username", user);
        return SUCCESS;
    }

    public String doreg() {
        return "registe";
    }

    public String login() {
        System.out.println(user);
        if (user == null) {
            return "error_reg";
        }
        User user1 = mumUserDaoImpl.getByPwd(user.getUsername(), user.getPassword());
        if (user1 != null) {
            ActionContext.getContext().getSession().put("username", user);
            return SUCCESS;
        }
        return "error_reg";
    }

    public String seeorder() {
        return "seeorder";
    }

    public String order() {
        return "seeResult";
    }

    public String addorder() {
        pizzas.add(item_id);
        ActionContext.getContext().getSession().put("pizza", pizzas);
        System.out.println("-->" + pizzas);
        inputStream = new StringBufferInputStream("ok");
        return "addok";
    }


}
