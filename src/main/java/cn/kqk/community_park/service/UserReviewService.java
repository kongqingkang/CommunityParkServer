package cn.kqk.community_park.service;

import cn.kqk.common.exception.BaseException;
import cn.kqk.common.utils.database.UUIDS;
import cn.kqk.community_park.bean.Message;
import cn.kqk.community_park.bean.User;
import cn.kqk.community_park.bean.UserReview;
import cn.kqk.community_park.bean.dto.UserDTO;
import cn.kqk.community_park.bean.dto.UserIdentifyDTO;
import cn.kqk.community_park.bean.vo.UserReviewVO;
import cn.kqk.community_park.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
public class UserReviewService {

    private UserReviewMapper userReviewMapper;
    private UserMapper userMapper;
    private PermissionMapper permissionMapper;
    private RolePermissionMapper rolePermissionMapper;
    private RoleMapper roleMapper;
    private UserRoleMapper userRoleMapper;
    private MessageMapper messageMapper;

    public UserReviewService(UserReviewMapper userReviewMapper, UserMapper userMapper, PermissionMapper permissionMapper, RolePermissionMapper rolePermissionMapper, RoleMapper roleMapper, UserRoleMapper userRoleMapper, MessageMapper messageMapper) {
        this.userReviewMapper = userReviewMapper;
        this.userMapper = userMapper;
        this.permissionMapper = permissionMapper;
        this.rolePermissionMapper = rolePermissionMapper;
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.messageMapper = messageMapper;
    }

    public UserIdentifyDTO reviewPhoto(String userReviewId){
        UserIdentifyDTO u =null;
        u=userReviewMapper.selectPhotoByuserReviewId(userReviewId);
        return u;
    }

    /**
     * 管理员审核实名认证
     * @param userReviewId
     */
    public void adminReviewUser(String userReviewId){
        //查询status是不是已经通过了
        UserReview ur = userReviewMapper.selectStatusByUserReviewId(userReviewId);
        if (ur.getStatus()==1){
            throw new BaseException(10,"已经通过了！");
        } else{
            //根据adminReviewUser更新status的状态，并且添加审核者的id
            UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
            userReviewMapper.updateStatusByUserReviewIdAndUpdateReviewer(getUser.getUserId(),userReviewId);
            userMapper.updateIdentifyFlagByIdAndNumber(1,ur.getApplyer());
            //在user表里也要更新identityflag
            userMapper.updateIdentifyFlagById(1,getUser.getUserId());
            //发送一条消息
            //获取发布时间
            LocalDateTime localDateTime = LocalDateTime.now();
            Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
            //根据userReviewId查到userid也就是接收者
            UserReview u =  userReviewMapper.selectUserIdByUserReviewId(userReviewId);

            //4.在message表里生成一条记录
            String messageId=UUIDS.getUUID();
            //4.1 UUID生成消息id，求租发布人的userid和当前用户放进去
            Message message = new Message();
            message.setMessageId(messageId);
            message.setSendUserId(getUser.getUserId());
            message.setReciptUserId(u.getApplyer());
            //4.2 类别是1表示出租意向 2求租意向   content 是 “我有车位给你”
            message.setMessageType(3);
            message.setContent("恭喜通过实名认证！");
            message.setCreateTime(date);
            //4.3 read_flag 设为0初始状态
            message.setReadFlag(0);
            messageMapper.insertOrUpdateSelective(message);
        }
    }

    /**
     * 驳回实名认证的请求
     * @param userReviewId
     */
    public void adminUnReviewUser(String userReviewId){
        //查询status是不是已经通过了
        UserReview ur = userReviewMapper.selectStatusByUserReviewId(userReviewId);
        if (ur.getStatus()==2){
            throw new BaseException(11,"已经驳回了！");
        } else{
            //根据adminReviewUser更新status的状态，并且添加审核者的id
            UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
            userReviewMapper.updateStatusByUserUnReviewIdAndUpdateReviewer(getUser.getUserId(),userReviewId);
            userMapper.updateIdentifyFlagByIdAndNumber(2,ur.getApplyer());
            //在user表里也要更新identityflag
            userMapper.updateIdentifyFlagById(2,getUser.getUserId());
            //发送一条消息
            //获取发布时间
            LocalDateTime localDateTime = LocalDateTime.now();
            Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
            //根据userReviewId查到userid也就是接收者
            UserReview u =  userReviewMapper.selectUserIdByUserReviewId(userReviewId);

            //4.在message表里生成一条记录
            String messageId=UUIDS.getUUID();
            //4.1 UUID生成消息id，求租发布人的userid和当前用户放进去
            Message message = new Message();
            message.setMessageId(messageId);
            message.setSendUserId(getUser.getUserId());
            message.setReciptUserId(u.getApplyer());
            //4.2 类别是1表示出租意向 2求租意向   content 是 “我有车位给你”
            message.setMessageType(4);
            message.setContent("抱歉，你没有通过实名认证！");
            message.setCreateTime(date);
            //4.3 read_flag 设为0初始状态
            message.setReadFlag(0);
            messageMapper.insertOrUpdateSelective(message);
        }
    }


    /**
     * 后台-分页获取用户审核数据
     * @param pageNum 当前页
     * @param pageSize 页面数据大小
     * @return
     */
    public PageInfo<UserIdentifyDTO> userIdentifyList(Integer pageNum, Integer pageSize){
//        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);// 设定当前页码，以及当前页显示的条数
        List<UserIdentifyDTO> userIdentifyDTOList= userReviewMapper.findUserIndentify();
//        PageHelper.clearPage();
        PageInfo<UserIdentifyDTO> info = new PageInfo<>(userIdentifyDTOList);
        info.getTotal();
        info.isHasNextPage();
        return info;
    }


    /**
     * 用户提交审核，状态成为待审核
     *
     * @param reviewVO
     * @author kqk
     * @time 2020年4月13日20:08:58
     */
    public void userReview(UserReviewVO reviewVO, MultipartFile[] reviewImg) {
        int flag = 0;
//        int rolePermissionFlag = 0;
        int userFlag = 0;
//        int permissionFlag=0;
        UserReview ur = new UserReview();
        User user;
//        User getUser = (User) SecurityUtils.getSubject().getPrincipal();
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        //获取该用户的角色id
//        UserRole userRole = userRoleMapper.selectRoleIdByUserId(getUser.getUserId());

        User result = userMapper.selectMobileFlagById(getUser.getUserId());
        UserReview res = userReviewMapper.selectStatusByUserId(getUser.getUserId());
        if (res==null) {
            //1、从页面获取到姓名等信息存进数据库
            ur.setUserReviewId(UUIDS.getUUID());
            ur.setTruthName(reviewVO.getTruthName());
            ur.setIdentityCard(reviewVO.getIdentityCard());
            ur.setSex(reviewVO.getSex());
            ur.setMobile(reviewVO.getMobile());
            ur.setStatus(0);
            ur.setApplyer(getUser.getUserId());
            try {
                flag = userReviewMapper.insertOrUpdateSelective(ur);
            } catch (Exception e) {
                throw new BaseException(1, "userReview添加失败……");
            }
//            String permissionId = UUIDS.getUUID();
//            //设置角色的权限
//            Permission permission = new Permission();
//            permission.setPermissionId(permissionId);
//            permission.setPermissionName("实名认证");
//            permission.setStatus(0);//默认状态
//            try {
//                permissionFlag = permissionMapper.insertOrUpdateSelective(permission);
//            } catch (Exception e) {
//                throw new BaseException(50, "permission添加失败……");
//            }
//            RolePermission rp = new RolePermission();
//            rp.setPermisssionId(permissionId);
//            rp.setRoleId(userRole.getRoleId());
//            try {
//                rolePermissionFlag = rolePermissionMapper.insertOrUpdateSelective(rp);
//            } catch (Exception e) {
//                throw new BaseException(51, "rolePermissionFlag添加失败……");
//            }
            try {
                //根据id将user表里面的身份认证的状态，改成0（待审核状态）
                user= userMapper.selectUserId(getUser.getUserId());
                userFlag  = userMapper.updateIdentifyFlagById(0,user.getUserId());
        } catch (Exception e) {
                System.out.println(e);
            throw new BaseException(2, "user添加失败……");
        }
            //把前端获取到的url存进数据库
            //再把url存入到硬盘里面
            if (flag == 1 && userFlag==1) {
                if (reviewImg != null) {
                    //存储图片
                    try {
                        //ur.setCardUrl1(reviewVO.getCard_url1());
                        //ur.setCardUrl2(reviewVO.getCard_url2());
                        addUserReviewImg(ur,reviewImg);
                        flag=userReviewMapper.insertOrUpdate(ur);
                        if (flag <= 0) {
                            throw new BaseException(3, "创建图片1地址失败");
                        }
                    } catch (Exception e) {
                        throw new BaseException(4, e.getMessage());
                    }
                } else {
                    throw new BaseException(7, "创建图片2地址失败");
                }
            } else {
                throw new BaseException(8, "这不对啊，失败了……");
            }
        }else if (result.getIdentifyFlag() == 1){
            throw new BaseException(58, "实名认证已通过……");
        }else if (result.getIdentifyFlag() == 2){
            throw new BaseException(59, "实名认证未通过……");
        }
        else {
            throw new BaseException(9, "实名认证正在加紧审核中……");
        }
    }

    private void addUserReviewImg(UserReview ur,MultipartFile[] reviewImg) {
        int count=0;
        String realPath="";
        String realNetPlath="";
        for (MultipartFile multipartFile : reviewImg) {    //循环保存文件
            if (multipartFile.getSize() / 10000 > 100) {
                throw new BaseException(9, "图片大小不能超过1000KB……");
            } else {
                //判断上传文件格式
                String fileType = multipartFile.getContentType();
                if (fileType.equals("image/jpeg") || fileType.equals("image/png") || fileType.equals("image/jpeg")) {
                    // 要上传的目标文件存放的绝对路径
                    final String localPath = "F:\\IDEAProject\\static\\img";
                    //映射地址
                    final String netPath = "/img";
                    //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
                    //获取文件名
                    String fileName = multipartFile.getOriginalFilename();
                    //获取文件后缀名
                    String suffixName = fileName.substring(fileName.lastIndexOf("."));
                    //重新生成文件名
                    fileName = UUIDS.getUUID() + suffixName;
                    realPath = localPath + "\\" + fileName;
                    realNetPlath = netPath + "/" + fileName;
                    //保存
                    File file = new File(realPath);
                    if (!file.getParentFile().exists()){
                        file.getParentFile().mkdirs();
                    }
                    try {
                        multipartFile.transferTo(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //result_msg="图片格式不正确";
                    throw new BaseException(11, "图片格式不正确……");
                }
                if(count==0){
                    ur.setCardUrl1(realNetPlath);
                }else{
                    ur.setCardUrl2(realNetPlath);
                }
            }
            count++;
        }
    }

    public int updateBatch(List<UserReview> list) {
        return userReviewMapper.updateBatch(list);
    }


    public int updateBatchSelective(List<UserReview> list) {
        return userReviewMapper.updateBatchSelective(list);
    }


    public int batchInsert(List<UserReview> list) {
        return userReviewMapper.batchInsert(list);
    }


    public int insertOrUpdate(UserReview record) {
        return userReviewMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(UserReview record) {
        return userReviewMapper.insertOrUpdateSelective(record);
    }
}
