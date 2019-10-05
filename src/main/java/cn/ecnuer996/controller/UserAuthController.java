package cn.ecnuer996.controller;

import cn.ecnuer996.bean.User;
import cn.ecnuer996.bean.UserAuth;
import cn.ecnuer996.service.UserAuthService;
import cn.ecnuer996.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserAuthController {

    @Autowired
    private UserAuthService userAuthService;
    @Autowired
    private UserService userService;

    @RequestMapping(value="/login")
    public User login(HttpServletRequest request){
        String usr = request.getParameter("usr");
        String psw = request.getParameter("psw");
        int method = Integer.parseInt(request.getParameter("method"));
        if(method != 0){
            return userService.getUserById(-1);
        }
        else{
            UserAuth userAuth = userAuthService.login(usr,psw);
            if(userAuth == null){
                return userService.getUserById(-1);
            }
            else{
                return userService.getUserById(userAuth.getUserId());
            }
        }
    }

}
