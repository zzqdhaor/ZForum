package zq.zforum.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zq.zforum.R;
import zq.zforum.accountservice.service.UserInfoService;

@RestController
@RequestMapping("/api/account/inner")
public class InnerUserInfoController {

    @PostMapping("add-thread-count/{userId}")
    public R<String> addThreadCount(@PathVariable int userId) {
        service.addThreadCount(userId);
        return R.success("OK");
    }

    @Autowired
    UserInfoService service;

    @GetMapping("/get-username-by-id/{id}")
    public R<String> getUsernameById(@PathVariable("id") int id) {
        return R.success(service.getUsernameById(id));
    }

}
