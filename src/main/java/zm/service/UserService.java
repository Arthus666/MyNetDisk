package zm.service;

import zm.pojo.User;

/**
 * @author Arthus
 */
public interface UserService {

    /**
     * 用户名查询用户
     * @param username
     * @return
     */
    public User findUserByUsername(String username);

    /**
     * 邮箱查询用户
     * @param email
     * @return
     */
    public User findUserByEmail(String email);

    /**
     * ID查询用户
     * @param id
     * @return
     */
    public User findUserById(String id);

    /**
     * 保存用户
     * @param user
     */
    public void saveUser(User user);

}
