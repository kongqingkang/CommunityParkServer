package cn.kqk.community_park.service;

import cn.kqk.common.exception.BaseException;
import cn.kqk.common.utils.database.UUIDS;
import cn.kqk.community_park.bean.UserInfo;
import cn.kqk.community_park.bean.UserReview;
import cn.kqk.community_park.bean.dto.AboutDTO;
import cn.kqk.community_park.bean.dto.SelfDTO;
import cn.kqk.community_park.bean.dto.UserDTO;
import cn.kqk.community_park.bean.vo.AboutVO;
import cn.kqk.community_park.mapper.UserInfoMapper;
import cn.kqk.community_park.mapper.UserReviewMapper;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Service
@Transactional
public class UserInfoService{

    private UserInfoMapper userInfoMapper;
    private UserReviewMapper userReviewMapper;

    public UserInfoService(UserInfoMapper userInfoMapper, UserReviewMapper userReviewMapper) {
        this.userInfoMapper = userInfoMapper;
        this.userReviewMapper = userReviewMapper;
    }

    public AboutDTO getgetAboutInfoByUserId(String userId){
            AboutDTO aboutDTO = new AboutDTO();
            UserInfo userInfo = null;
            userInfo = userInfoMapper.selectUserInfoByUserId(userId);
            aboutDTO.setAddress(userInfo.getAddress());
            aboutDTO.setMobile(userInfo.getMobile());
            aboutDTO.setTencent(userInfo.getTencent());
            aboutDTO.setAvatarUrl(userInfo.getAvatarUrl());
            return aboutDTO;
        }

    /**
     * 获取页面上方导航栏的头像和真实姓名
     * @return
     */
    public SelfDTO getSelfInfo(){
//        User getUser = (User) SecurityUtils.getSubject().getPrincipal();
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        SelfDTO selfDTO = new SelfDTO();
        UserInfo userInfo = userInfoMapper.selectUserInfoByUserId(getUser.getUserId());
        UserReview u = userReviewMapper.selectTruthNameByUserId(getUser.getUserId());

        if(u==null){
            selfDTO.setTruthName("未认证");
            selfDTO.setAvatarUrl("http://kqk19961027:9000/CommunityPark/img/ae65ae9be239460d9a23b0f4e37542e4.jpg");
        }else{
            selfDTO.setTruthName(u.getTruthName());
            selfDTO.setAvatarUrl(userInfo.getAvatarUrl());
        }
        return selfDTO;
    }


    /**
     * 前台获取用户信息
     * @return
     */
    public AboutDTO getAboutInfo(){
//        User getUser = (User) SecurityUtils.getSubject().getPrincipal();
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();

        AboutDTO aboutDTO = new AboutDTO();
        UserInfo userInfo = null;
        userInfo = userInfoMapper.selectUserInfoByUserId(getUser.getUserId());
        aboutDTO.setAddress(userInfo.getAddress());
        aboutDTO.setMobile(userInfo.getMobile());
        aboutDTO.setTencent(userInfo.getTencent());
        aboutDTO.setAvatarUrl(userInfo.getAvatarUrl());
        return aboutDTO;
    }


    /**
     * 修改个人信息
     * @param aboutVO
     */
    public void editPersonalInfo(AboutVO aboutVO, MultipartFile reviewImg){
//        User getUser = (User) SecurityUtils.getSubject().getPrincipal();
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        //mobile address tencent
        int flag = 0;
        if (reviewImg != null) {
            userInfoMapper.updateUserInfo(aboutVO.getMobile(),aboutVO.getAddress(),aboutVO.getTencent(),getUser.getUserId());
            //存储图片
            try {
                //找到UserInfoId
                UserInfo userInfo = userInfoMapper.selectUserInfoIdByUserId(getUser.getUserId());
                String realPath="";
                String realNetPlath="";
                if (reviewImg.getSize() / 10000 > 100) {
                    throw new BaseException(9, "图片大小不能超过1000KB……");
                } else {
                    //判断上传文件格式
                    String fileType = reviewImg.getContentType();
                    if (fileType.equals("image/jpeg") || fileType.equals("image/png") || fileType.equals("image/jpeg")) {
                        // 要上传的目标文件存放的绝对路径
                        final String localPath = "F:\\IDEAProject\\static\\img";
                        //映射地址
                        final String netPath = "http://kqk19961027:9000/CommunityPark/img";
                        //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
                        //获取文件名
                        String fileName = reviewImg.getOriginalFilename();
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
                            reviewImg.transferTo(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //result_msg="图片格式不正确";
                        throw new BaseException(11, "图片格式不正确……");
                    }
                    if (userInfo.getAvatarUrl()==null){
                        userInfo.setAvatarUrl("http://kqk19961027:9000/CommunityPark/img/ae65ae9be239460d9a23b0f4e37542e4.jpg");
                    }else{
                        userInfo.setAvatarUrl(realNetPlath);
                    }
                }
                flag = userInfoMapper.updateAvatarUrlByUserInfoId(realNetPlath,userInfo.getUserInfoId());
                //flag=userInfoMapper.insertOrUpdate(userInfo);
                if (flag<0) {
                    throw new BaseException(3, "创建图片1地址失败");
                }
            } catch (Exception e) {
                throw new BaseException(4, e.getMessage());
            }
        } else {
            userInfoMapper.updateUserInfo(aboutVO.getMobile(),aboutVO.getAddress(),aboutVO.getTencent(),getUser.getUserId());
        }
    }


    public int batchInsert(List<UserInfo> list) {
        return userInfoMapper.batchInsert(list);
    }


    public int insertOrUpdate(UserInfo record) {
        return userInfoMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(UserInfo record) {
        return userInfoMapper.insertOrUpdateSelective(record);
    }

}
