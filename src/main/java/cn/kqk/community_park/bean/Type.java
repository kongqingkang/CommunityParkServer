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
@Table(name = "`type`")
public class Type implements Serializable {
    @Id
    @Column(name = "type_id")
    private String typeId;

    @Column(name = "type_name")
    private String typeName;

    private static final long serialVersionUID = 1L;
}
