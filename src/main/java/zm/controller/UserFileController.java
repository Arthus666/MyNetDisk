package zm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import zm.pojo.User;
import zm.pojo.UserFile;
import zm.service.UserFileService;
import zm.service.UserService;
import zm.utils.DoIcons;
import zm.utils.UnitConverter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Arthus
 */
@Controller
public class UserFileController {

    @Autowired
    private UserFileService userFileService;

    @Autowired
    private UserService userService;

    @RequestMapping("/fileUpload")
    @ResponseBody
    public JSONObject fileUpload(HttpServletRequest request, MultipartFile file) throws IOException {

        JSONObject jsonObject = new JSONObject();

        //若文件为空，则直接返回提示
        if (file == null) {
            jsonObject.put("result", 0);
            jsonObject.put("message", "请选择文件!");
            return jsonObject;
        }

        Cookie[] cookies = request.getCookies();

        String userId = new String("");
        //获取Cookie中的用户id
        for (Cookie cookie : cookies) {
            if (cookie != null && "id".equals(cookie.getName())) {
                userId = cookie.getValue();
                break;
            }
        }

        //通过userId获取用户
        User user = userService.findUserById(userId);

        //获取文件名
        String fileName = file.getOriginalFilename();
        //处理文件重名问题
        String hzName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID().toString() + hzName;

        //获取保存路径
        File theFile = new File("D:\\FileUpload" + File.separator + user.getUsername());
        if (!theFile.exists()) {
            theFile.mkdir();
        }

        String finalPath = "D:\\FileUpload" + File.separator + user.getUsername() + File.separator + fileName;

        //上传到指定路径
        file.transferTo(new File(finalPath));

        //格式化当前日期和时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = dateFormat.format(new Date(System.currentTimeMillis()));

        //生成下载文件的链接
        String fileLink = "/MyNetDisk/fileDownload?username=" + user.getUsername() + "&&filename=" + fileName;

        //将文件字节大小转换为B,KB,MB,GB
        String fileSize = UnitConverter.convert(file.getSize());

        //通过文件类型获取图标属性
        String icon = DoIcons.getIcon(file.getContentType());

        //在数据库中保存file的信息
        userFileService.saveUserFile(new UserFile(0, user.getId(), file.getOriginalFilename(), dateTime, file.getContentType(), icon, finalPath, fileSize, fileLink));

        System.out.println(file.toString());

        jsonObject.put("result", 1);
        jsonObject.put("message", "上传成功!");

        return jsonObject;
    }

    @RequestMapping("/fileDownload")
    @ResponseBody
    public ResponseEntity fileDownload(String username, String filename) throws IOException {

        String realPath = "D:\\FileUpload\\" + username + "\\" + filename;

        //创建输入流
        InputStream inputStream = new FileInputStream(realPath);

        //创建字节数组
        byte[] bytes = new byte[inputStream.available()];

        //将流读到字节数组中
        inputStream.read(bytes);

        //创建HttpHeaders对象设置响应头信息
        MultiValueMap<String, String> headers = new HttpHeaders();

        //设置要下载方式以及下载文件的名字
        headers.add("Content-Disposition", "attachment;filename=" + filename);

        //设置响应状态码
        HttpStatus statusCode = HttpStatus.OK;

        //创建ResponseEntity对象
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(bytes, headers, statusCode);

        inputStream.close();

        return responseEntity;
    }

    @RequestMapping("/getFiles")
    @ResponseBody
    public List<UserFile> getFiles(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        String user_id = "";
        for (Cookie cookie : cookies) {
            if (cookie != null && "id".equals(cookie.getName())) {
                user_id = cookie.getValue();
                break;
            }
        }
        //通过Cookie中的user_id获取该用户的所有文件
        List<UserFile> userFiles = userFileService.findAllUserFiles(user_id);

        return userFiles;
    }

    @RequestMapping("/removeFile")
    @ResponseBody
    public JSONObject removeFile(String username, String filename,String file_id) {

        JSONObject jsonObject = new JSONObject();
        if (username == null || filename == null || file_id == null) {
            jsonObject.put("result", 0);
            jsonObject.put("message", "请选择文件！");
            return jsonObject;
        }

        //删除数据库中的文件
        userFileService.removeUserFile(file_id);

        //删除磁盘中的文件
        File file = new File("D:\\FileUpload" + File.separator + username + File.separator + filename);
        if(file.exists() && file.isFile()){
            file.delete();
        }

        jsonObject.put("result", 1);
        jsonObject.put("message", "删除成功！");
        return jsonObject;

    }
}
