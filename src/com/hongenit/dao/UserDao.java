package com.hongenit.dao;


import com.hongenit.domain.User;

public interface UserDao {

    /**
     * 根据用户名查询用户信息
     *
     * @param account
     * @return
     */
    User findByAccount(String account);

    // 根据用户昵称查找用户
    User findByName(String name);


    // 根据用户ID查找用户
    User findByUserId(int userId);

    /**
     * 用户保存
     *
     * @param user
     */
    User save(User user);


    // 更新用户信息
    boolean update(User user);

}
