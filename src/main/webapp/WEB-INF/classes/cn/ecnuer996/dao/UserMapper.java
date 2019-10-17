package cn.ecnuer996.dao;

import cn.ecnuer996.bean.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByNickname(String nickname);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}