package cn.kqk.community_park.service;

import cn.kqk.common.exception.BaseException;
import cn.kqk.common.utils.database.UUIDS;
import cn.kqk.community_park.bean.*;
import cn.kqk.community_park.bean.dto.UserDTO;
import cn.kqk.community_park.mapper.*;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
/**
 *
 * @date 2020/4/10 19:53
 * @author kqk
 */
@Service
@Transactional
public class WantService{

    private WantMapper wantMapper;
    private MessageMapper messageMapper;
    private RentInfoMapper rentInfoMapper;
    private UserReviewMapper userReviewMapper;
    private ParkReviewMapper parkReviewMapper;
    private ParkMapper parkMapper;
    private RentParkMapper rentParkMapper;

    public WantService(WantMapper wantMapper, MessageMapper messageMapper, RentInfoMapper rentInfoMapper, UserReviewMapper userReviewMapper, ParkReviewMapper parkReviewMapper, ParkMapper parkMapper, RentParkMapper rentParkMapper) {
        this.wantMapper = wantMapper;
        this.messageMapper = messageMapper;
        this.rentInfoMapper = rentInfoMapper;
        this.userReviewMapper = userReviewMapper;
        this.parkReviewMapper = parkReviewMapper;
        this.parkMapper = parkMapper;
        this.rentParkMapper = rentParkMapper;
    }

    /**
     * 根据传进来的rentInfoId在want表生成一条记录，表示有租赁意向，同时在message生成一条消息记录
     * 出租详情
     * @param rentInfoId
     */
    public void reserveRentOut(String rentInfoId){
        //获取发布时间
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
        //1.根据获取当前用户的id
//        User getUser = (User) SecurityUtils.getSubject().getPrincipal();
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        //根据userId查看userreview是否实名认证了
        UserReview userReview = userReviewMapper.selectStatusByUserId(getUser.getUserId());
        if (userReview!=null){
            if (userReview.getStatus()==1){
                //2.根据传进来的rentInfoId找到相应的出租信息（发布人的userid）
                RentInfo rentInfo = rentInfoMapper.selectuserIdByRentInfoId(rentInfoId);
                //3.获得求租发布人的userid和当前用户的以及这条出租信息的rentInfoId
                Want want =new Want();
                String wantId= UUIDS.getUUID();
                String messageId=UUIDS.getUUID();
                want.setWantId(wantId);
                want.setRentInfoId(rentInfoId);
                want.setUserId(getUser.getUserId());
                want.setStatus(0);//0是初始状态
                wantMapper.insertOrUpdateSelective(want);
                //4.在message表里生成一条记录
                //4.1 UUID生成消息id，求租发布人的userid和当前用户放进去
                Message message = new Message();
                message.setMessageId(messageId);
                message.setSendUserId(getUser.getUserId());
                message.setReciptUserId(rentInfo.getUserId());
                //4.2 类别是1表示出租意向 2求租意向   content 是 “我有车位给你”
                message.setMessageType(2);
                message.setContent("我想租你的车位!希望你能租给我！");
                message.setCreateTime(date);
                //4.3 read_flag 设为0初始状态
                message.setReadFlag(0);
                messageMapper.insertOrUpdateSelective(message);
            }
            if(userReview.getStatus()==0) {
                throw new BaseException(4,"实名认证审核中，不可预订车位！");
            }
            if (userReview.getStatus()==2){
                throw new BaseException(5,"实名认证没通过，不可以预订车位！");
            }
        }else{
            throw new BaseException(6,"你还没实名认证，不可以预订车位！");
        }
    }

    /**
     * 出租详情
     * 根据rentInfoId确认已经出租
     * @param rentInfoId
     */
    public void confirmOutInfo(String rentInfoId){
//        User getUser = (User) SecurityUtils.getSubject().getPrincipal();
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        //获取发布时间
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());

        //根据传进来的rentInfoId找到发布者的userid
//        RentInfo rentUserId=rentInfoMapper.selectuserIdByRentInfoId(rentInfoId);
//        根据userId和wantid更新status为2
        List<Want> want =wantMapper.selectWantIdByRentInfoId(rentInfoId);
        for (int i=0;i<want.size();i++){
            wantMapper.updateStatusByWantId(want.get(i).getWantId());
        }

        //根据rentInfoId找到park_review_id和开始时间和结束时间
        RentInfo rent = rentInfoMapper.selectUserIdByRentInfoId(rentInfoId);
        //根据park_review_id找parkid
        ParkReview parkReview=parkReviewMapper.selectParkInfoByParkReviewId(rent.getParkReviewId());
//        根据parkid把renout_flag置1
        parkMapper.updateRentOutFlagByParkId(parkReview.getParkId());
//        在rentpark 谁租了哪个车位 parkid 开始时间和结束时间 status 为0（正常）
//        从want表找到
        RentPark rentPark = new RentPark();
        rentPark.setParkId(parkReview.getParkId());
//        rentPark.setUserId();
        rentPark.setStartTime(rent.getStartTime());
        rentPark.setEndTime(rent.getEndTime());
        rentPark.setStatus(0);
        rentPark.setUserId(getUser.getUserId());//车位所有者的id，也就是当前用户的id
        rentParkMapper.insertOrUpdateSelective(rentPark);

        //List<RentInfo> renttAlluserIdList=rentInfoMapper.selectAlluserIdByRentInfoId(rentInfoId);
        List<Want> wantList = wantMapper.selectAlluserIdByRentInfoId(rentInfoId);
        //3.获得求租发布人的userid和当前用户的以及这条出租信息的rentInfoId
        for (int i=0;i<wantList.size();i++){
            //4.在message表里生成一条记录
            //4.1 UUID生成消息id，求租发布人的userid和当前用户放进去
            Message message = new Message();
            message.setMessageId(UUIDS.getUUID());
            message.setSendUserId(getUser.getUserId());
            message.setReciptUserId(wantList.get(i).getUserId());
            //4.2 类别是1表示出租意向 2求租意向   content 是 “我有车位给你”
            message.setMessageType(2);
            message.setContent("我的车位已经出租了!谢谢！");
            message.setCreateTime(date);
            //4.3 read_flag 设为0初始状态
            message.setReadFlag(0);
            messageMapper.insertOrUpdateSelective(message);
        }
    }

    /**
     * 根据传进来的rentInfoId，在want表生成一条记录，同时在message生成一条消息记录
     * 求租详情 点预留判断有没有车位审核通过的
     * @param rentInfoId
     */
    public void reserveRent(String rentInfoId){
        //其实首先判断用户有没有该权限的,现在是测试，暂时不加
        //获取发布时间
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
        //1.根据获取当前用户的id
//        User getUser = (User) SecurityUtils.getSubject().getPrincipal();
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        //先查有没有实名认证
        UserReview userReview = userReviewMapper.selectStatusByUserId(getUser.getUserId());
        if (userReview!=null){
            if (userReview.getStatus()==1){
                //再根据userId查看parkreview是不是有车位空着
                //查询本用户一共有多少个已通过审核的车位，并且rentout_flag是0,
                List<ParkReview> list = parkReviewMapper.selectFreePark(getUser.getUserId());
                List<Park> parkList=null;
                for (int i=0;i<list.size();i++){
                    parkList = parkMapper.selectRentoutFlagByParkId(list.get(i).getParkId());
                }
                if(parkList!=null){
                    //2.根据传进来的rentInfoId找到相应的求租信息（发布人的userid）
                    RentInfo rentInfo = rentInfoMapper.selectuserIdByRentInfoId(rentInfoId);
                    //3.获得求租发布人的userid和当前用户的以及这条出租信息的rentInfoId
                    Want want =new Want();
                    String wantId= UUIDS.getUUID();
                    String messageId=UUIDS.getUUID();
                    want.setWantId(wantId);
                    want.setRentInfoId(rentInfoId);
                    want.setUserId(getUser.getUserId());
                    want.setStatus(0);//0是初始状态
                    wantMapper.insertOrUpdateSelective(want);
                    //4.在message表里生成一条记录
                    //4.1 UUID生成消息id，求租发布人的userid和当前用户放进去
                    Message message = new Message();
                    message.setMessageId(messageId);
                    message.setSendUserId(getUser.getUserId());
                    message.setReciptUserId(rentInfo.getUserId());
                    //4.2 类别是1表示出租意向 2求租意向   content 是 “我有车位给你”
                    message.setMessageType(1);
                    message.setContent("我有车位给你!请联系我！");
                    message.setCreateTime(date);
                    //4.3 read_flag 设为0初始状态
                    message.setReadFlag(0);
                    messageMapper.insertOrUpdateSelective(message);
                }else {
                    throw new BaseException(7,"你没有车位可以预留！");
                }
            }
            if(userReview.getStatus()==0) {
                throw new BaseException(1,"实名认证审核中，不可预订车位！");
            }
            if (userReview.getStatus()==2){
                throw new BaseException(2,"实名认证没通过，不可以预订车位！");
            }
        }else{
            throw new BaseException(3,"你还没实名认证，不可以预订车位！");
        }

    }
    /**
     * 根据rentInfoId确认已经租到车位了
     * 求租详情
     * @param rentInfoId
     */
    public void confirmInfo(String rentInfoId){
        //其实首先判断用户有没有该权限的,现在是测试，暂时不加
        //根据传进来的rentInfoId找到发布者的userid
//        RentInfo rentUserId=rentInfoMapper.selectuserIdByRentInfoId(rentInfoId);
//        List<RentInfo> renttAlluserIdList=rentInfoMapper.selectAlluserIdByRentInfoId(rentInfoId);
//        根据userId和wantid更新status为2
        List<Want> want =wantMapper.selectWantIdByRentInfoId(rentInfoId);
        for (int i=0;i<want.size();i++){
            wantMapper.updateStatusByWantId(want.get(i).getWantId());
        }
        //发送消息
        //获取发布时间
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
        //1.根据获取当前用户的id
//        User getUser = (User) SecurityUtils.getSubject().getPrincipal();
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        //2.根据传进来的rentInfoId找到相应的求租信息（发布人的userid）
        List<Want> wantList = wantMapper.selectAlluserIdByRentInfoId(rentInfoId);
//        RentInfo rentInfo = rentInfoMapper.selectuserIdByRentInfoId(rentInfoId);
        //3.获得求租发布人的userid和当前用户的以及这条出租信息的rentInfoId
        for (int i=0;i<wantList.size();i++){
            //4.在message表里生成一条记录
            //4.1 UUID生成消息id，求租发布人的userid和当前用户放进去
            Message message = new Message();
            message.setMessageId(UUIDS.getUUID());
            message.setSendUserId(getUser.getUserId());
            message.setReciptUserId(wantList.get(i).getUserId());
            //4.2 类别是1表示出租意向 2求租意向   content 是 “我有车位给你”
            message.setMessageType(2);
            message.setContent("我已经租到车位了!谢谢！");
            message.setCreateTime(date);
            //4.3 read_flag 设为0初始状态
            message.setReadFlag(0);
            messageMapper.insertOrUpdateSelective(message);
        }
    }

    public int batchInsert(List<Want> list) {
        return wantMapper.batchInsert(list);
    }


    public int insertOrUpdate(Want record) {
        return wantMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(Want record) {
        return wantMapper.insertOrUpdateSelective(record);
    }

}
