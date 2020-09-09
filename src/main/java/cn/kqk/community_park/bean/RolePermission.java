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
@Table(name = "role_permission")
public class RolePermission implements Serializable {
    /**
     * 角色编号
     */
    @Id
    @Column(name = "role_id")
    private String roleId;

    /**
     * 权限编号
     */
    @Column(name = "permisssion_id")
    private String permisssionId;

    private static final long serialVersionUID = 1L;
}
