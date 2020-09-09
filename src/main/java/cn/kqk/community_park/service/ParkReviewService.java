package cn.kqk.community_park.service;

import cn.kqk.common.exception.BaseException;
import cn.kqk.common.utils.database.UUIDS;
import cn.kqk.community_park.bean.*;
import cn.kqk.community_park.bean.dto.ParkReviewDTO;
import cn.kqk.community_park.bean.dto.UserDTO;
import cn.kqk.community_park.bean.vo.ParkReviewVO;
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
public class ParkReviewService{

    private ParkReviewMapper parkReviewMapper;
    private ParkMapper parkMapper;
    private CommunityMapper communityMapper;
    private UserReviewMapper userReviewMapper;
    private MessageMapper messageMapper;

    /**
     * 用户提交车位审核，状态成为待审核
     * @param parkReviewMapper
     * @param parkMapper
     * @param communityMapper
     * @param userReviewMapper
     * @param messageMapper
     */
    public ParkReviewService(ParkReviewMapper parkReviewMapper, ParkMapper parkMapper, CommunityMapper communityMapper, UserReviewMapper userReviewMapper, MessageMapper messageMapper) {
        this.parkReviewMapper = parkReviewMapper;
        this.parkMapper = parkMapper;
        this.communityMapper = communityMapper;
        this.userReviewMapper = userReviewMapper;
        this.messageMapper = messageMapper;
    }

    /**
     * 后台-分页获取车辆审核列表页面
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<ParkReviewDTO> parkIdentifyList(Integer pageNum, Integer pageSize){
//        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);// 设定当前页码，以及当前页显示的条数
        List<ParkReviewDTO> parkReviewDTOS= parkReviewMapper.findParkIndentify();
//        PageHelper.clearPage();
        PageInfo<ParkReviewDTO> info = new PageInfo<>(parkReviewDTOS);
        info.getTotal();
        info.isHasNextPage();
        return info;
    }

    public ParkReviewDTO reviewParkPhoto(String parkReviewId){
        ParkReviewDTO pr = parkReviewMapper.selectPhotoByParkReviewId(parkReviewId);
        return pr;
    }

    /**
     * 后台-管理员通过车位的审核
     * @param parkReviewId
     */
    public void adminReviewPark(String parkReviewId){
        //查询status是不是已经通过了
        ParkReview parkReview = parkReviewMapper.selectParkInfoByParkReviewId(parkReviewId);
        if (parkReview.getStatus()==1){
            throw new BaseException(10,"已经通过了！");
        } else{
            //根据adminReviewUser更新status的状态，并且添加审核者的id
            UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
            parkReviewMapper.updateStatusByParkReviewId(parkReviewId);
            parkMapper.updateIdentifyFlagByParkId(1,parkReview.getParkId());
            //发送一条消息
            //获取发布时间
            LocalDateTime localDateTime = LocalDateTime.now();
            Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
            //根据userReviewId查到userid也就是接收者
            ParkReview p =  parkReviewMapper.selectUserIdByUserReviewId(parkReviewId);

            //4.在message表里生成一条记录
            String messageId=UUIDS.getUUID();
            //4.1 UUID生成消息id，求租发布人的userid和当前用户放进去
            Message message = new Message();
            message.setMessageId(messageId);
            message.setSendUserId(getUser.getUserId());
            message.setReciptUserId(p.getUserId());
            //4.2 类别是1表示出租意向 2求租意向   content 是 “我有车位给你”
            message.setMessageType(5);
            message.setContent("恭喜，该车位认证成功，可以发布车位出租信息！");
            message.setCreateTime(date);
            //4.3 read_flag 设为0初始状态
            message.setReadFlag(0);
            messageMapper.insertOrUpdateSelective(message);
        }
    }

    /**
     * 管理员驳回申请
     * @param parkReviewId
     */
    public void adminUnReviewPark(String parkReviewId){
        //查询status是不是已经通过了
        ParkReview parkReview = parkReviewMapper.selectParkInfoByParkReviewId(parkReviewId);
        if (parkReview.getStatus()==2){
            throw new BaseException(11,"已经驳回了！");
        } else{
            //根据adminReviewUser更新status的状态，并且添加审核者的id
            UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
            parkReviewMapper.updateStatusByUserUnReviewId(parkReviewId);
            //车位表相应的作出改变
            parkMapper.updateIdentifyFlagByParkId(2,parkReview.getParkId());
            //发送一条消息
            //获取发布时间
            LocalDateTime localDateTime = LocalDateTime.now();
            Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
            //根据userReviewId查到userid也就是接收者
            ParkReview p =  parkReviewMapper.selectUserIdByUserReviewId(parkReviewId);

            //4.在message表里生成一条记录
            String messageId=UUIDS.getUUID();
            //4.1 UUID生成消息id，求租发布人的userid和当前用户放进去
            Message message = new Message();
            message.setMessageId(messageId);
            message.setSendUserId(getUser.getUserId());
            message.setReciptUserId(p.getUserId());
            //4.2 类别是1表示出租意向 2求租意向   content 是 “我有车位给你”
            message.setMessageType(6);
            message.setContent("抱歉，该车位未认证！");
            message.setCreateTime(date);
            //4.3 read_flag 设为0初始状态
            message.setReadFlag(0);
            messageMapper.insertOrUpdateSelective(message);
        }
    }


    /**
     * 车位审核
     * @param parkReviewVO
     * @param reviewImg
     */
    public void parkReview(ParkReviewVO parkReviewVO, MultipartFile[] reviewImg){
        int flag = 0;
        int parkFlag = 0;
        int communityFlag=0;
//        int permissionFlag=0;
//        int rolePermissionFlag=0;
        Community comm;
        String communityId=UUIDS.getUUID();
        String parkId=UUIDS.getUUID();
        //获取到当前登录的用户信息
//        User getUser = (User) SecurityUtils.getSubject().getPrincipal();
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        //获取该用户的角色id
//        UserRole userRole = userRoleMapper.selectRoleIdByUserId(getUser.getUserId());

        //创建对象
        ParkReview parkReview = new ParkReview();
        Community community = new Community();
        Park park = new Park();

        //1、根据用户id判断有没有实名认证，没有抛出异常
        UserReview ur = userReviewMapper.selectUserReviewInfoByUserId(getUser.getUserId());
        if (ur!=null){
            if(ur.getStatus()==1){//表示通过审核

//                //添加权限
//                String permissionId = UUIDS.getUUID();
//                //设置角色的权限
//                Permission permission = new Permission();
//                permission.setPermissionId(permissionId);
//                permission.setPermissionName("车位认证");
//                permission.setStatus(0);//默认状态
//                try {
//                    permissionFlag = permissionMapper.insertOrUpdateSelective(permission);
//                } catch (Exception e) {
//                    throw new BaseException(50, "permission添加失败……");
//                }
//                RolePermission rp = new RolePermission();
//                rp.setPermisssionId(permissionId);
//                rp.setRoleId(userRole.getRoleId());
//                try {
//                    rolePermissionFlag = rolePermissionMapper.insertOrUpdateSelective(rp);
//                } catch (Exception e) {
//                    throw new BaseException(51, "rolePermissionFlag添加失败……");
//                }
                //判断当前车位是否认证
                //2、根据社区地址查到社区id，如果输入的社区名没有，就添加进去
                //判断社区是不是已存在，存在就不用再存
                comm = communityMapper.selectCommuityByCommunityName(parkReviewVO.getCommunityName(),parkReviewVO.getCommunityAddress());
                if(comm!=null){
                    communityFlag=1;
                }else{
                    community.setCommunityId(communityId);
                    community.setCommunityName(parkReviewVO.getCommunityName());
                    community.setCommunityAddress(parkReviewVO.getCommunityAddress());
                    communityFlag  = communityMapper.insertOrUpdateSelective(community);
                    communityMapper.insertOrUpdateSelective(community);
                }
                //3、根据社区id和车位地址查有没有认证过
                Community communityInfo = communityMapper.selectCommuityIdByCommunityName(parkReviewVO.getCommunityName());
                System.out.println(communityInfo.getCommunityId());
                System.out.println(parkReviewVO.getParkAddress());
                ParkReview pr= parkReviewMapper.selectReviewInfoByCommunityIdAndParkAddress(communityInfo.getCommunityId(),parkReviewVO.getParkAddress());
                if (pr==null){
                    parkReview.setParkReviewId(UUIDS.getUUID());
                    parkReview.setOwnerName(parkReviewVO.getOwnerName());
                    parkReview.setMobile(parkReviewVO.getMobile());
                    parkReview.setIdentityCard(parkReviewVO.getIdCard());
                    parkReview.setSex(parkReviewVO.getSex());
                    parkReview.setCommunityId(communityId);
                    parkReview.setParkAddress(parkReviewVO.getParkAddress());
                    parkReview.setStatus(0);
                    parkReview.setUserId(getUser.getUserId());
                    parkReview.setParkId(parkId);
                    try {
                        flag = parkReviewMapper.insertOrUpdateSelective(parkReview);
                    } catch (Exception e) {
                        throw new BaseException(1, "parkReview添加失败……");
                    }
                    try {
                        //把填的车位信息插入到表中
                        park.setParkId(parkId);
                        park.setParkName(parkReviewVO.getParkAddress());
                        park.setLocation(parkReviewVO.getParkAddress());
                        park.setParkName(parkReviewVO.getParkName());
                        park.setCommunityId(communityId);
                        park.setIdentifyFlag(0);//提交了，还没还没审核
                        park.setRentoutFlag(0);//初始状态 0
                        parkFlag = parkMapper.insertOrUpdateSelective(park);

                    } catch (Exception e) {
                        System.out.println(e);
                        throw new BaseException(3, "park添加失败……");
                    }
                    if (flag == 1 && parkFlag==1&&communityFlag==1) {
                        if (reviewImg != null) {
                            //存储图片
                            try {
                                addParkReviewImg(parkReview,reviewImg);
                                parkFlag=parkReviewMapper.insertOrUpdate(parkReview);
                                if (parkFlag <= 0) {
                                    throw new BaseException(4, "创建图片1地址失败");
                                }
                            } catch (Exception e) {
                                throw new BaseException(5, e.getMessage());
                            }
                        } else {
                            throw new BaseException(6, "创建图片2地址失败");
                        }
                    } else {
                        throw new BaseException(7, "失败……");
                    }
                }else if(pr.getStatus() == 0){
                    throw new BaseException(8, "车位认证正在加紧审核中……");
                }else {
                    throw new BaseException(9, "已经审核了，仔细看看……");
                }
            }else{
                throw new BaseException(80,"没通过实名认证审核");
            }
        }else {
            throw new BaseException(90,"还没实名认证");
        }
    }
    private void addParkReviewImg(ParkReview pr, MultipartFile[] reviewImg) {
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
                    throw new BaseException(10, "图片格式不正确……");
                }
                if(count==0){
                    pr.setEvidenceUrl(realNetPlath);
                }else{
                    pr.setParkPhotoUrl(realNetPlath);
                }
            }
            count++;
        }
    }

    public int updateBatch(List<ParkReview> list) {
        return parkReviewMapper.updateBatch(list);
    }


    public int updateBatchSelective(List<ParkReview> list) {
        return parkReviewMapper.updateBatchSelective(list);
    }


    public int batchInsert(List<ParkReview> list) {
        return parkReviewMapper.batchInsert(list);
    }


    public int insertOrUpdate(ParkReview record) {
        return parkReviewMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(ParkReview record) {
        return parkReviewMapper.insertOrUpdateSelective(record);
    }

}
