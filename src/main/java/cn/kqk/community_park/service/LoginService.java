package cn.kqk.community_park.service;

import cn.kqk.common.exception.BaseException;
import cn.kqk.community_park.bean.User;
import cn.kqk.community_park.bean.UserLogin;
import cn.kqk.community_park.bean.UserReview;
import cn.kqk.community_park.bean.dto.LoginCheckDataDTO;
import cn.kqk.community_park.bean.dto.UserDTO;
import cn.kqk.community_park.mapper.UserLoginMapper;
import cn.kqk.community_park.mapper.UserMapper;
import cn.kqk.community_park.mapper.UserReviewMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author kqk
 * @version 1.0
 * @className LoginService
 * @description 登录
 * @date 2020/4/10 19:58
 */
@Service
@Transactional
public class LoginService {
    private UserMapper userMapper;
    private UserLoginMapper userLoginMapper;
    private UserReviewMapper userReviewMapper;

    public LoginService(UserMapper userMapper, UserLoginMapper userLoginMapper, UserReviewMapper userReviewMapper) {
        this.userMapper = userMapper;
        this.userLoginMapper = userLoginMapper;
        this.userReviewMapper = userReviewMapper;
    }

    @Transactional(readOnly = true)
    public LoginCheckDataDTO getLoginCheckData(String account){
        LoginCheckDataDTO result = new LoginCheckDataDTO();
        UserLogin userLogin = null;
        User user = null;

        userLogin = userLoginMapper.selectByAccount(account);
        if (userLogin != null){
            user = userMapper.selectByPrimaryKey(userLogin.getUserId());
        }else{
            throw new BaseException(1,"账号不存在！");
        }
        result.setUser(user);
        result.setUserLogin(userLogin);
        return result;
    }


    public UserDTO getUser(String account){
        UserDTO result = new UserDTO();
        UserLogin userLogin = null;
        User user = null;
        UserReview userReview=null;
        userLogin = userLoginMapper.selectTypeByAccount(account);
        user = userMapper.selectByPrimaryKey(userLogin.getUserId());
        userReview=userReviewMapper.selectTruthNameByUserId(userLogin.getUserId());
        result.setType(userLogin.getType());
        result.setUserId(user.getUserId());
        return result;
    }



    /**
     * admin检查登录数据
     * @param account
     * @return
     */
    @Transactional(readOnly = true)
    public LoginCheckDataDTO getAdminLoginCheckData(String account){
        LoginCheckDataDTO result = new LoginCheckDataDTO();
        UserLogin userLogin = null;
        User user = null;
        //在userlogin表里面检查type是不是2，是2的话代表是管理员
        userLogin=userLoginMapper.selectTypeByAccount(account);
        if(userLogin.getType()!=2){
            throw new BaseException(250,"你不是管理员，无法登录！");
        }
//         = userLoginMapper.selectByAccount(account);
        if (userLogin != null){
            user = userMapper.selectByPrimaryKey(userLogin.getUserId());
        }
        result.setUser(user);
        result.setUserLogin(userLogin);
        return result;
    }

}
