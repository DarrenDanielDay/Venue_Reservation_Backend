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
            response.put("message","�û������ڣ�");
            return response;
        }else if(!userAuth.getCredential().equals(credential)){ //��Ϊû���������Ͷ��ŷ���
                                                             // ���������¼��ʽ�Ͷ��ŵ�¼��ʽ����֤��ֻ�ܾ�̬�洢�����ݿ�
            response.put("code",400);                        // ��ͨ���ж��ַ����Ƿ��������֤��֤��
            if(signInMethod.equals("nickname")){
                response.put("message","�������");
            }else if(signInMethod.equals("phone")){
                response.put("message","�ֻ���֤�����");
            }else if(signInMethod.equals("email")){
                response.put("message","������֤�����");
            }
            return response;
        }else{
            response.put("code",200);
            response.put("message","��¼�ɹ�");
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
            response.put("message","�û����ѱ�ʹ�ã�");
            return response;
        }else if(userAuthService.getBySignInMethod("phone",phone)!=null){
            response.put("code",400);
            response.put("message","�ֻ����ѱ�ע�ᣡ");
            return response;
        }else if(userAuthService.getBySignInMethod("email",email)!=null){
            response.put("code",400);
            response.put("message","�����ѱ�ע�ᣡ");
            return response;
        }else{
            User user=new User();
            user.setId(null);
            user.setNickname(nickname);
            user.setAvatar("/user-avatars/default.jpg"); // Ĭ��ͷ��
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
            response.put("message","ע��ɹ���");
            response.put("data",user);
            return response;
        }
    }

    @RequestMapping(value="/update-user-info",method=RequestMethod.POST)
    public @ResponseBody JSONObject updateUserInfo(@RequestBody User user){
        JSONObject response=new JSONObject();
        if(user.getId()==null || userService.getUserById(user.getId())==null){
            response.put("code",400);
            response.put("message","���������һ�������ڵ��û�����Ϣ��");
            return response;
        }else{
            userService.insert(user);
            response.put("code",200);
            response.put("message","������Ϣ���³ɹ���");
            response.put("data",userService.getUserById(user.getId()));
            return response;
        }
    }
}
