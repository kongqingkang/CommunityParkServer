package cn.kqk.community_park.service;

import cn.kqk.common.exception.BaseException;
import cn.kqk.common.utils.database.UUIDS;
import cn.kqk.community_park.bean.*;
import cn.kqk.community_park.bean.dto.*;
import cn.kqk.community_park.bean.vo.RentInfoVO;
import cn.kqk.community_park.bean.vo.RentOutInfoVO;
import cn.kqk.community_park.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class RentInfoService{

    private RentInfoMapper rentInfoMapper;
    private CommunityMapper communityMapper;
    private ParkMapper parkMapper;
    private ParkReviewMapper parkReviewMapper;
    private UserReviewMapper userReviewMapper;
    private WantMapper wantMapper;
    private UserMapper userMapper;

    public RentInfoService(RentInfoMapper rentInfoMapper, CommunityMapper communityMapper, ParkMapper parkMapper, ParkReviewMapper parkReviewMapper, UserReviewMapper userReviewMapper, WantMapper wantMapper, UserMapper userMapper) {
        this.rentInfoMapper = rentInfoMapper;
        this.communityMapper = communityMapper;
        this.parkMapper = parkMapper;
        this.parkReviewMapper = parkReviewMapper;
        this.userReviewMapper = userReviewMapper;
        this.wantMapper = wantMapper;
        this.userMapper = userMapper;
    }

    /**
     * 管理员首页获取需要的信息
     * @return
     */
    public AdminIndexDTO adminIndexInfo(){
        AdminIndexDTO adminIndexDTO = new AdminIndexDTO();
        //用户总数
        int userTotal  = userMapper.selectUserAccount();
        //当月信息增长
        int addTotal = rentInfoMapper.selectTotalAddThisMonth();
        //空余车位数
        int freePark = parkMapper.selectFreePark();
        //求租信息
        int rentInfoTotal = rentInfoMapper.selectRentInfoTotalByTypeId(0);
        //出租信息
        int rentOutInfoTotal = rentInfoMapper.selectRentInfoTotalByTypeId(1);
        //实名认证待审核数
        int noIdentify = userReviewMapper.selectNoIdentify();
        //车位认证待审核数
        int noParkIdentify=parkReviewMapper.selectNoIdentify();
        //真实姓名
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        UserReview u = userReviewMapper.selectTruthNameByUserId(getUser.getUserId());
        adminIndexDTO.setUserTotal(userTotal);
        adminIndexDTO.setTodayAdd(addTotal);
        adminIndexDTO.setFreePark(freePark);
        adminIndexDTO.setRentInfoTotal(rentInfoTotal);
        adminIndexDTO.setRentOutInfoTotal(rentOutInfoTotal);
        adminIndexDTO .setNoIdentify(noIdentify);
        adminIndexDTO.setNoParkIdentify(noParkIdentify);
        adminIndexDTO.setTruthName(u.getTruthName());
        return adminIndexDTO;
    }


    /**
     * 删除求租信息
     * @param rentInfoId
     */
    public void deleteRentInfo( String rentInfoId){
        //这里的删除就是讲typeid变成-1，不再展现，原始的数据还是存在的。
        rentInfoMapper.UpdateTypeIdByRentInfoId(rentInfoId);
    }


    /**
     * 修改出租信息
     * @param rentOutInfoVO
     * @param rentInfoId
     */
    public void editRentOutInfo(RentOutInfoVO rentOutInfoVO, String rentInfoId){
        RentOutInfoVO rentOut = new RentOutInfoVO();
        rentOut.setTitle(rentOutInfoVO.getTitle());
        rentOut.setDescription(rentOutInfoVO.getDescription());
        rentOut.setEndTime(rentOutInfoVO.getEndTime());
        rentOut.setStartTime(rentOutInfoVO.getStartTime());
        rentOut.setLongFlag(rentOutInfoVO.getLongFlag());
        rentOut.setPrice(rentOutInfoVO.getPrice());
        rentOut.setFreeFlag(rentOutInfoVO.getFreeFlag());
        //获取发布时间
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
        rentInfoMapper.updateByRentOutInfoId(rentOutInfoVO,date,rentInfoId);
        //其实是不可以更改的
//        rentvo.setMobile(rentInfoVO.getMobile());
//        rentvo.setTruthName(rentInfoVO.getTruthName());
//        rentInfoMapper.updateRentInfoVOByRentInfoId(rentvo,rentInfoId);
    }

    /**
     *修改求租数据
     * @param rentInfoVO
     * @param rentInfoId
     * @return
     */
    public void editRentInfo(RentInfoVO rentInfoVO, String rentInfoId){
        RentInfoVO rentvo=new RentInfoVO();
        rentvo.setTitle(rentInfoVO.getTitle());
        rentvo.setDescription(rentInfoVO.getDescription());
        rentvo.setEndTime(rentInfoVO.getEndTime());
        rentvo.setStartTime(rentInfoVO.getStartTime());
        rentvo.setLongFlag(rentInfoVO.getLongFlag());
        rentvo.setPrice(rentInfoVO.getPrice());
        //获取发布时间
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
        rentInfoMapper.updateByRentInfoId(rentInfoVO,date,rentInfoId);
        //其实是不可以更改的
//        rentvo.setMobile(rentInfoVO.getMobile());
//        rentvo.setTruthName(rentInfoVO.getTruthName());
//        rentInfoMapper.updateRentInfoVOByRentInfoId(rentvo,rentInfoId);
    }


    /**
     * 根据信息id查询求租信息
     * @param rentInfoId
     */
    public RentInfoDTO rentInfoDetail(String rentInfoId){
        //1.每进入一次views就更新一下
        rentInfoMapper.updateViewsByRentInfoId(rentInfoId);
//        User getUser = (User) SecurityUtils.getSubject().getPrincipal();
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        RentInfoDTO rentInfo = new RentInfoDTO();

        //查询当前用户有没有对当前用户做出过want请求
        Want want = wantMapper.selectInfoByUserIdAndRentInfoId(getUser.getUserId(),rentInfoId);
        if(want==null){//没做过
            rentInfo.setWantId("0");
        }else{
            rentInfo.setWantId(want.getWantId());
        }
        //查询有没有确认 2是已确认
        List<Want> wantStatus = wantMapper.selectStatusByWantIdAndRentInfoId(rentInfoId);
        if (wantStatus.size() == 0) {
            rentInfo.setWantStatus(-1);
        } else {
            rentInfo.setWantStatus(2);
        }

        //查询userid和当前用户的id是否一致，一致的话返回1，不是的话返回0
        //2.根据id查询到信息
        RentInfoDTO rent = rentInfoMapper.findRentInfoDetail(rentInfoId);
        if (rent.getSelfFlag().equals(getUser.getUserId())){
            rentInfo.setSelfFlag("1");
        }else{
            rentInfo.setSelfFlag("0");
        }
        rentInfo.setPublishTime(rent.getPublishTime());
        rentInfo.setTypeId(rent.getTypeId());
        rentInfo.setMobile(rent.getMobile());
        rentInfo.setLongFlag(rent.getLongFlag());
        rentInfo.setTitle(rent.getTitle());
        rentInfo.setTruthName(rent.getTruthName());
        rentInfo.setStartTime(rent.getStartTime());
        rentInfo.setEndTime(rent.getEndTime());
        rentInfo.setPrice(rent.getPrice());
        rentInfo.setDescription(rent.getDescription());
        rentInfo.setAvatarUrl(rent.getAvatarUrl());
        rentInfo.setViews(rent.getViews());
        return rentInfo;
    }

    /**
     * 根据信息id查询出租信息
     * @param rentInfoId
     * @return
     */
    public RentOutInfoDTO rentOutInfoDetail(String rentInfoId){
        //每进入一次views就更新一下
        rentInfoMapper.updateViewsByRentInfoId(rentInfoId);
        //根据id查询到信息
        RentOutInfoDTO rent = new RentOutInfoDTO();
//        User getUser = (User) SecurityUtils.getSubject().getPrincipal();
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        //根据rentInfoId查询到park_review_id
        RentOutInfoDTO rentOutInfo = rentInfoMapper.findRentOutInfoDetail(rentInfoId);
        ParkReview pr = parkReviewMapper.selectParkInfoByParkReviewId(rentOutInfo.getParkReviewId());
        //根据查到的park_review_id 查询到parkId,根据parkId查到parkname和parkaddress
        System.out.println(pr.getParkId());
        Park p = parkMapper.selectParkInfoById(pr.getParkId());
//        根据parkid查到社区名
        Community c= communityMapper.selectCommunityInfoById(p.getCommunityId());
        //2.根据id查询到信息
        RentInfoDTO rent1 = rentInfoMapper.findRentInfoDetail(rentInfoId);
        if (rent1.getSelfFlag().equals(getUser.getUserId())){
            rent.setSelfFlag("1");
        }else{
            rent.setSelfFlag("0");
        }
        //查询当前用户有没有对当前用户做出过want请求
        Want want = wantMapper.selectInfoByUserIdAndRentInfoId(getUser.getUserId(),rentInfoId);
        if(want==null){//没做过
            rent.setWantId("0");
        }else{
            rent.setWantId(want.getWantId());
        }
        //查询有没有确认 2是已确认
//        Want wantStatus = wantMapper.selectStatusByWantIdAndRentInfoId(rentInfoId);
//        if(wantStatus==null){
//            rent.setWantStatus(-1);
//        }else{
//            rent.setWantStatus(wantStatus.getStatus());
//        }
        //查询有没有确认 2是已确认
        List<Want> wantStatus = wantMapper.selectStatusByWantIdAndRentInfoId(rentInfoId);
        if (wantStatus.size() == 0) {
            rent.setWantStatus(-1);
        } else {
            rent.setWantStatus(2);
        }
        //把获取的信息全部set进去
        rent.setAvatarUrl(rentOutInfo.getAvatarUrl());
        rent.setCommunityName(c.getCommunityName());
        rent.setDescription(rentOutInfo.getDescription());
        rent.setPublishTime(rentOutInfo.getPublishTime());
        rent.setEndTime(rentOutInfo.getEndTime());
        rent.setStartTime(rentOutInfo.getStartTime());
        rent.setFreeFlag(rentOutInfo.getFreeFlag());
        rent.setLongFlag(rentOutInfo.getLongFlag());
        rent.setMobile(rentOutInfo.getMobile());
        rent.setViews(rentOutInfo.getViews());
        rent.setTypeId(rentOutInfo.getTypeId());
        rent.setTitle(rentOutInfo.getTitle());
        rent.setPrice(rentOutInfo.getPrice());
        rent.setAvatarUrl(rentOutInfo.getAvatarUrl());
        rent.setTruthName(rentOutInfo.getTruthName());
        rent.setPublishTime(rentOutInfo.getPublishTime());
        rent.setParkAddress(p.getLocation());
        rent.setParkName(p.getParkName());
        return rent;
    }

    /**
     * 归档-分页获取车位信息
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<ArchivesRentInfoDTO> archivesParkInfoList(Integer pageNum, Integer pageSize){
//        User getUser = (User) SecurityUtils.getSubject().getPrincipal();
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);// 设定当前页码，以及当前页显示的条数
        List<ArchivesRentInfoDTO> archivesRentInfoDTO= rentInfoMapper.findIndexParkInfoById(getUser.getUserId());
        PageHelper.clearPage();
        PageInfo<ArchivesRentInfoDTO> info = new PageInfo<>(archivesRentInfoDTO);
        info.getTotal();
        info.isHasNextPage();
        return info;
    }


    /**
     * 获取数据（archive）
     * @param pageNum 当前页
     * @param pageSize 页面数据大小
     * @return
     */
    public PageInfo<IndexRentInfoDTO> archivesRentInfoList(Integer pageNum, Integer pageSize){
//        User getUser = (User) SecurityUtils.getSubject().getPrincipal();
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);// 设定当前页码，以及当前页显示的条数
        List<IndexRentInfoDTO> indexRentInfoDTOS= rentInfoMapper.findIndexInfoById(getUser.getUserId());
        PageHelper.clearPage();
        PageInfo<IndexRentInfoDTO> info = new PageInfo<>(indexRentInfoDTOS);
        info.getTotal();
        info.isHasNextPage();
        return info;
    }
    /**
     * 获取数据（archive）
     * @param pageNum 当前页
     * @param pageSize 页面数据大小
     * @return
     */
    public PageInfo<IndexRentInfoDTO> archivesRentOutInfoList(Integer pageNum, Integer pageSize){
//        User getUser = (User) SecurityUtils.getSubject().getPrincipal();
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);// 设定当前页码，以及当前页显示的条数
        List<IndexRentInfoDTO> indexRentInfoDTOS= rentInfoMapper.findIndexInfoByIdTypeIdOne(getUser.getUserId());
        PageHelper.clearPage();
        PageInfo<IndexRentInfoDTO> info = new PageInfo<>(indexRentInfoDTOS);
        info.getTotal();
        info.isHasNextPage();
        return info;
    }
    /**
     * 分页获取数据（首页）
     * @param pageNum 当前页
     * @param pageSize 页面数据大小
     * @return
     */
    public PageInfo<IndexRentInfoDTO> indexRentInfo(Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);// 设定当前页码，以及当前页显示的条数
        List<IndexRentInfoDTO> indexRentInfoDTOS= rentInfoMapper.findIndexInfo();
        PageHelper.clearPage();
        PageInfo<IndexRentInfoDTO> info = new PageInfo<>(indexRentInfoDTOS);
        info.getTotal();
        info.isHasNextPage();
        return info;
    }

    /**
     * 搜索
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<IndexRentInfoDTO> searchInfo(String keyword, Integer pageNum, Integer pageSize){

        PageHelper.startPage(pageNum, pageSize);// 设定当前页码，以及当前页显示的条数
        List<IndexRentInfoDTO> list = rentInfoMapper.selectAllRentInfoByDescription(keyword);
        PageHelper.clearPage();
        PageInfo<cn.kqk.community_park.bean.dto.IndexRentInfoDTO> info = new PageInfo<>(list);
        info.getTotal();
        info.isHasNextPage();
        return info;
    }

    /**
     * 获取最新发布的求租信息数据
     */
    public List<RentInfo> getLatestRentInfo(){
        List<RentInfo> list = rentInfoMapper.getLatestRentInfoLimitSix();
        return list;
    }

    /**
     * 获取最新发布的出租信息数据
     */
    public List<RentInfo> getLatestRentOutInfo(){
        List<RentInfo> list = rentInfoMapper.getLatestRentOutInfoLimitSix();
        return list;
    }

    /**
     * 获取最多浏览的信息数据
     */
    public List<RentInfo> getMostViewsInfo(){
        List<RentInfo> list = rentInfoMapper.getMostViewsInfo();
        return list;
    }



    /**
     * 求租发布
     * @param rentInfoVO
     */
    public void rentInfoInput(RentInfoVO rentInfoVO){
        RentInfo rentInfo = new RentInfo();
        String rentId= UUIDS.getUUID();
        //根据用户编号在实名认证表中查询是否通过认证
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        UserReview res = userReviewMapper.selectStatusByUserId(getUser.getUserId());
//        UserReview flag = userReviewMapper.selectUserReviewInfoByNameAndMobile(rentInfoVO.getTruthName(),rentInfoVO.getMobile());
        if (res==null){
            throw new BaseException(55,"还未实名认证,不能发布求租信息！");
        }else{
            if(res.getStatus()==1){
                //获取发布时间
                LocalDateTime localDateTime = LocalDateTime.now();
                Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());

                String startTimeStr = rentInfoVO.getStartTime();
                String endTimeStr = rentInfoVO.getEndTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date startTime = null;
                Date endTime = null;
                try {
                    startTime = format.parse(startTimeStr);
                    endTime = format.parse(endTimeStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                rentInfo.setRentInfoId(rentId);
                rentInfo.setTitle(rentInfoVO.getTitle());
                rentInfo.setLongFlag(rentInfoVO.getLongFlag());
                rentInfo.setDescription(rentInfoVO.getDescription());
                rentInfo.setPrice(rentInfoVO.getPrice());
                rentInfo.setTypeId("0");
                rentInfo.setViews(0);
                rentInfo.setConfirmStatus(0);//初始状态
                rentInfo.setUserId(getUser.getUserId());
                rentInfo.setPublishTime(date);
                int result = date.compareTo(startTime);
                int result1 = startTime.compareTo(endTime);
                if(result==-1&&result1==-1){
                    rentInfo.setStartTime(startTime);
                    rentInfo.setEndTime(endTime);
                }else{
                    throw new BaseException(1,"你填的时间有问题……出错啦！");
                }
                rentInfoMapper.insert(rentInfo);
            }else{
                throw new BaseException(16,"还未通过实名认证,不能发布求租信息！");
            }
        }
    }

    /**
     * 出租发布
     * @param rentOutInfoVO
     */
    public void rentOutInfoInput(RentOutInfoVO rentOutInfoVO){
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        RentInfo rentInfo = new RentInfo();
        Park park;
        ParkReview parkReview;
        String rentId= UUIDS.getUUID();
        //根据输入的车位地址和车位名查询到车位identify_flag 0提交 1通过 2未通过，通过的话允许发布
        park = parkMapper.selectIdentifyFlagByParkAddressAndName(rentOutInfoVO.getParkName(),rentOutInfoVO.getParkAddress());
        UserReview res = userReviewMapper.selectStatusByUserId(getUser.getUserId());
        if (res==null){
            throw new BaseException(56,"还未实名认证,不能发布出租信息！");
        }else if(res.getStatus()==1){
            if (park.getIdentifyFlag()==1){
                //获取发布时间
                LocalDateTime localDateTime = LocalDateTime.now();
                Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());

                //localDateTime time = localDateTime.parse("rentInfoVO.getStartTime()", DateTimeFormatter.ISO_DATE);
                String startTimeStr = rentOutInfoVO.getStartTime();
                String endTimeStr = rentOutInfoVO.getEndTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date startTime = null;
                Date endTime = null;
                try {
                    startTime = format.parse(startTimeStr);
                    endTime = format.parse(endTimeStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                rentInfo.setRentInfoId(rentId);
                rentInfo.setTitle(rentOutInfoVO.getTitle());
                rentInfo.setLongFlag(rentOutInfoVO.getLongFlag());
                rentInfo.setDescription(rentOutInfoVO.getDescription());
                rentInfo.setFreeFlag(rentOutInfoVO.getFreeFlag());
                rentInfo.setViews(0);
                if (rentOutInfoVO.getFreeFlag()==0){
                    rentInfo.setPrice((long) 0);
                }else{
                    rentInfo.setPrice(rentOutInfoVO.getPrice());
                }
                rentInfo.setTypeId("1");
                rentInfo.setViews(0);
                rentInfo.setConfirmStatus(0);//初始状态
                rentInfo.setUserId(getUser.getUserId());
                rentInfo.setPublishTime(date);
                int result = date.compareTo(startTime);
                int result1 = startTime.compareTo(endTime);
                if(result==-1&&result1==-1){
                    rentInfo.setStartTime(startTime);
                    rentInfo.setEndTime(endTime);
                }else{
                    throw new BaseException(2,"你填的时间有问题……出错啦！");
                }
//            根据parkid去找parkreview的id然后put进去
                parkReview =parkReviewMapper.selectReviewInfoByParkId(park.getParkId());
                rentInfo.setParkReviewId(parkReview.getParkReviewId());
                rentInfoMapper.insert(rentInfo);
            }else if(park.getIdentifyFlag()==0){
                throw new BaseException(3,"别着急，车位正在审核中……");
            }else{//2未通过
                throw new BaseException(4,"你的车位没有审核通过，不能发布……");
            }
        }else if(res.getStatus()==2){
            throw new BaseException(57,"未通过实名认证,不能发布出租信息！");
        }else{
            throw new BaseException(58,"未实名认证,不能发布出租信息！");
        }
    }
        //再根据车位查到communityname
        //根据输入的社区名和车位名车位地址判断这个车位是否认证,有的话加上park_review_id
        //1、根据社区名找到社区编号
//        Community communityInfo = communityMapper.selectCommuityIdByCommunityName(rentOutInfoVO.getCommunityName());
//        if (communityInfo!=null){
//            //2、根据社区编号和车位名和车位地址找 identify_flag 0提交 1通过 2未通过
//            Park park1 = parkMapper.selectIdentifyFlagByParkIdAndName(communityInfo.getCommunityId(),rentOutInfoVO.getParkName(),rentOutInfoVO.getParkAddress());
//            if (park1!=null){
//                //3、如果是1的话该内容就可以被发布，不然的话就直接抛异常（“该车位正在审核”或者“该车位没通过审核”）
//                if (park1.getIdentifyFlag()==1){
//                    //获取发布时间
//                    LocalDateTime localDateTime = LocalDateTime.now();
//                    Date date = Date.from(localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant());
//
//                    //localDateTime time = localDateTime.parse("rentInfoVO.getStartTime()", DateTimeFormatter.ISO_DATE);
//                    String startTimeStr = rentOutInfoVO.getStartTime();
//                    String endTimeStr = rentOutInfoVO.getEndTime();
//                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                    Date startTime = null;
//                    Date endTime = null;
//                    try {
//                        startTime = format.parse(startTimeStr);
//                        endTime = format.parse(endTimeStr);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    User getUser = (User) SecurityUtils.getSubject().getPrincipal();
//
//                    rentInfo.setRentInfoId(rentId);
//                    rentInfo.setTitle(rentOutInfoVO.getTitle());
//                    rentInfo.setLongFlag(rentOutInfoVO.getLongFlag());
//                    rentInfo.setDescription(rentOutInfoVO.getDescription());
//                    rentInfo.setFreeFlag(rentOutInfoVO.getFreeFlag());
//                    rentInfo.setViews(0);
//                    if (rentOutInfoVO.getFreeFlag()==0){
//                        rentInfo.setPrice((long) 0);
//                    }else{
//                        rentInfo.setPrice(rentOutInfoVO.getPrice());
//                    }
//                    rentInfo.setTypeId("1");
//                    rentInfo.setViews(0);
//                    rentInfo.setConfirmStatus(0);//初始状态
//                    rentInfo.setUserId(getUser.getUserId());
//                    rentInfo.setPublishTime(date);
//                    int result = date.compareTo(startTime);
//                    int result1 = startTime.compareTo(endTime);
//                    if(result==-1&&result1==-1){
//                        rentInfo.setStartTime(startTime);
//                        rentInfo.setEndTime(endTime);
//                    }else{
//                        throw new BaseException(2,"你填的时间有问题……出错啦！");
//                    }
//                    //根据parkid去找parkreview的id然后put进去
//                    parkReview =parkReviewMapper.selectReviewInfoByParkId(park1.getParkId());
//                    rentInfo.setParkReviewId(parkReview.getParkReviewId());
//                    rentInfoMapper.insert(rentInfo);
//
//                }else if(park1.getIdentifyFlag()==0){
//                    throw new BaseException(3,"别着急，车位正在审核中……");
//                }else{
//                    throw new BaseException(4,"你的车位没有审核通过，不能发布……");
//                }
//            }else{
//                throw new BaseException(5,"找不到车位！");
//            }
//        }else{
//            throw new BaseException(6,"还没审核呢！");
//        }

    public int batchInsert(List<RentInfo> list) {
        return rentInfoMapper.batchInsert(list);
    }


    public int insertOrUpdate(RentInfo record) {
        return rentInfoMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(RentInfo record) {
        return rentInfoMapper.insertOrUpdateSelective(record);
    }

}
