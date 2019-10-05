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

}
