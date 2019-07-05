package com.hongenit.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongenit.domain.ResultInfo;
import com.hongenit.domain.User;
import com.hongenit.service.UserService;
import com.hongenit.service.impl.UserServiceImpl;
import com.mysql.cj.util.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet(name = "UserServlet", urlPatterns = "/user/*")
public class UserServlet extends BaseServlet {
    UserService service = new UserServiceImpl();

    /**
     * 注册功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //1.获取数据
        User user = populateUser(request);

        if (StringUtils.isNullOrEmpty(user.getAccount()) || StringUtils.isNullOrEmpty(user.getPwd())) {
            return;
        }
        //3.调用service完成注册
        ResultInfo info = service.regist(user);


        //将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        //将json数据写回客户端
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

    }


    /**
     * 登录功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取数据
        User user = populateUser(request);

        if (StringUtils.isNullOrEmpty(user.getAccount()) || StringUtils.isNullOrEmpty(user.getPwd())) {
            return;
        }
        //3.调用service完成注册
        ResultInfo info = service.login(user);

        //将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json数据写回客户端
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }


    /**
     * 登录功能
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void fetch_my_info(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取数据
        User user = populateUser(request);

        if (StringUtils.isNullOrEmpty(user.getToken()) || user.getUser_id() <= 0) {
            return;
        }
        //3.调用service完成注册
        ResultInfo info = service.fetch_my_info(user);

        //将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //将json数据写回客户端
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }


    /**
     * 更新用户信息
     *
     * @throws ServletException
     * @throws IOException
     */
    public void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = populateUser(request);
        if (StringUtils.isNullOrEmpty(user.getNickname()) || user.getUser_id() <= 0) {
            return;
        }
        //3.调用service完成注册
        ResultInfo info = service.updateNickname(user);
        //将json数据写回客户端
        writeValue(info, response);

    }

    private User populateUser(HttpServletRequest request) {
        //1.获取数据
        Map<String, String[]> map = request.getParameterMap();

        //2.封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return user;
    }


}
