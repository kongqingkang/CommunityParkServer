package cn.kqk.community_park.mapper;

import cn.kqk.common.mapper.BaseMapper;
import cn.kqk.community_park.bean.RentPark;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Mapper
public interface RentParkMapper extends BaseMapper<RentPark> {
    int batchInsert(@Param("list") List<RentPark> list);

    int insertOrUpdate(RentPark record);

    int insertOrUpdateSelective(RentPark record);
}
