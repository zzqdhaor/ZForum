package zq.zforum.accountservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zq.zforum.UserBasicInfo;
import zq.zforum.accountservice.encoder.MD5PasswordEncoder;
import zq.zforum.accountservice.mapper.AdminMapper;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper mapper;

    @Autowired
    private MD5PasswordEncoder encoder;
    @Override
    public List<UserBasicInfo> getUserInfo(Integer id, String username) {
        if (id == null && username == null) return mapper.getAllUserInfo();
        if (id != null) return mapper.getUserInfoById(id);
        else return mapper.getUserInfoByUsername(username);
    }

    @Override
    public void changePassword(String password, String id) {
        mapper.changePassword(encoder.encode(password), id);
    }
}
