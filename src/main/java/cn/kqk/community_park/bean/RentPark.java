package cn.kqk.community_park.bean;

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
@Table(name = "rent_park")
public class RentPark implements Serializable {
    /**
     * 这张表的意思就是实际的租车位表
     */
    @Id
    @Column(name = "user_id")
    private String userId;

    /**
     * 这张表的意思就是实际的租车位表
     */
    @Column(name = "park_id")
    private String parkId;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    /**
     * 0 正常 1取消 这张表的意思就是实际的租车位表
     */
    @Column(name = "`status`")
    private Integer status;

    private static final long serialVersionUID = 1L;
}
