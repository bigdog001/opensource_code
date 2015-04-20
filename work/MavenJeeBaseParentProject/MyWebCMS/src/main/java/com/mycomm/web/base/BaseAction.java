/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycomm.web.base;

import com.mycomm.dao.mydao.IMycommDao;
import com.mycomm.dao.mydao.IUserDao;
import com.mycomm.web.bean.StringConstant;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import java.lang.reflect.ParameterizedType;
import javax.annotation.Resource;
import org.apache.struts2.convention.annotation.*;
/**
 *
 * @author jw362j
 */
@Results({
    @Result(name = StringConstant.ActionResult_NAME.JSP_SITE_INDEX_DIS, location = StringConstant.JSP_LOCATION.SITE_INDEX_JSP, type = "dispatcher"),
    @Result(name = StringConstant.ActionResult_NAME.JSP_MyWebCms_INDEX_DIS, location = StringConstant.JSP_LOCATION.MyWebCms_INDEX_JSP, type = "dispatcher")    
})
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

    private T model;

    public BaseAction() {
        try {
            // 通过反射获取model的真实类型
            ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
            Class<T> clazz = (Class<T>) pt.getActualTypeArguments()[0];
            // 通过反射创建model的实例
            model = clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T getModel() {
        return model;
    }
    @Resource
    protected IUserDao userDao;
    
    @Resource
    protected IMycommDao mycommDaoImpl;
    

}
