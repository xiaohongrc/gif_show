package com.hongenit.service.impl;

import com.hongenit.domain.AccountResultInfo;
import com.hongenit.domain.ResultInfo;
import com.hongenit.service.UserService;
import com.hongenit.dao.impl.UserDaoImpl;
import com.hongenit.dao.UserDao;
import com.hongenit.domain.User;
import com.hongenit.util.Md5Utitl;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    @Override
    public AccountResultInfo regist(User user) {
        AccountResultInfo info = new AccountResultInfo();
        //4.响应结果
        //1.根据用户名查询用户对象
        User u = userDao.findByAccount(user.getAccount());
        //判断u是否为null
        if (u == null) {
            //2.保存用户信息
            User saveUser = userDao.save(user);
            if (saveUser == null) {
                //注册用户存储数据库失败
                info.setStatus(ResultInfo.STATUS_REGIST_FAIL);
                info.setMsg(ResultInfo.MSG_REGIST_FAIL);
            } else {
                info.setUser_id(saveUser.getUser_id());
                info.setToken(saveUser.getToken());
            }
        } else {
            //用户名存在，注册失败
            info.setStatus(ResultInfo.STATUS_ACCOUNT_EXIST);
            info.setMsg(ResultInfo.MSG_ACCOUNT_EXIST);
        }
        return info;
    }

    @Override
    public ResultInfo updateNickname(User user) {
        ResultInfo resultInfo = new ResultInfo();
        //1.根据用户名查询用户对象
        User u = userDao.findByName(user.getNickname());
        //判断改昵称的用户是否已存在
        if (u == null) {
            //用户名不存在，修改用户昵称
            User targetUser = userDao.findByUserId(user.getUser_id());
            if (targetUser != null) {
                // 先通过user_id查出用户信息，然后只改变昵称。以保证不改变其它用户信息
                targetUser.setNickname(user.getNickname());
                boolean updateSucceed = userDao.update(targetUser);
                if (updateSucceed) {
                    resultInfo.setData(targetUser);
                } else {
                    resultInfo.setStatus(ResultInfo.STATUS_UPDATE_FAIL);
                    resultInfo.setMsg(ResultInfo.MSG_UPDATE_FAIL);
                }

            } else {
                resultInfo.setStatus(ResultInfo.STATUS_USER_NOT_EXIST);
                resultInfo.setMsg(ResultInfo.MSG_USER_NOT_EXIST + "  user_id:" + user.getUser_id());
            }
        } else {
            // 用户名已存在
            resultInfo.setStatus(ResultInfo.STATUS_NICKNAME_EXIST);
            resultInfo.setMsg(ResultInfo.MSG_NICKNAME_EXIST);
        }

        return resultInfo;
    }


    /**
     * 登录方法
     *
     * @param user
     * @return
     */
    @Override
    public AccountResultInfo login(User user) {
        AccountResultInfo resultInfo = new AccountResultInfo();
        User resultUser = userDao.findByAccount(user.getAccount());
        if (resultUser != null && user.getPwd().equals(resultUser.getPwd())) {
            long currentTimeMillis = System.currentTimeMillis();
            user.setToken(Md5Utitl.genToken(user.getAccount(), user.getPwd(), String.valueOf(currentTimeMillis)));
            boolean update = userDao.update(user);
            if (update) {
                resultInfo.setUser_id(resultUser.getUser_id());
                resultInfo.setToken(resultInfo.getToken());
            } else {
                resultInfo.setStatus(ResultInfo.STATUS_LOGIN_FAIL);
                resultInfo.setMsg(ResultInfo.MSG_LOGIN_FAIL);
            }
        } else {
            resultInfo.setStatus(ResultInfo.STATUS_LOGIN_FAIL);
            resultInfo.setMsg(ResultInfo.MSG_LOGIN_FAIL);
        }
        return resultInfo;
    }


    @Override
    public ResultInfo fetch_my_info(User user) {
        ResultInfo resultInfo = new ResultInfo();
        User resultUser = userDao.findByUserId(user.getUser_id());
        if (resultUser != null && user.getToken().equals(resultUser.getToken())) {
            resultInfo.setData(resultUser);
        } else {
            resultInfo.setStatus(ResultInfo.STATUS_FETCH_USER_INFO_FAIL);
            resultInfo.setMsg(ResultInfo.MSG_FETCH_USER_INFO_FAIL);
        }
        return resultInfo;
    }


}
