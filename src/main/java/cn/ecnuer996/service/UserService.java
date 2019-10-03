package cn.ecnuer996.service;

import cn.ecnuer996.bean.User;
import cn.ecnuer996.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.lang.model.type.NullType;

@Service("UserService")
public class UserService {

    @Autowired
    private UserMapper userDao;

    public User getUserById(int id){
        return userDao.selectByPrimaryKey(id);
    }

    public boolean registerJudge(String nickname){
        User usr = userDao.registerJudge(nickname);
        return usr.getId() != -1;
    }

    public void register(String nickname,String password,String tel,String email) {
        User usr = new User(nickname,password,tel,email);
        userDao.insertSelective(usr);
    }

}
