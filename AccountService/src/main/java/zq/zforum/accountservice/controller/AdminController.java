package zq.zforum.accountservice.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zq.zforum.R;
import zq.zforum.accountservice.encoder.MD5PasswordEncoder;
import zq.zforum.accountservice.mapper.AdminMapper;

@RestController
@RequestMapping("/api/account/admin")
public class AdminController {

    @Autowired
    AdminMapper mapper;

    @Autowired
    MD5PasswordEncoder encoder;

    @Autowired
    HttpSession session;

    @PostMapping("/login")
    public R<String> adminLogin(String username, String password) {
        String passwordInDb = mapper.getPassword(username);
        if (encoder.match(password, passwordInDb)) {
            session.setAttribute("admin", "1");
            return R.success("OK");
        }
        return R.error("Error!");
    }

}
