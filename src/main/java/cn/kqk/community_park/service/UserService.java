package cn.kqk.community_park.service;

import cn.kqk.common.exception.BaseException;
import cn.kqk.common.utils.database.UUIDS;
import cn.kqk.common.utils.encryption.GeneratePassword;
import cn.kqk.community_park.bean.*;
import cn.kqk.community_park.bean.dto.UserDTO;
import cn.kqk.community_park.bean.dto.UserListDTO;
import cn.kqk.community_park.bean.vo.Register;
import cn.kqk.community_park.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Service
@Transactional
public class UserService{

    private UserMapper userMapper;
    private UserLoginMapper userLoginMapper;
    private UserInfoMapper userInfoMapper;
    private PermissionMapper permissionMapper;
    private RolePermissionMapper rolePermissionMapper;
    private RoleMapper roleMapper;
    private UserRoleMapper userRoleMapper;
    private MessageMapper messageMapper;
    @Value("${server-config.salt}")
    private String salt;

    /**
     * 构造方法
     * @param userMapper
     * @param userLoginMapper
     * @param userInfoMapper
     * @param permissionMapper
     * @param rolePermissionMapper
     * @param roleMapper
     * @param userRoleMapper
     * @param messageMapper
     */
    public UserService(UserMapper userMapper, UserLoginMapper userLoginMapper, UserInfoMapper userInfoMapper, PermissionMapper permissionMapper, RolePermissionMapper rolePermissionMapper, RoleMapper roleMapper, UserRoleMapper userRoleMapper, MessageMapper messageMapper) {
        this.userMapper = userMapper;
        this.userLoginMapper = userLoginMapper;
        this.userInfoMapper = userInfoMapper;
        this.permissionMapper = permissionMapper;
        this.rolePermissionMapper = rolePermissionMapper;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.messageMapper = messageMapper;
    }

    /**
     * 后台-分页获取用户列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<UserListDTO> userList(Integer pageNum, Integer pageSize){
//        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);// 设定当前页码，以及当前页显示的条数
        List<UserListDTO> user= userMapper.findUser();
        PageHelper.clearPage();
        PageInfo<UserListDTO> info = new PageInfo<>(user);
        info.getTotal();
        info.isHasNextPage();
        return info;
    }

    /**
     * 后台-管理员封禁用户
     * @param userId
     */
    public void adminLockUser(String userId){
        //查询status是不是已经封禁了
        User user= userMapper.selectUserId(userId);
//        ParkReview parkReview = parkReviewMapper.selectParkInfoByParkReviewId(parkReviewId);
        if (user.getStatus()==1){
            throw new BaseException(10,"已经封禁了！");
        } else{
            //根据userId更新status的状态
            UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
            userMapper.updateStatusByUserId(1,userId);
            //发送一条消息
            //获取发布时间
            LocalDateTime localDateTime = LocalDateTime.now();
            Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());

            //4.在message表里生成一条记录
            String messageId=UUIDS.getUUID();
            //4.1 UUID生成消息id，求租发布人的userid和当前用户放进去
            Message message = new Message();
            message.setMessageId(messageId);
            message.setSendUserId(getUser.getUserId());
            message.setReciptUserId(userId);
            //4.2 类别是1表示出租意向 2求租意向   content 是 “我有车位给你”
            message.setMessageType(7);
            message.setContent("你的账号已被封禁，无法使用，请联系管理员！");
            message.setCreateTime(date);
            //4.3 read_flag 设为0初始状态
            message.setReadFlag(0);
            messageMapper.insertOrUpdateSelective(message);
        }
    }

    /**
     * 管理员解封用户
     * @param userId
     */
    public void adminUnLockUser(String userId){
        //查询status是不是已经通过了
        User user= userMapper.selectUserId(userId);
        if (user.getStatus()==0){
            throw new BaseException(11,"已经解封了！");
        } else{
            //根据userId更新status的状态，并且添加审核者的id
            UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
            userMapper.updateStatusByUserId(0,userId);
            //发送一条消息
            //获取发布时间
            LocalDateTime localDateTime = LocalDateTime.now();
            Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());

            //4.在message表里生成一条记录
            String messageId=UUIDS.getUUID();
            //4.1 UUID生成消息id，求userid和当前用户放进去
            Message message = new Message();
            message.setMessageId(messageId);
            message.setSendUserId(getUser.getUserId());
            message.setReciptUserId(userId);
            message.setMessageType(8);
            message.setContent("恭喜已解封！");
            message.setCreateTime(date);
            //4.3 read_flag 设为0初始状态
            message.setReadFlag(0);
            messageMapper.insertOrUpdateSelective(message);
        }
    }

    /**
     * 用户注册
     * @param register
     * @return
     */
    public void userRegister(Register register){
        User user=new User();
        UserLogin userLogin=new UserLogin();
        UserLogin ul1=new UserLogin();
        UserInfo userInfo=new UserInfo();
        String uuid=UUIDS.getUUID();
        String newauth= GeneratePassword.password(register.getAuth(),salt);

        //查询是否有重名
        int doubleName = userLoginMapper.selectByAccountOrMobile(register.getAccount(),register.getMobile());
        if (doubleName==0){
            user.setUserId(uuid);
            user.setStatus(0);
//            user.setIdentifyFlag(0);
            user.setMobileFlag(0);
            userMapper.insertOrUpdateSelective(user);

            //这个是用account作为登录方式
            userLogin.setUserLoginId(UUIDS.getUUID());
            userLogin.setUserId(uuid);
            userLogin.setAccount(register.getAccount());
            userLogin.setAuth(newauth);
            userLogin.setType(0);//账户名登录
            userLogin.setLoginStatusFlag(0);//登录的方式是否可用 0可用
            userLoginMapper.insertOrUpdateSelective(userLogin);

            //这个是手机号登录方式
            ul1.setUserLoginId(UUIDS.getUUID());
            ul1.setUserId(uuid);
            ul1.setAccount(register.getMobile());
            ul1.setAuth(newauth);
            ul1.setType(1);//手机号登录
            ul1.setLoginStatusFlag(0);//登录的方式是否可用 0可用
            userLoginMapper.insertOrUpdateSelective(ul1);

            userInfo.setUserInfoId(UUIDS.getUUID());
            userInfo.setUserId(uuid);
            userInfo.setMobile(register.getMobile());
            userInfoMapper.insertOrUpdateSelective(userInfo);

            //设置用户角色和权限
            String roleId = UUIDS.getUUID();
            Role role = new Role();
            role.setRoleId(roleId);
            role.setRoleName("普通用户");
            role.setStatus(0);//可用，也代表没有任何权限
            roleMapper.insertOrUpdateSelective(role);

            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(uuid);
            userRoleMapper.insertOrUpdateSelective(userRole);


        }else{
            throw new BaseException(2,"此账号或此手机号已经被注册过！");
        }
    }


    public int batchInsert(List<User> list) {
        return userMapper.batchInsert(list);
    }


    public int insertOrUpdate(User record) {
        return userMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(User record) {
        return userMapper.insertOrUpdateSelective(record);
    }



}
