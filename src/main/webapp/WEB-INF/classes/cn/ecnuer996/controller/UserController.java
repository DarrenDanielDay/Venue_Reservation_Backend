package cn.ecnuer996.controller;

import cn.ecnuer996.bean.User;
import cn.ecnuer996.bean.UserAuth;
import cn.ecnuer996.service.UserAuthService;
import cn.ecnuer996.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserAuthService userAuthService;

    String urlPrefix="https://ecnuer996.cn/images";

    @RequestMapping(value="/sign-in",method=RequestMethod.POST)
    public @ResponseBody JSONObject signIn(@RequestBody JSONObject postBody){
        String identifier=postBody.getString("id");
        String credential=postBody.getString("credential");
        String signInMethod=postBody.getString("method");
        JSONObject response=new JSONObject();
        UserAuth userAuth=userAuthService.getBySignInMethod(signInMethod,identifier);
        if(userAuth==null){
            response.put("code",500);
            response.put("message","用户不存在！");
            return response;
        }else if(!userAuth.getCredential().equals(credential)){ //因为没有邮箱服务和短信服务，
                                                             // 所以邮箱登录方式和短信登录方式的验证码只能静态存储在数据库
            response.put("code",400);                        // 简单通过判断字符串是否相等来验证验证码
            if(signInMethod.equals("nickname")){
                response.put("message","密码错误！");
            }else if(signInMethod.equals("phone")){
                response.put("message","手机验证码错误！");
            }else if(signInMethod.equals("email")){
                response.put("message","邮箱验证码错误！");
            }
            return response;
        }else{
            response.put("code",200);
            response.put("message","登录成功");
            User user=userService.getUserById(userAuth.getUserId());
            user.setAvatar(urlPrefix+user.getAvatar());
            response.put("data",user);
            return response;
        }
    }

    @RequestMapping(value="/sign-up",method=RequestMethod.POST)
    public @ResponseBody JSONObject signUp(@RequestBody JSONObject postBody){
        JSONObject response=new JSONObject();
        String email=postBody.getString("email");
        String phone=postBody.getString("phone");
        String nickname=postBody.getString("nickname");
        String password=postBody.getString("password");
        if(userAuthService.getBySignInMethod("nickname",nickname)!=null){
            response.put("code",400);
            response.put("message","用户名已被使用！");
            return response;
        }else if(userAuthService.getBySignInMethod("phone",phone)!=null){
            response.put("code",400);
            response.put("message","手机号已被注册！");
            return response;
        }else if(userAuthService.getBySignInMethod("email",email)!=null){
            response.put("code",400);
            response.put("message","邮箱已被注册！");
            return response;
        }else{
            User user=new User();
            user.setId(null);
            user.setNickname(nickname);
            user.setAvatar("/user-avatars/default.jpg"); // 默认头像
            user.setEmail(email);
            user.setPhone(phone);
            userService.insert(user);
            user.setAvatar(urlPrefix+user.getAvatar());

            UserAuth nicknameAuth=new UserAuth();
            UserAuth phoneAuth=new UserAuth();
            UserAuth emailAuth=new UserAuth();

            int user_id=userService.getUserByName(nickname).getId();

            nicknameAuth.setUserId(user_id);
            nicknameAuth.setIdentityType("nickname");
            nicknameAuth.setIdentifier(nickname);
            nicknameAuth.setCredential(password);
            userAuthService.insert(nicknameAuth);

//            phoneAuth.setUserId(user_id);
//            phoneAuth.setIdentityType("phone");
//            phoneAuth.setIdentifier(phone);
//            phoneAuth.setCredential("null");
//            userAuthService.insert(phoneAuth);
//
//            emailAuth.setUserId(user_id);
//            emailAuth.setIdentityType("email");
//            emailAuth.setIdentifier(email);
//            emailAuth.setCredential("null");
//            userAuthService.insert(emailAuth);

            response.put("code",200);
            response.put("message","注册成功！");
            response.put("data",user);
            return response;
        }
    }

    @RequestMapping(value="/update-user-info",method=RequestMethod.POST)
    public @ResponseBody JSONObject updateUserInfo(@RequestBody User user){
        JSONObject response=new JSONObject();
        if(user.getId()==null || userService.getUserById(user.getId())==null){
            response.put("code",400);
            response.put("message","不允许更新一个不存在的用户的信息！");
            return response;
        }else{
            userService.insert(user);
            response.put("code",200);
            response.put("message","个人信息更新成功！");
            response.put("data",userService.getUserById(user.getId()));
            return response;
        }
    }
}
