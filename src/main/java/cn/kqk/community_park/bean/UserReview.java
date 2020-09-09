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
@Table(name = "user_review")
public class UserReview implements Serializable {
    /**
     * 实名认证编号
     */
    @Id
    @Column(name = "user_review_id")
    private String userReviewId;

    /**
     * 真实姓名
     */
    @Column(name = "truth_name")
    private String truthName;

    /**
     * 手机号
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 身份证号码
     */
    @Column(name = "identity_card")
    private String identityCard;

    /**
     * 性别
     */
    @Column(name = "sex")
    private String sex;

    /**
     * 身份证正面
     */
    @Column(name = "card_url1")
    private String cardUrl1;

    /**
     * 身份证反面
     */
    @Column(name = "card_url2")
    private String cardUrl2;

    /**
     * 状态 0未审核 1通过 2未通过
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 审核人
     */
    @Column(name = "reviewer")
    private String reviewer;

    /**
     * 申请人
     */
    @Column(name = "applyer")
    private String applyer;

    private static final long serialVersionUID = 1L;
}
