package cn.ecnuer996.service;

import cn.ecnuer996.bean.User;
import cn.ecnuer996.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("UserService")
public class UserService {

    @Autowired
    private UserMapper userDao;

    public User getUserById(int id) {
        return userDao.selectByPrimaryKey(id);
    }

    public User getUserByName(String nickname){
        return userDao.selectByNickname(nickname);
    }

    public int insert(User user){
        return userDao.insertSelective(user);
    }

}
