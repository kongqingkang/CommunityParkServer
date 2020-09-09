package cn.kqk.community_park.mapper;

import cn.kqk.common.mapper.BaseMapper;
import cn.kqk.community_park.bean.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    int batchInsert(@Param("list") List<RolePermission> list);

    int insertOrUpdate(RolePermission record);

    int insertOrUpdateSelective(RolePermission record);
}
