package zq.zforum.accountservice.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zq.zforum.accountservice.encoder.PasswordEncoder;
import zq.zforum.accountservice.mapper.AccountMapper;
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    HttpSession session;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AccountMapper mapper;

    @Override
    public boolean login(String username, String password) {
        String passwordInDb = mapper.getPassword(username);
        if (encoder.match(password, passwordInDb)) {
            Integer id = mapper.getId(username);
            session.setAttribute("userId", id);
            session.setAttribute("username", username);
            return true;
        }
        return false;
    }

    public boolean register(String username, String password) {
        return mapper.register(username, encoder.encode(password)) == 1;
    }

    @Override
    public String getUsername(HttpServletRequest request) {
        return (String) session.getAttribute("username");
    }

}
