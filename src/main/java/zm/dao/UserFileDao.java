package zm.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import zm.pojo.UserFile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthus
 */
@Repository
public interface UserFileDao {

    /**
     * 保存用户文件到数据库
     *
     * @param userFile
     */
    @Insert("insert into user_file values (#{id},#{user_id},#{file_name},#{file_time},#{file_type},#{file_icon},#{file_path},#{file_size},#{file_link})")
    public void insertUserFile(UserFile userFile);

    /**
     * 查询用户所有文件
     * @param user_id
     * @return
     */
    @Select("select * from user_file where user_id=#{user_id}")
    public ArrayList<UserFile> selectAllUserFiles(String user_id);

    /**
     * 删除指定id的文件
     * @param file_id
     */
    @Select("delete from user_file where id=#{file_id}")
    public void deleteUserFile(String file_id);
}
