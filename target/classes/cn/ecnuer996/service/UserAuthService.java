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
        return userAuthDao.insertSelective(userAuth);
    }

    //Ŀǰֻ����nickname�ĵ�¼��ʽ�������ݿ���Ƴɿ���չ������¼��ʽ����phone,wechat,weibo��
    //�˺���Ѱ��nickname��½��ʽ����֤��Ϣ
    public UserAuth getNicknameLoginAuth(Integer id){
        return userAuthDao.selectNicknameLoginAuth(id);
    }

    public UserAuth getBySignInMethod(String method,String identifier){
        return userAuthDao.selectBySignInMethod(method,identifier);
    }
}
