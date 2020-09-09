package cn.kqk.community_park.mapper;

import cn.kqk.common.mapper.BaseMapper;
import cn.kqk.community_park.bean.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    int updateBatch(List<Permission> list);

    int updateBatchSelective(List<Permission> list);

    int batchInsert(@Param("list") List<Permission> list);

    int insertOrUpdate(Permission record);

    int insertOrUpdateSelective(Permission record);
}
