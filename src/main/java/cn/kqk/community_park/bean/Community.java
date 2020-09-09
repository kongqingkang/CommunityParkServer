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
@Table(name = "community")
public class Community implements Serializable {
    /**
     * 社区编号
     */
    @Id
    @Column(name = "community_id")
    private String communityId;

    /**
     * 社区名
     */
    @Column(name = "community_name")
    private String communityName;

    /**
     * 社区地址
     */
    @Column(name = "community_address")
    private String communityAddress;

    private static final long serialVersionUID = 1L;
}
