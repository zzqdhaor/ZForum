package zq.zforum.forumservice.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import zq.zforum.Info;
import zq.zforum.Reply;
import zq.zforum.ReplyToReply;
import zq.zforum.Thread;

import java.util.List;

@Mapper
public interface ForumMapper {

    @Insert("insert into thread(userId,postDate,text,title,pics,lastReplyDate, replyNum) values (#{userId},#{postDate},#{text},#{title},#{pics},#{lastReplyDate},0)")
    void createNewThread(Thread thread);

    @Select("select * from thread order by postDate desc limit #{page},10")
    List<Thread> queryThreads(int startPage);

    @Select("select thread.id as id,username,userId,text,title,pics from thread inner join user on thread.userId=user.id where thread.id=#{id}")
    Thread getThreadInfoById(int id);

    @Insert("insert into reply values (#{id},#{threadId},#{text},#{pics},#{userId})")
    void saveReply(Reply reply);

    @Select("select user.username as username,text,pics,userId,reply.id as id from reply inner join user on user.id=reply.userId where threadId=#{threadId}")
    List<Reply> getReplyByThreadId(int threadId);

    @Insert("insert into reply_to_reply(replyId,text,pics,userId) values (#{replyId},#{text},#{pics},#{userId})")
    void saveReplyToReply(ReplyToReply rtr);

    @Update("update thread set replyNum=replyNum+1 where id=#{threadId}")
    int addThreadReplyNum(int threadId);

    @Select("select user.username as username,text,pics,userId,reply_to_reply.id as id from reply_to_reply inner join user on user.id=reply_to_reply.userId where replyId=#{replyId}")
    List<ReplyToReply> getReplyToReplyByReplyId(int replyId);

    @Select("select * from thread where userId=#{userId} order by postDate desc")
    List<Thread> getThreadsByUserId(Integer userId);

    @Select("select * from info order by pubDate desc")
    List<Info> getInfo();
}
