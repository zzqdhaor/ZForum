package zq.zforum.accountservice.controller;

import io.seata.spring.annotation.GlobalTransactional;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import zq.zforum.R;
import zq.zforum.UserBasicInfo;
import zq.zforum.accountservice.service.UserInfoService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user-info")
public class UserInfoController {

    @Autowired
    UserInfoService service;

    @PostMapping("/icon")
    public R<String> uploadIcon(MultipartFile file) throws IOException {
        boolean result = service.uploadIcon(file);
        return basicStringReply(result);
    }

    @GetMapping("/get-icon")
    public void getIcon(HttpServletResponse resp, Integer userId) throws IOException {
        service.getIcon(resp, userId);
    }

    @GetMapping("/get-basic-info")
    public R<UserBasicInfo> getBasicInfo(Integer userId, HttpSession session) {
        if (userId == 0)
            return R.success(service.getUserInfo((Integer) session.getAttribute("userId")));
        return R.success(service.getUserInfo(userId));
    }

    @PostMapping("/watch")
    @GlobalTransactional
    public R<String> watch(Integer toWatchUser) {
        service.changeWatchState(toWatchUser);
        return basicStringReply(true);
    }

    @GetMapping("/has-watch")
    public R<Boolean> hasWatch(Integer otherUser) {
        return R.success(service.hasWatch(otherUser));
    }


    @GetMapping("/get-fans")
    public R<List<UserBasicInfo>> getFans(Integer userId) {
        return R.success(service.getFans(userId));
    }

    @GetMapping("/get-watching")
    public R<List<UserBasicInfo>> getWatching(Integer userId) {
        return R.success(service.getWatching(userId));
    }

    public R<String> basicStringReply(boolean ok) {
        if (ok) return R.success("OK");
        return R.error("Failed");
    }
}