package zq.zforum.forumservice.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zq.zforum.Info;
import zq.zforum.Reply;
import zq.zforum.ReplyToReply;
import zq.zforum.Thread;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import zq.zforum.forumservice.mapper.ForumMapper;

@Service
public class ForumServiceImpl implements ForumService {

    @Autowired
    HttpSession session;

    @Autowired
    InnerAccountService accountService;

    @Autowired
    ForumMapper mapper;

    private String buildCommaSeperatedString(List<String> list) {
        StringBuilder builder = new StringBuilder();
        list.forEach(i -> builder.append(i).append(","));
        if (builder.isEmpty()) return "";
        builder.deleteCharAt(builder.length() - 1);
        return builder.toString();
    }



    private List<String> savePics(HttpServletRequest request) throws IOException, ServletException {
        List<String> pics = new ArrayList<>();
        request.getParts().forEach(s -> {
            if (!s.getName().startsWith("file")) return;
            String uuid = UUID.randomUUID().toString();
            pics.add(uuid);
            File savePic = new File("pics/" + uuid);
            try {
                savePic.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try (OutputStream os = Files.newOutputStream(savePic.toPath())) {
                s.getInputStream().transferTo(os);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return pics;
    }

    @Override
    public List<Thread> getThreads(int startPage) {
        List<Thread> threads = mapper.queryThreads((startPage - 1) * 10);
        threads.forEach(t -> t.setUsername(accountService.getUsernameById(t.getUserId()).info));
        return threads;
    }

    @Override
    public boolean createNewThread(HttpServletRequest request) throws ServletException, IOException {
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        Thread thread = new Thread();
        thread.setTitle(title);
        thread.setText(text);
        List<String> pics = savePics(request);
        thread.setPics(buildCommaSeperatedString(pics));
        thread.setPostDate(LocalDateTime.now());
        thread.setLastReplyDate(LocalDateTime.now());
        thread.setUserId(getUserId());
        accountService.addThreadCount(getUserId());
        mapper.createNewThread(thread);
        return thread.getUserId() != null;
    }

    @Override
    public Thread getThreadInfoById(int id) {
        return mapper.getThreadInfoById(id);
    }

    private Integer getUserId() {
        return (Integer) session.getAttribute("userId");
    }

    @Override
    public boolean replyToThread(HttpServletRequest request) throws ServletException, IOException {
        String text = request.getParameter("text");
        int threadId = Integer.parseInt(request.getParameter("threadId"));
        mapper.addThreadReplyNum(threadId);
        List<String> pics = savePics(request);
        Reply reply = new Reply();
        reply.setText(text);
        reply.setPics(buildCommaSeperatedString(pics));
        reply.setUserId(getUserId());
        reply.setThreadId(threadId);
        mapper.saveReply(reply);
        return true;
    }

    @Override
    public List<Reply> getRepliesByThreadId(int threadId) {
        return mapper.getReplyByThreadId(threadId);
    }

    @Override
    public void getPic(HttpServletResponse response, String picId) throws IOException {
        response.setHeader("Content-Type", "image/jpeg");
        OutputStream os = response.getOutputStream();
        File file = new File("pics/" + picId);
        InputStream is = Files.newInputStream(file.toPath());
        is.transferTo(os);
        is.close();
    }

    @Override
    public void replyToThreadReply(HttpServletRequest request) throws ServletException, IOException {
        String text = request.getParameter("text");
        int replyId = Integer.parseInt(request.getParameter("replyId"));
        List<String> pics = savePics(request);
        ReplyToReply rtr = new ReplyToReply();
        rtr.setText(text);
        rtr.setReplyId(replyId);
        rtr.setPics(buildCommaSeperatedString(pics));
        rtr.setUserId(getUserId());
        mapper.saveReplyToReply(rtr);
    }

    @Override
    public List<ReplyToReply> getReplyToReplyByReplyId(int replyId) {
        return mapper.getReplyToReplyByReplyId(replyId);
    }

    @Override
    public List<Thread> getThreadsByUserId(Integer userId) {
        if (userId == 0) userId = getUserId();
        return mapper.getThreadsByUserId(userId);
    }

    @Override
    public List<Info> getInfo() {
        return mapper.getInfo();
    }
}
