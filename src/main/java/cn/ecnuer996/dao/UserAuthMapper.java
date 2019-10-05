package cn.ecnuer996.dao;

import cn.ecnuer996.bean.UserAuth;

public interface UserAuthMapper {
    int insert(UserAuth record);

    int insertSelective(UserAuth record);

    UserAuth login(String nickname, String password);
}