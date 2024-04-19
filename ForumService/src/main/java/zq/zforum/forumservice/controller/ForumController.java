package zq.zforum.forumservice.controller;

import io.seata.spring.annotation.GlobalTransactional;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zq.zforum.*;
import zq.zforum.Thread;
import zq.zforum.forumservice.search.ElasticThread;
import zq.zforum.forumservice.service.ForumService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumController {

    @Autowired
    ForumService service;

    @PostMapping("/new-thread")
    @GlobalTransactional
    public R<String> newThread(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service.createNewThread(request);
        response.sendRedirect("http://localhost:5173/main/home");
        return R.success("OK");
    }

    @PostMapping("/delete-thread")
    public R<String> deleteThread(Integer id) {
        service.deleteThread(id);
        return R.success("OK");
    }

    @GetMapping("/get-threads-by-user-id")
    public R<List<Thread>> getThreadsByUserId(Integer userId) {
        return R.success(service.getThreadsByUserId(userId));
    }

    @GetMapping("/thread-info")
    public R<Thread> getThreadInfoById(int id) {
        return R.success(service.getThreadInfoById(id));
    }

    @GetMapping("/threads")
    public R<List<Thread>> queryThreads(int page) {
        return R.success(service.getThreads(page));
    }

    @PostMapping("/reply-to-thread")
    public R<String> replyToThread(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service.replyToThread(request);
        response.sendRedirect("http://localhost:5173/main/thread/" + request.getParameter("threadId"));
        return R.success("OK");
    }

    @PostMapping("/reply-to-thread-reply")
    public R<String> replyToThreadReply(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        service.replyToThreadReply(request);
        response.sendRedirect("http://localhost:5173/main/thread/" + request.getParameter("threadId"));
        return R.success("OK");
    }
    @GetMapping("/pic")
    public void getPic(HttpServletResponse response, String picId) throws IOException {
        service.getPic(response, picId);
    }

    @GetMapping("/get-reply-to-reply")
    public R<List<ReplyToReply>> getReplyToReply(int replyId) {
        List<ReplyToReply> rList = service.getReplyToReplyByReplyId(replyId);
        return R.success(rList);
    }

    @GetMapping("/get-reply-by-id")
    public R<List<Reply>> getReplyById(int threadId) {
        return R.success(service.getRepliesByThreadId(threadId));
    }

    @GetMapping("/info")
    public R<List<Info>> getInfo() {
        return R.success(service.getInfo());
    }

    @GetMapping("/carousel")
    public R<List<Carousel>> getCarousel() {
        return R.success(service.getCarousel());
    }

    @GetMapping("/search")
    public R<List<SearchHit<ElasticThread>>> search(String keyword) {
        return R.success(service.search(keyword));
    }


}
