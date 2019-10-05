package cn.ecnuer996.controller;

import cn.ecnuer996.bean.User;
import cn.ecnuer996.service.UserService;
import com.sun.mail.imap.protocol.INTERNALDATE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

}
