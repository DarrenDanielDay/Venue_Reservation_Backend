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
            UserAuth userAuth = userAuthService.loginJudge(usr,psw);
            if(userAuth == null){
                return userService.getUserById(-1);
            }
            else{
                return userService.getUserById(userAuth.getUserId());
            }
        }
    }

    @RequestMapping(value="/register")
    public User register(HttpServletRequest request){
        String usr = request.getParameter("usr");
        String psw = request.getParameter("psw");
        int method = Integer.parseInt(request.getParameter("method"));
        if(method != 0){
            return userService.getUserById(-1);
        }
        else{
            UserAuth userAuth = userAuthService.registerJudge(usr);
            if(userAuth != null){
                return userService.getUserById(-2);
            }
            else{
                User tempUser = new User(usr,psw,null,null);
                UserAuth tempUserAuth = new UserAuth("nickname",usr,psw);
                userAuthService.insert(tempUserAuth);
                userService.insert(tempUser);
                return userService.getUserByName(usr);
            }
        }
    }

}
