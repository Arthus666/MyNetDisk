package zm.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import zm.pojo.User;

/**
 * @author Arthus
 */
@Repository
public interface UserDao {

    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    @Select("select * from user where username=#{username}")
    public User queryUserByUsername(String username);

    /**
     * 通过邮箱地址查询用户
     * @param email
     * @return
     */
    @Select("select * from user where email=#{email}")
    public User queryUserByEmail(String email);

    /**
     * 通过ID查询用户
     * @param id
     * @return
     */
    @Select("select * from user where id=#{id}")
    public User queryUserById(String id);

    /**
     * 保存用户到数据库
     * @param user
     */
    @Insert("insert into user values (#{id},#{username},#{password},#{is_admin},#{email})")
    public void insertUser(User user);

}
