package cn.kqk.community_park.bean.dto;

import lombok.Data;

/**
 * @auhtor kqk
 * @date 2020/4/19 0019 - 19:00
 */
@Data
public class ArchivesRentInfoDTO {
//    private String title;
    //rentinfo表
    //private String rentInfoId;
    //private String userId;
//    private String description;
//    private String truthName;
//    private String avatarUrl;
//    private Integer views;
//    private String typeId;
//    private String publishTime;
//    private List<ArchivesRentInfoDTO> archivesRentInfoDTOList;
    //车位审核
    private String parkReviewId;
    //关于车位的信息
    //park表
    private String parkId;
    private String location;//车位地址
    private String parkName;//车位号
    private String communityName;
    private Integer identifyFlag;
    private Integer rentoutFlag;
}
