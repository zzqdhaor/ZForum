package zq.zforum.accountservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zq.zforum.UserBasicInfo;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("select password from admin where id=#{id}")
    String getPassword(String id);

    @Select("select * from user")
    List<UserBasicInfo> getAllUserInfo();

    @Select("select * from user where username like #{username}")
    List<UserBasicInfo> getUserInfoByUsername(String username);

    @Select("select * from user where id=#{id}")
    List<UserBasicInfo> getUserInfoById(Integer id);

    @Update("update user set password=#{password} where id=#{id}")
    void changePassword(String password, String id);
}
