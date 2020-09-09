package cn.kqk.community_park.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rent_info")
public class RentInfo implements Serializable {
    /**
     * 信息id
     */
    @Id
    @Column(name = "rent_info_id")
    private String rentInfoId;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 浏览次数
     */
    @Column(name = "views")
    private Integer views;

    /**
     * 长租还是短租
     */
    @Column(name = "long_flag")
    private Integer longFlag;

    /**
     * 备注
     */
    @Column(name = "description")
    private String description;

    /**
     * 价格
     */
    @Column(name = "price")
    private Long price;

    /**
     * 类型名 0是出租 1求租
     */
    @Column(name = "type_id")
    private String typeId;

    /**
     * 车位认证编号
     */
    @Column(name = "park_review_id")
    private String parkReviewId;

    /**
     * 是否免费
     */
    @Column(name = "free_flag")
    private Integer freeFlag;

    /**
     *  0初始状态 1平台内完成 2 平台外完成 3 失败
     */
    @Column(name = "confirm_status")
    private Integer confirmStatus;

    /**
     * 发表时间
     */
    @Column(name = "publish_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date publishTime;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    /**
     * 截止时间
     */
    @Column(name = "end_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    /**
     * 用户编号
     */
    @Column(name = "user_id")
    private String userId;

    private static final long serialVersionUID = 1L;

}
