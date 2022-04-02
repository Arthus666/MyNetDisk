package zm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zm.dao.UserDao;
import zm.pojo.User;
import zm.service.UserService;


/**
 * @author Arthus
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findUserByUsername(String username) {
        return userDao.queryUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userDao.queryUserByEmail(email);
    }

    @Override
    public User findUserById(String id) {
        return userDao.queryUserById(id);
    }

    @Override
    public void saveUser(User user) {
        userDao.insertUser(user);
    }
}
