package cn.kqk.community_park.bean.dto;

import lombok.Data;

/**
 * @auhtor kqk
 * @date 2020/4/19 0019 - 19:00
 */
@Data
public class RentInfoDTO {
    private String avatarUrl;
    private String truthName;
    private Integer views;

    private String typeId;
    private String address;
    private String title;
    private String description;
    private String mobile;
    private String communityName;
    private String communityAddress;
    private String startTime;
    private String endTime;
    private Integer longFlag;
    //信息总数
    private Long price;
    private String publishTime;
    //判断是不是本人
    private String selfFlag;

    //  判断是不是已经对该求租信息预留过了（首先要做权限认证）
    private String wantId;
    private Integer wantStatus;
//    private List<Integer> wantStatus;


}
