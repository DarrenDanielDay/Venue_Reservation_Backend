package cn.ecnuer996.controller;

import cn.ecnuer996.bean.User;
import cn.ecnuer996.service.UserService;
import com.sun.mail.imap.protocol.INTERNALDATE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    /*
    @RequestMapping(value="/register")
    public User register(HttpServletRequest request){
        String username = request.getParameter("usr");
        String password = request.getParameter("psw");
        String mail = request.getParameter("mail");
        String tel = request.getParameter("tel");
        int method = Integer.parseInt(request.getParameter("method"));
        if(method != 0)return userService.getUserById(-1);                  //后期添加功能
        if(mail.equals("") || tel.equals("")){
            return userService.getUserById(-1);
        }
        Boolean flag = userService.registerJudge(username);
        if(flag){
            User usr = new User(username,password,tel,mail);
            userService.register(username,password,tel,mail);
            return userService.getUserById(-1);////
        }
        else{
            return userService.getUserById(-2);
        }
    }

    @RequestMapping(value="/user")
    public User getUser(HttpServletRequest request){
        int method = Integer.parseInt(request.getParameter("method"));
        return userService.getUserById(method);
    }
    */
}
