package zq.zforum.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zq.zforum.R;
import zq.zforum.UserBasicInfo;
import zq.zforum.accountservice.service.AdminService;
import zq.zforum.accountservice.service.LoginService;
import zq.zforum.accountservice.service.UserInfoService;

import java.util.List;
@RestController
@RequestMapping("/api/account/admin")
public class AdminManageController {

    @Autowired
    private AdminService service;

    @GetMapping("/user-info")
    public R<List<UserBasicInfo>> getUserInfo(Integer id, String username) {
        return R.success(service.getUserInfo(id, username));
    }

    @PostMapping("/change-password")
    public R<String> changePassword(String password, String id) {
        service.changePassword(password, id);
        return R.success("OK");
    }
}