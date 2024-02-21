package zq.zforum.forumservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import zq.zforum.R;

@FeignClient(name = "account-service")
public interface InnerAccountService {

    @PostMapping("/api/account/inner/add-thread-count/{userId}")
    void addThreadCount(@PathVariable Integer userId);

    @GetMapping("/api/account/inner/get-username-by-id/{id}")
    R<String> getUsernameById(@PathVariable int id);

}
