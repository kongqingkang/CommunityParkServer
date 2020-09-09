package cn.kqk.community_park.bean.dto;

import lombok.Data;

/**
 * @auhtor kqk
 * @date 2020/4/17 0017 - 16:15
 */
@Data
public class RentOutInfoDTO {
    //头像、真实姓名、浏览次数和发表时间
    private String avatarUrl;
    private String truthName;
    private Integer views;
    private String publishTime;
    //社区名
    private String communityName;
    //车位地址和社区号
    private String parkAddress;
    private String parkName;

    private String parkReviewId;
    //    类型
    private String typeId;
//    标题、备注开始结束时间
    private String title;
    private String description;
    private String mobile;
    private String startTime;
    private String endTime;
//    长短租、是否免费、价格
    private Integer longFlag;
    private Integer freeFlag;
    private Long price;
    //判断是是不是本人
    private String selfFlag;

    //  判断是不是已经对该出租信息预留过了（首先要做权限认证）
    private String wantId;
    private Integer wantStatus;
}
