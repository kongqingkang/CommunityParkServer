package cn.kqk.community_park.bean.dto;

import lombok.Data;

/**
 * @auhtor kqk
 * @date 2020/5/2 0002 - 17:16
 */
@Data
public class ParkReviewDTO {
    /**
     * 后台-管理员审核车辆列表页面数据
     */
    private String parkReviewId;
    private String ownerName;
    private String mobile;
    private String identityCard;
    private String communityName;
    private String parkAddress;
    private String evidenceUrl;
    private String parkPhotoUrl;
    private String status;
}
