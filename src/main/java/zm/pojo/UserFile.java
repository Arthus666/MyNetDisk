package zm.pojo;

import java.util.Arrays;

/**
 * @author Arthus
 */
public class UserFile {

    private int id;
    private int user_id;
    private String file_name;
    private String file_time;
    private String file_type;
    private String file_icon;
    private String file_path;
    private String file_size;
    private String file_link;

    public UserFile(int id, int user_id, String file_name, String file_time, String file_type, String file_icon, String file_path, String file_size, String file_link) {
        this.id = id;
        this.user_id = user_id;
        this.file_name = file_name;
        this.file_time = file_time;
        this.file_type = file_type;
        this.file_icon = file_icon;
        this.file_path = file_path;
        this.file_size = file_size;
        this.file_link = file_link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_time() {
        return file_time;
    }

    public void setFile_time(String file_time) {
        this.file_time = file_time;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFile_icon() {
        return file_icon;
    }

    public void setFile_icon(String file_icon) {
        this.file_icon = file_icon;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public String getFile_link() {
        return file_link;
    }

    public void setFile_link(String file_link) {
        this.file_link = file_link;
    }

    @Override
    public String toString() {
        return "UserFile{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", file_name='" + file_name + '\'' +
                ", file_time='" + file_time + '\'' +
                ", file_type='" + file_type + '\'' +
                ", file_icon='" + file_icon + '\'' +
                ", file_path='" + file_path + '\'' +
                ", file_size='" + file_size + '\'' +
                ", file_link='" + file_link + '\'' +
                '}';
    }
}
