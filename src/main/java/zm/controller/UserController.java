package zm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zm.pojo.User;
import zm.service.UserService;
import zm.utils.Semail;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthus
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/userLogin")
    @ResponseBody
    public JSONObject userLogin(HttpServletResponse response, HttpSession session, @RequestBody String jsonStr) {

        JSONObject jsonObject = JSONObject.parseObject(jsonStr);

        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String captcha = jsonObject.getString("captcha");

        //图形验证码
        String token = session.getAttribute("captcha").toString();

        if ("".equals(username) || "".equals(password) || "".equals(captcha)) {
            jsonObject.put("result", 0);
            jsonObject.put("message", "输入框不能为空！");
        } else if (captcha.equals(token.toUpperCase()) == false) {
            jsonObject.put("result", 0);
            jsonObject.put("message", "验证码错误！");
        } else {
            if (username.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$")) {
                //邮箱登录
                User user = userService.findUserByEmail(username);
                if (user == null || !user.getPassword().equals(password)) {
                    jsonObject.put("result", 0);
                    jsonObject.put("message", "用户名或密码错误！");
                } else {
                    //对用户ID进行md5加密
                    String word = DigestUtils.md5DigestAsHex(("FuckYou!" + user.getId()).getBytes());
                    Cookie cookieWord = new Cookie("word", word);

                    //设置Cookie生命周期为三小时
                    cookieWord.setMaxAge(10800);
                    response.addCookie(cookieWord);

                    Cookie cookieId = new Cookie("id", String.valueOf(user.getId()));
                    //设置Cookie生命周期为三小时
                    cookieWord.setMaxAge(10800);
                    response.addCookie(cookieId);

                    jsonObject.put("result", 1);
                    jsonObject.put("message", "登录成功！");
                }
            } else {
                //用户名登录
                User user = userService.findUserByUsername(username);
                if (user == null || user.getPassword().equals(password) == false) {
                    jsonObject.put("result", 0);
                    jsonObject.put("message", "用户名或密码错误！");
                } else {

                    //对用户ID进行md5加密
                    String word = DigestUtils.md5DigestAsHex(("FuckYou!" + user.getId()).getBytes());
                    Cookie cookieWord = new Cookie("word", word);
                    //设置Cookie生命周期为三小时
                    cookieWord.setMaxAge(10800);
                    response.addCookie(cookieWord);

                    Cookie cookieId = new Cookie("id", String.valueOf(user.getId()));
                    //设置Cookie生命周期为三小时
                    cookieWord.setMaxAge(10800);
                    response.addCookie(cookieId);

                    jsonObject.put("result", 1);
                    jsonObject.put("message", "登录成功！");
                }
            }
        }
        return jsonObject;
    }

    @RequestMapping("/userLogon")
    @ResponseBody
    public JSONObject userLogon(HttpSession session, @RequestBody String jsonStr) {

        JSONObject jsonObject = JSONObject.parseObject(jsonStr);

        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        String confirm = jsonObject.getString("confirm");
        String email = jsonObject.getString("email");
        String captcha = jsonObject.getString("captcha");

        //获取session域对象中的邮箱验证码
        String vertiyCode = null;
        if (session.getAttribute("vertiyCode") != null) {
            vertiyCode = session.getAttribute("vertiyCode").toString();
        }

        if ("".equals(username) || "".equals(password) || "".equals(confirm) || "".equals(email) || "".equals(captcha)) {
            jsonObject.put("result", 0);
            jsonObject.put("message", "输入框不能为空！");
        } else if (username.matches("^[a-zA-Z0-9_]{3,16}$") == false) {
            jsonObject.put("result", 0);
            jsonObject.put("message", "用户名为英文字母、数字、下划线、长度3~16位！");
        } else if (password.matches("^[a-zA-Z0-9_]{6,16}$") == false) {
            jsonObject.put("result", 0);
            jsonObject.put("message", "密码为英文字母、数字、下划线、长度6~16位！");
        } else if (password.equals(confirm) == false) {
            jsonObject.put("result", 0);
            jsonObject.put("message", "确认密码不一致！");
        } else if (email.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$") == false) {
            jsonObject.put("result", 0);
            jsonObject.put("message", "邮箱格式错误！");
        } else if (vertiyCode == null || captcha.equals(vertiyCode) == false) {
            jsonObject.put("result", 0);
            jsonObject.put("message", "验证码错误！");
        } else if (userService.findUserByUsername(username) != null) {
            jsonObject.put("result", 0);
            jsonObject.put("message", "该用户名已存在！");
        } else if (userService.findUserByEmail(email) != null) {
            jsonObject.put("result", 0);
            jsonObject.put("message", "该邮箱已被注册！");
        } else {

            //保存用户
            userService.saveUser(new User(0, username, password, 0, email));

            jsonObject.put("result", 1);
            jsonObject.put("message", "注册成功！");
        }
        return jsonObject;
    }

    @RequestMapping("/getEmailToken")
    @ResponseBody
    public JSONObject getToken(HttpSession session, @RequestBody String jsonStr) {

        //获取json字符串中的email
        String email = JSONObject.parseObject(jsonStr).getString("email");

        JSONObject jsonObject = new JSONObject();

        if ("".equals(email) || email.matches("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$") == false) {

            jsonObject.put("result", 0);
            jsonObject.put("message", "邮箱格式错误！");

        } else {

            //随机生成六位数字验证码
            int vertiyCode = (int) ((Math.random() * 9 + 1) * 100000);

            System.out.println("获取邮箱：" + email + " 发送验证码：" + vertiyCode);

            //发送验证码
            Semail.sendEmail(email, vertiyCode);

            session.setAttribute("vertiyCode", vertiyCode);

            jsonObject.put("result", 1);
            jsonObject.put("message", "获取验证码成功");
        }

        return jsonObject;

    }

    @RequestMapping("/exitLogin")
    @ResponseBody
    public JSONObject exitLogin(HttpServletRequest request, HttpServletResponse response) {

        JSONObject jsonObject = new JSONObject();

        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {

            if ("word".equals(cookie.getName())) {
                //将Cookie删除
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            if ("id".equals(cookie.getName())) {
                //将Cookie删除
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }

        jsonObject.put("result", 1);
        jsonObject.put("message", "已退出登录！");
        return jsonObject;
    }

    @RequestMapping("/getUserInformation")
    @ResponseBody
    public JSONObject getUserInformation(HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();

        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if ("id".equals(cookie.getName())) {

                User user = userService.findUserById(cookie.getValue());
                jsonObject.put("result", 1);
                jsonObject.put("message", "获取账户信息成功!");
                jsonObject.put("user", user);
                break;

            }
        }

        return jsonObject;
    }
}
