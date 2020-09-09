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
@Table(name = "want")
public class Want implements Serializable {
    @Id
    @Column(name = "want_id")
    private String wantId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "rent_info_id")
    private String rentInfoId;

    /**
     * 发布人操作，表示最终结果，是谁获得该车位 出租没有1，求租，有意向是1，所有人确认是2
     */
    @Column(name = "`status`")
    private Integer status;

    private static final long serialVersionUID = 1L;
}
