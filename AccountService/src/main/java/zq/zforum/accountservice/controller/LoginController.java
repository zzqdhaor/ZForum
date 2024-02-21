package zq.zforum.accountservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zq.zforum.R;
import zq.zforum.accountservice.encoder.PasswordEncoder;
import zq.zforum.accountservice.service.LoginService;

@RestController
@RequestMapping("/api/account")
public class LoginController {

    @Autowired
    private LoginService service;


    @PostMapping("/login")
    public R<String> login(String username, String password) {
        boolean result = service.login(username, password);
        if (result) return R.success("OK");
        else return R.error("Error");
    }

    @PostMapping("/register")
    public R<String> register(String username, String password) {
        boolean result = service.register(username, password);
        if (result) return R.success("OK");
        else return R.error("Error");
    }

    @GetMapping("/logout")
    public R<String> logout(HttpSession session) {
        session.invalidate();
        return R.success("OK");
    }

    @GetMapping("/get-username")
    public String getUsername(HttpServletRequest request) {
        return service.getUsername(request);
    }

}
