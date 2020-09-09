package cn.kqk.community_park.bean.dto;

import lombok.Data;

/**
 * @auhtor kqk
 * @date 2020/4/30 0030 - 10:12
 */
@Data
public class UserIdentifyDTO {
    private String userReviewId;
    private String truthName;
    private String mobile;
    private String identityCard;
    private String sex;
    private String cardUrl1;
    private String cardUrl2;
    private String status;
}
