package zq.zforum.forumservice.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import zq.zforum.*;
import zq.zforum.Thread;

import java.io.IOException;
import java.util.List;

public interface ForumService {

    List<Thread> getThreads(int startPage);

    boolean createNewThread(HttpServletRequest request) throws ServletException, IOException;

    Thread getThreadInfoById(int id);

    boolean replyToThread(HttpServletRequest request) throws ServletException, IOException;

    List<Reply> getRepliesByThreadId(int threadId);

    void getPic(HttpServletResponse response, String picId) throws IOException;

    void replyToThreadReply(HttpServletRequest request) throws ServletException, IOException;

    List<ReplyToReply> getReplyToReplyByReplyId(int replyId);

    List<Thread> getThreadsByUserId(Integer userId);

    List<Info> getInfo();

    void deleteThread(int id);

    List<Carousel> getCarousel();
}
