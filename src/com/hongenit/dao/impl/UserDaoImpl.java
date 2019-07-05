package com.hongenit.dao.impl;

import com.hongenit.dao.UserDao;
import com.hongenit.util.JDBCUtils;
import com.hongenit.util.Md5Utitl;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import com.hongenit.domain.User;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByAccount(String account) {
        User user = null;
        try {
            //1.定义sql
            String sql = "select * from users where account = ?";
            //2.执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), account);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User findByName(String name) {
        User user = null;
        try {
            //1.定义sql
            String sql = "select * from users where nickname = ?";
            //2.执行sql
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findByUserId(int userId) {
        User user = null;
        try {
            String sql = "select * from users where user_id = ?";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User save(User user) {

        //user_id, nickname, followings_count, followers_count, feeds_count,is_following, description, gender,birthday, avatar, account, pwd,create_time;
        // 插入用户数据
        String sql = "insert into users(user_id, nickname, followings_count, followers_count, feeds_count,is_following, description, gender,birthday, avatar, account, pwd,create_time,token) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        //2.执行sql
        long currentTimeMillis = System.currentTimeMillis();
        int update = template.update(sql, user.getUser_id(),
                user.getNickname(),
                user.getFollowings_count(),
                user.getFollowers_count(),
                user.getFeeds_count(),
                user.isIs_following(),
                user.getDescription(),
                user.getGender(),
                user.getBirthday(),
                user.getAvatar(),
                user.getAccount(),
                user.getPwd(),
                currentTimeMillis,
                Md5Utitl.genToken(user.getAccount(), user.getPwd(), String.valueOf(currentTimeMillis))
        );

        System.out.println("save update = " + update + " user= " + user);
        User insertUser = null;
        if (update > 0) {
            insertUser = findByAccount(user.getAccount());
            System.out.println(" user = " + insertUser);
        }


        return insertUser;
    }


    @Override
    public boolean update(User user) {
        // 更新用户信息  update 表名 set 列名1 = 值1, 列名2 = 值2,... [where 条件];
        String sql = "update users set nickname= ? , followings_count= ? , followers_count= ? , feeds_count = ? ,is_following= ? , description= ? , gender = ? ,birthday= ? , avatar= ? , account= ? , pwd = ? , create_time = ? ,token = ?  where user_id = ?";
        //2.执行sql
        int update = template.update(sql, user.getNickname(),
                user.getFollowings_count(),
                user.getFollowers_count(),
                user.getFeeds_count(),
                user.isIs_following(),
                user.getDescription(),
                user.getGender(),
                user.getBirthday(),
                user.getAvatar(),
                user.getAccount(),
                user.getPwd(),
                user.getCreate_time(),
                user.getUser_id(),
                user.getToken()
        );

        System.out.println("update count = " + update);

        return update > 0;


    }

}
