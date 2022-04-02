package zm.utils;

import com.sun.org.apache.xalan.internal.xsltc.dom.SimpleResultTreeImpl;
import sun.security.krb5.internal.ETypeInfo;

/**
 * @author Arthus
 */
public class DoIcons {

    public static String getIcon(String type) {

        String file_icon;

        if (type.indexOf("image") != -1) {
            file_icon = "glyphicon glyphicon-picture";
        } else if (type.indexOf("audio")!=-1) {
            file_icon = "glyphicon glyphicon-music";
        } else if (type.indexOf("video") != -1) {
            file_icon = "glyphicon glyphicon-film";
        } else {
            file_icon = "glyphicon glyphicon-file";
        }
        return file_icon;

    }

}
