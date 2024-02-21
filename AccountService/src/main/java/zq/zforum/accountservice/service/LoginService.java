package zq.zforum.accountservice.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

public interface LoginService {

    boolean login(String username, String password);

    boolean register(String username, String password);

    String getUsername(HttpServletRequest request);
}
