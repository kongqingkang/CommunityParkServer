package cn.kqk.community_park.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "park_review")
public class ParkReview implements Serializable {
    /**
     * 车位认证编号
     */
    @Id
    @Column(name = "park_review_id")
    private String parkReviewId;

    /**
     * 所有人名字
     */
    @Column(name = "owner_name")
    private String ownerName;

    /**
     * 手机号
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 身份证号
     */
    @Column(name = "identity_card")
    private String identityCard;

    /**
     * 性别
     */
    @Column(name = "sex")
    private String sex;

    /**
     * 社区编号
     */
    @Column(name = "community_id")
    private String communityId;

    /**
     * 车位地址
     */
    @Column(name = "park_address")
    private String parkAddress;

    /**
     * 证明材料
     */
    @Column(name = "evidence_url")
    private String evidenceUrl;

    /**
     * 车位照片
     */
    @Column(name = "park_photo_url")
    private String parkPhotoUrl;

    /**
     * 状态 0未审核 1通过 2未通过
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 用户编号
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 车位编号
     */
    @Column(name = "park_id")
    private String parkId;

    private static final long serialVersionUID = 1L;
}
