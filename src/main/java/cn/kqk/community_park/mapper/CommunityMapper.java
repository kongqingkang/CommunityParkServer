package cn.kqk.community_park.mapper;

import cn.kqk.common.mapper.BaseMapper;
import cn.kqk.community_park.bean.Community;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Mapper
public interface CommunityMapper extends BaseMapper<Community> {
    int updateBatch(List<Community> list);

    int updateBatchSelective(List<Community> list);

    int batchInsert(@Param("list") List<Community> list);

    int insertOrUpdate(Community record);

    int insertOrUpdateSelective(Community record);

    //根据社区名查找是否该社区已经存在
    //在出租发布中根据社区名找到社区编号
    Community selectCommuityByCommunityName(@Param("communityName") String communityName, @Param("communityAddress") String communityAddress);
    Community selectCommuityIdByCommunityName(@Param("communityName") String communityName);
    Community selectCommunityInfoById(@Param("communityId") String communityId);
}
