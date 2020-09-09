package cn.kqk.community_park.mapper;

import cn.kqk.common.mapper.BaseMapper;
import cn.kqk.community_park.bean.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Mapper
public interface TypeMapper extends BaseMapper<Type> {
    int updateBatch(List<Type> list);

    int updateBatchSelective(List<Type> list);

    int batchInsert(@Param("list") List<Type> list);

    int insertOrUpdate(Type record);

    int insertOrUpdateSelective(Type record);
}
