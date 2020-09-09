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
@Table(name = "`role`")
public class Role implements Serializable {
    /**
     * 角色id
     */
    @Id
    @Column(name = "role_id")
    private String roleId;

    /**
     * 角色名
     */
    @Column(name = "role_name")
    private String roleName;

    /**
     * 是否可用
     */
    @Column(name = "`status`")
    private Integer status;

    private static final long serialVersionUID = 1L;
}
