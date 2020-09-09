package cn.kqk.community_park.service;

import cn.kqk.common.exception.BaseException;
import cn.kqk.common.utils.encryption.GeneratePassword;
import cn.kqk.community_park.bean.UserLogin;
import cn.kqk.community_park.bean.dto.UserDTO;
import cn.kqk.community_park.bean.vo.EditPassword;
import cn.kqk.community_park.mapper.UserLoginMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Service
@Transactional
public class UserLoginService{

    private UserLoginMapper userLoginMapper;

    public UserLoginService(UserLoginMapper userLoginMapper) {
        this.userLoginMapper = userLoginMapper;
    }

    @Value("${server-config.salt}")
    private String salt;
    /**
     * 修改密码
     * @param editPassword
     */
    public void editThePassword(EditPassword editPassword){
        UserLogin userLogin = new UserLogin();
//        User getUser = (User) SecurityUtils.getSubject().getPrincipal();
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        String newAuth="";
        //1、从前台拿到原密码
        //2、拿原密码和数据库中的密码对比
        String oldAuth= GeneratePassword.password(editPassword.getOldPassword(),salt);
        int result = userLoginMapper.selectAuthSameByUserLoginId(oldAuth,getUser.getUserId());
        if (result==1||result==2){//2.1输入的原密码和数据库中的密码一致，则允许修改密码
            //1或2的原因是多种登录方式，现在目前只有两种，账号登录和手机号登录
            //3、判断新密码和原密码是否一致
            newAuth= GeneratePassword.password(editPassword.getPassword(),salt);
            int flag = userLoginMapper.selectAuthSameByUserLoginId(newAuth,getUser.getUserId());
            if(flag==0){//3.1、若不一致则允许修改
                //4、拿到新密码，更新
                //根据用户id找到所有的登录账号的信息，然后根据id把密码更新进去
                List<UserLogin> list = userLoginMapper.selectUserIdByAuth(oldAuth,getUser.getUserId());
                //批量更新
                userLoginMapper.AuthUpdateBatch(list,newAuth);
//                String json = JSON.toJSONString(list);
//                JSONArray jsonStr = JSONArray.parseArray(json);
//                for(int i=0;i<2;i++){
//                    if(i==0){
//                       String str = jsonStr.getJSONObject(0).getString("userLoginId");
//                        userLoginMapper.updateAuthByUserLoginId(newAuth,str);
//                    }else{
//                        //String str1 = jsonStr.getJSONObject(1).getString("userLoginId");
//                      //  userLoginMapper.updateAuthByUserLoginId(newAuth,list);
//                    }
//                    System.out.println(i);
//                    System.out.println("1111111111111");
//                }
//                System.out.println(json);
//                Iterator it = list.iterator();
//                while(it.hasNext()){
//                    System.out.println(it.next());
//                }
//                Object[] objs = list.toArray();
//                for (int i=0;i<objs.length;i++){
//                    System.out.println(objs);
//                    //UserLogin ul = userLoginMapper.selectUserLoginIdByUserId();
//                }
            }else{ //3.2、若一致则不允许修改，抛出异常
                throw new BaseException(2,"新密码和原密码一样！");
            }
        }else{//2.2不一致则new baseexception
            throw new BaseException(1,"原密码不正确！");
        }
    }

    public int updateBatch(List<UserLogin> list) {
        return userLoginMapper.updateBatch(list);
    }


    public int updateBatchSelective(List<UserLogin> list) {
        return userLoginMapper.updateBatchSelective(list);
    }


    public int batchInsert(List<UserLogin> list) {
        return userLoginMapper.batchInsert(list);
    }


    public int insertOrUpdate(UserLogin record) {
        return userLoginMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(UserLogin record) {
        return userLoginMapper.insertOrUpdateSelective(record);
    }

}
