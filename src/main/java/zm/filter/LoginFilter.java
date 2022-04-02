package zm.filter;

import com.alibaba.druid.support.ibatis.SpringIbatisBeanNameAutoProxyCreator;
import org.springframework.util.DigestUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Arthus
 */
public class LoginFilter implements Filter {

    private String excludePaths;

    @Override
    public void  init(FilterConfig filterConfig) throws ServletException {
        excludePaths = filterConfig.getInitParameter("excludePaths");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String[] paths = excludePaths.split(";");

        for (int i = 0; i < paths.length; i++) {
            if (paths[i] == null || "".equals(paths[i])) {
                continue;
            }
            if (request.getRequestURI().indexOf(paths[i]) != -1) {
                //如果是不过滤的请求，便放行
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }
        }

        Cookie[] cookies = request.getCookies();
        Cookie cookieWord = null,cookieId = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("word".equals(cookie.getName())) {
                    cookieWord = cookie;
                }
                if ("id".equals(cookie.getName())) {
                    cookieId = cookie;
                }
            }
        }

        if (cookieWord == null || cookieId == null) {
            request.getRequestDispatcher("/login").forward(request, response);
        } else {

            String id = cookieId.getValue();
            String word = DigestUtils.md5DigestAsHex(("FuckYou!" + id).getBytes());

            if (word.equals(cookieWord.getValue())) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                request.getRequestDispatcher("/login").forward(request, response);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
