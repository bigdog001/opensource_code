/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigdog.server.skyplan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author bigdog
 */
@Controller
public class PageHandler {

    @RequestMapping(value = "page_{page}.htm")
    public ModelAndView findEmployee(@PathVariable String page) {
        ModelAndView mv = new ModelAndView();
        if ("".equals(page) || page == null) {
            mv.setViewName("page/error");
        } else {
            mv.addObject("w",page);
            mv.setViewName("page/" + page);
        }
        return mv;
    }
    
//  
//    @RequestMapping("log")
//    public ModelAndView log() {
//        ModelAndView mv = new ModelAndView();         
//        mv.setViewName("page/test");        
//        return mv;
//    }  

}
