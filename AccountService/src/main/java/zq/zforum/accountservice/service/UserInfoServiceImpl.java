package zq.zforum.accountservice.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zq.zforum.UserBasicInfo;
import zq.zforum.accountservice.mapper.AccountMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    HttpSession session;

    @Autowired
    AccountMapper mapper;

    public boolean uploadIcon(MultipartFile file) throws IOException {
        Integer userId = getUserId();
        File fileLocalDir = new File("icons");
        if (!fileLocalDir.exists())
            fileLocalDir.mkdir();
        File fileLocal = new File("icons/" + userId);
        if (fileLocal.exists()) fileLocal.delete();
        file.getInputStream().transferTo(Files.newOutputStream(fileLocal.toPath()));
        return true;
    }

    @Override
    public String getUsernameById(int id) {
        return mapper.getUsername(id);
    }

    @Override
    public boolean addThreadCount(int userId) {
        mapper.addThreadCount(userId);
        return true;
    }

    @Override
    public UserBasicInfo getUserInfo(Integer userId) {
        return mapper.getUserInfo(userId);
    }

    @Override
    public List<UserBasicInfo> getWatching(Integer userId) {
        if (userId == 0) userId = getUserId();
        return mapper.getWatching(userId);
    }

    @Override
    public List<UserBasicInfo> getFans(Integer userId) {
        if (userId == 0) userId = getUserId();
        return mapper.getFans(userId);
    }

    @Override
    public void changeWatchState(Integer toWatchUser) {
        if (mapper.hasWatch(getUserId(), toWatchUser) >= 1) {
            mapper.unWatch(getUserId(), toWatchUser);
            mapper.minusWatchingCount(getUserId());
            mapper.minusFansCount(toWatchUser);
        }
        else {
            mapper.watch(getUserId(), toWatchUser);
            mapper.addWatchingCount(getUserId());
            mapper.addFansCount(toWatchUser);
        }
    }

    @Override
    public boolean hasWatch(Integer otherUser) {
        return mapper.hasWatch(getUserId(), otherUser) == 1;
    }

    private Integer getUserId() {
        return Integer.parseInt(session.getAttribute("userId").toString());
    }

    public void getIcon(HttpServletResponse response,Integer userId) throws IOException {
        if (userId == null || userId == 0)
            userId = getUserId();
        File icon = new File("icons/" + userId);
        response.setHeader("Content-Type", "image/jpeg");
        response.getOutputStream().write(Files.newInputStream(icon.toPath()).readAllBytes());
    }

}
