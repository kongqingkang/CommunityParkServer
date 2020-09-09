package cn.kqk.community_park.service;

import cn.kqk.common.exception.BaseException;
import cn.kqk.common.utils.database.UUIDS;
import cn.kqk.community_park.bean.Message;
import cn.kqk.community_park.bean.Park;
import cn.kqk.community_park.bean.ParkReview;
import cn.kqk.community_park.bean.dto.ParkListDTO;
import cn.kqk.community_park.bean.dto.UserDTO;
import cn.kqk.community_park.mapper.MessageMapper;
import cn.kqk.community_park.mapper.ParkMapper;
import cn.kqk.community_park.mapper.ParkReviewMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

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
public class ParkService{

    private ParkMapper parkMapper;
    private ParkReviewMapper parkReviewMapper;
    private MessageMapper messageMapper;

    public ParkService(ParkMapper parkMapper, ParkReviewMapper parkReviewMapper, MessageMapper messageMapper) {
        this.parkMapper = parkMapper;
        this.parkReviewMapper = parkReviewMapper;
        this.messageMapper = messageMapper;
    }

    /**
     * 后台-分页获取车位信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<ParkListDTO> parkList(Integer pageNum, Integer pageSize){
//        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);// 设定当前页码，以及当前页显示的条数
        List<ParkListDTO> user= parkMapper.findPark();
        PageHelper.clearPage();
        PageInfo<ParkListDTO> info = new PageInfo<>(user);
        info.getTotal();
        info.isHasNextPage();
        return info;
    }

    public void adminLockPark(String parkId){
        //查询status是不是已经封禁了
        Park park = parkMapper.selectRentoutFlagByParkIdSingle(parkId);
        ParkReview parkReview = parkReviewMapper.selectReviewInfoByParkId(parkId);
        if (park.getRentoutFlag()==2){
            throw new BaseException(10,"已经封禁了！");
        } else{
            //根据userId更新status的状态
            UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
            parkMapper.updateRentoutFlagByParkId(2,parkId);
            //发送一条消息
            //获取发布时间
            LocalDateTime localDateTime = LocalDateTime.now();
            Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());

            //4.在message表里生成一条记录
            String messageId= UUIDS.getUUID();
            //4.1 UUID生成消息id，求租发布人的userid和当前用户放进去
            Message message = new Message();
            message.setMessageId(messageId);
            message.setSendUserId(getUser.getUserId());
            message.setReciptUserId(parkReview.getUserId());
            //4.2 类别是1表示出租意向 2求租意向   content 是 “我有车位给你”
            message.setMessageType(9);
            message.setContent("此车位已被封禁，无法使用，请联系管理员！");
            message.setCreateTime(date);
            //4.3 read_flag 设为0初始状态
            message.setReadFlag(0);
            messageMapper.insertOrUpdateSelective(message);
        }
    }

    public void adminUnLockPark(String parkId){
        //查询status是不是已经封禁了
        Park park = parkMapper.selectRentoutFlagByParkIdSingle(parkId);
        if (park.getRentoutFlag()==1){
            throw new BaseException(11,"已经解封了！");
        } else{
            //根据userId更新status的状态，并且添加审核者的id
            UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
            parkMapper.updateRentoutFlagByParkId(0,parkId);
            ParkReview parkReview = parkReviewMapper.selectReviewInfoByParkId(parkId);
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
            message.setReciptUserId(parkReview.getUserId());
            message.setMessageType(10);
            message.setContent("恭喜已解封！");
            message.setCreateTime(date);
            //4.3 read_flag 设为0初始状态
            message.setReadFlag(0);
            messageMapper.insertOrUpdateSelective(message);
        }
    }


    public int batchInsert(List<Park> list) {
        return parkMapper.batchInsert(list);
    }


    public int insertOrUpdate(Park record) {
        return parkMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(Park record) {
        return parkMapper.insertOrUpdateSelective(record);
    }

}
