package zm.service;

import zm.pojo.UserFile;

import java.util.ArrayList;

/**
 * @author Arthus
 */
public interface UserFileService {

    /**
     * 保存用户文件
     * @param userFile
     */
    public void saveUserFile(UserFile userFile);

    /**
     * 查询用户所有文件
     * @param user_id
     * @return
     */
    public ArrayList<UserFile> findAllUserFiles(String user_id);

    /**
     * 删除指定id的文件
     * @param file_id
     */
    public void removeUserFile(String file_id);
}
