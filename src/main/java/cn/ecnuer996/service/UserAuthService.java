package cn.ecnuer996.service;

import cn.ecnuer996.bean.UserAuth;
import cn.ecnuer996.dao.UserAuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserAuthService")
public class UserAuthService {

    @Autowired
    private UserAuthMapper userAuthDao;

    public UserAuth login(String nickname, String password){
        return userAuthDao.login(nickname,password);
    }

}
