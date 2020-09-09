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
@Table(name = "park")
public class Park implements Serializable {
    /**
     * 车位编号
     */
    @Id
    @Column(name = "park_id")
    private String parkId;

    @Column(name = "park_name")
    private String parkName;

    @Column(name = "community_id")
    private String communityId;

    /**
     * 是否车位被认证
     */
    @Column(name = "identify_flag")
    private Integer identifyFlag;

    /**
     * 分区车位地址（具体位置）
     */
    @Column(name = "`location`")
    private String location;

    /**
     * 是否已经被出租
     */
    @Column(name = "rentout_flag")
    private Integer rentoutFlag;

    private static final long serialVersionUID = 1L;
}
