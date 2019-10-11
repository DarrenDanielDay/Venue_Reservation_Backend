package cn.ecnuer996.dao;

import cn.ecnuer996.bean.UserAuth;

public interface UserAuthMapper {
    int insert(UserAuth record);

    int insertSelective(UserAuth record);

    UserAuth registerJudge(String nickname);

    UserAuth loginJudge(String nickname, String password);

    //此函数寻找nickname登陆方式的验证信息
    public UserAuth selectNicknameLoginAuth(Integer id);
    //根据登录方式和对应ID来获取验证信息
    public UserAuth selectBySignInMethod(String method,String identifier);
}