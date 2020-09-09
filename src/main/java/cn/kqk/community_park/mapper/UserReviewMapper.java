package cn.kqk.community_park.mapper;

import cn.kqk.common.mapper.BaseMapper;
import cn.kqk.community_park.bean.UserReview;
import cn.kqk.community_park.bean.dto.UserIdentifyDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @date 2020/4/10 19:53
 * @author kqk
 */
@Mapper
public interface UserReviewMapper extends BaseMapper<UserReview> {
    int updateBatch(List<UserReview> list);

    int updateBatchSelective(List<UserReview> list);

    int batchInsert(@Param("list") List<UserReview> list);

    int insertOrUpdate(UserReview record);

    int insertOrUpdateSelective(UserReview record);

    int updateImageByUserReview(UserReview record);

    int updateImageByUserReview1(UserReview record);
    
    UserReview selectTruthNameByUserId(@Param("applyer") String applyer);

    UserReview selectUserReviewInfoByUserId(@Param("applyer") String applyer);

    UserReview selectUserReviewInfoByNameAndMobile(@Param("truthName") String truthName, @Param("mobile") String mobile);

    UserReview selectStatusByUserId(@Param("userId") String userId);

    int selectNoIdentify();

    List<UserIdentifyDTO> findUserIndentify();

    int updateStatusByUserReviewIdAndUpdateReviewer(@Param("userId") String userId, @Param("userReviewId") String userReviewId);

    UserReview selectUserIdByUserReviewId(@Param("userReviewId") String userReviewId);

    UserReview selectStatusByUserReviewId(@Param("userReviewId") String userReviewId);

    int updateStatusByUserUnReviewIdAndUpdateReviewer(@Param("userId") String userId,@Param("userReviewId") String userReviewId);

    UserIdentifyDTO selectPhotoByuserReviewId(@Param("userReviewId") String userReviewId);
}
