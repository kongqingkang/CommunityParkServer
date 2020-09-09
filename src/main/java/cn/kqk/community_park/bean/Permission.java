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
@Table(name = "permission")
public class Permission implements Serializable {
    /**
     * 权限编号
     */
    @Id
    @Column(name = "permission_id")
    private String permissionId;

    /**
     * 权限名
     */
    @Column(name = "permission_name")
    private String permissionName;

    /**
     * 状态
     */
    @Column(name = "`status`")
    private Integer status;

    private static final long serialVersionUID = 1L;
}
