/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bigdog.server.cishan.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author bigdog
 */
@Controller
public class PageHandler {

    @RequestMapping("/index")
    public ModelAndView goIndex() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("web/index");
        mv.addObject("w",1);
        return mv;
    }

    @RequestMapping("/gethelp")
    public ModelAndView getGethelp() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("web/gethelp");
        mv.addObject("w",2);
        return mv;
    }
    
    @RequestMapping("/givehelp")
    public ModelAndView getGivehelp() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("web/givehelp");
        mv.addObject("w",3);
        return mv;
    }
}
