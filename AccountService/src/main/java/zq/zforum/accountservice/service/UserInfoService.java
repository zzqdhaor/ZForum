package zq.zforum.accountservice.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;
import zq.zforum.UserBasicInfo;

import java.io.IOException;
import java.util.List;

public interface UserInfoService {

    void changeWatchState(Integer toWatchUser);

    boolean hasWatch(Integer otherUser);

    void getIcon(HttpServletResponse response, Integer userId) throws IOException;
    boolean uploadIcon(MultipartFile file) throws IOException;
    String getUsernameById(int id);

    boolean addThreadCount(int userId);

    UserBasicInfo getUserInfo(Integer userId);

    List<UserBasicInfo> getWatching(Integer userId);

    List<UserBasicInfo> getFans(Integer userId);

}
