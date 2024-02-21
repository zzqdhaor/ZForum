package zq.zforum.accountservice.mapper;

import org.apache.ibatis.annotations.*;
import zq.zforum.UserBasicInfo;

import java.util.List;

@Mapper
public interface AccountMapper {

    @Select("select id from user where username=#{username} and password=#{password}")
    Integer login(String username, String password);

    @Insert("insert into user(username,password) values (#{username},#{password})")
    int register(String username, String password);

    @Select("select password from user where username=#{username}")
    String getPassword(String username);

    @Select("select id from user where username=#{username}")
    Integer getId(String username);

    @Select("select username from user where id=#{id}")
    String getUsername(Integer id);

    @Update("update user set threadCount = threadCount + 1 where id=#{userId}")
    void addThreadCount(int userId);

    @Select("select username,threadCount,fans,watching from user where id=#{userId}")
    UserBasicInfo getUserInfo(Integer userId);

    @Insert("insert into watch values(#{userId}, #{toWatchUser})")
    void watch(Integer userId, Integer toWatchUser);

    @Delete("delete from watch where watcher=#{userId} and watched=#{toUnwatchUser}")
    void unWatch(Integer userId, Integer toUnwatchUser);

    @Select("select count(*) from watch where watcher=#{userId} and watched=#{toWatchUser}")
    int hasWatch(Integer userId, Integer toWatchUser);

    @Update("update user set fans=fans+1 where id=#{id}")
    void addFansCount(Integer id);

    @Update("update user set fans=fans-1 where id=#{id}")
    void minusFansCount(Integer id);

    @Update("update user set watching=watching+1 where id=#{id}")
    void addWatchingCount(Integer id);

    @Update("update user set watching=watching-1 where id=#{id}")
    void minusWatchingCount(Integer id);

    @Select("select * from user join watch on watch.watched=user.id where watch.watcher=#{userId}")
    List<UserBasicInfo> getWatching(Integer userId);

    @Select("select * from user join watch on watch.watcher=user.id where watch.watched=#{userId}")
    List<UserBasicInfo> getFans(Integer userId);

}
