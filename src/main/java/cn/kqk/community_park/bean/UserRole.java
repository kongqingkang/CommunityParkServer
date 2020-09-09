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
@Table(name = "user_role")
public class UserRole implements Serializable {
    /**
     * 用户编号
     */
    @Id
    @Column(name = "user_id")
    private String userId;

    /**
     * 角色编号
     */
    @Column(name = "role_id")
    private String roleId;

    private static final long serialVersionUID = 1L;
}
