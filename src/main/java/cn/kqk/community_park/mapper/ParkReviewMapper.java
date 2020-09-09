package cn.kqk.community_park.mapper;

import cn.kqk.common.mapper.BaseMapper;
import cn.kqk.community_park.bean.ParkReview;
import cn.kqk.community_park.bean.dto.ParkReviewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Mapper
public interface ParkReviewMapper extends BaseMapper<ParkReview> {
    int updateBatch(List<ParkReview> list);

    int updateBatchSelective(List<ParkReview> list);

    int batchInsert(@Param("list") List<ParkReview> list);

    int insertOrUpdate(ParkReview record);

    int insertOrUpdateSelective(ParkReview record);

    ParkReview selectReviewInfoByUserId(@Param("user_id") String user_id);

    ParkReview selectReviewInfoByCommunityIdAndParkAddress(@Param("communityId") String communityId, @Param("parkAddress") String parkAddress);

    ParkReview selectReviewInfoByParkId(@Param("parkId") String parkId);

    ParkReview selectParkInfoByParkReviewId(@Param("parkReviewId") String parkReviewId);

    List<ParkReview> selectFreePark(@Param("userId") String userId);

    int selectNoIdentify();

    List<ParkReviewDTO> findParkIndentify();
    
    int updateStatusByParkReviewId(@Param("parkReviewId") String parkReviewId);

    int updateStatusByUserUnReviewId(@Param("parkReviewId") String parkReviewId);

    ParkReview selectUserIdByUserReviewId(@Param("parkReviewId") String parkReviewId);

    ParkReviewDTO selectPhotoByParkReviewId(@Param("parkReviewId") String parkReviewId);
}
