package cn.ecnuer996.controller;

import cn.ecnuer996.bean.User;
import cn.ecnuer996.service.UserService;
import cn.ecnuer996.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
/*
    @RequestMapping(value="/user")
    public int register(HttpServletRequest request){
        String username = request.getParameter("usr");
        String password = request.getParameter("psw");
        String mail = request.getParameter("mail");
        String tel = request.getParameter("tel");
        int method = Integer.parseInt("method");
        if(mail.equals("") || tel.equals("")) return 401;
        //
        return 0;
    }
*/
    @RequestMapping(value="/user")
    public User getUser(HttpServletRequest request){
        int method = Integer.parseInt(request.getParameter("method"));
        return userService.getUserById(method);
    }
}
