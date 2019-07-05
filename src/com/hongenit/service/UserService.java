package com.hongenit.service;


import com.hongenit.domain.AccountResultInfo;
import com.hongenit.domain.ResultInfo;
import com.hongenit.domain.User;

public interface UserService {
    /**
     * 注册用户
     *
     * @param user
     * @return 注册是否成功
     */
    AccountResultInfo regist(User user);


    ResultInfo updateNickname(User user);


    AccountResultInfo login(User user);

    ResultInfo fetch_my_info(User user);

}
