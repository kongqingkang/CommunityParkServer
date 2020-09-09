package cn.kqk.community_park.mapper;

import cn.kqk.common.mapper.BaseMapper;
import cn.kqk.community_park.bean.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
    int batchInsert(@Param("list") List<UserRole> list);

    int insertOrUpdate(UserRole record);

    int insertOrUpdateSelective(UserRole record);

    UserRole selectRoleIdByUserId(@Param("userId") String userId);
}
