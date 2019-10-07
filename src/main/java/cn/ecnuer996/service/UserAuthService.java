package cn.ecnuer996.service;

import cn.ecnuer996.bean.UserAuth;
import cn.ecnuer996.dao.UserAuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserAuthService")
public class UserAuthService {

    @Autowired
    private UserAuthMapper userAuthDao;

    public UserAuth registerJudge(String nickname){
        return userAuthDao.registerJudge(nickname);
    }

    public UserAuth loginJudge(String nickname, String password){
        return userAuthDao.loginJudge(nickname,password);
    }

    public int insert(UserAuth userAuth){
        return userAuthDao.insert(userAuth);
    }

}
