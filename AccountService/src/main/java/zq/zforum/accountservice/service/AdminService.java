package zq.zforum.accountservice.service;

import org.apache.catalina.User;
import zq.zforum.UserBasicInfo;

import java.util.List;

public interface AdminService {


    List<UserBasicInfo> getUserInfo(Integer id, String username);

    void changePassword(String password, String id);
}
