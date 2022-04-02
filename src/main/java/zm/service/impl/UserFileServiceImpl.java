package zm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zm.dao.UserFileDao;
import zm.pojo.User;
import zm.pojo.UserFile;
import zm.service.UserFileService;

import java.util.ArrayList;

/**
 * @author Arthus
 */
@Service("userFileService")
public class UserFileServiceImpl implements UserFileService {

    @Autowired
    private UserFileDao userFileDao;

    @Override
    public void saveUserFile(UserFile userFile) {
        userFileDao.insertUserFile(userFile);
    }

    @Override
    public ArrayList<UserFile> findAllUserFiles(String user_id) {
        return userFileDao.selectAllUserFiles(user_id);
    }

    @Override
    public void removeUserFile(String file_id) {
        userFileDao.deleteUserFile(file_id);
    }

}
