package cn.kqk.community_park.mapper;

import cn.kqk.common.mapper.BaseMapper;
import cn.kqk.community_park.bean.Message;
import cn.kqk.community_park.bean.dto.MessageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    int updateBatch(List<Message> list);

    int updateBatchSelective(List<Message> list);

    int batchInsert(@Param("list") List<Message> list);

    int insertOrUpdate(Message record);

    int insertOrUpdateSelective(Message record);

    List<MessageDTO> getMessageByUserId(@Param("userId") String userId);

    int selectMessageCountByUserId(@Param("userId") String userId);

    int ReadFlagUpdateBatch(@Param("list") List<Message> list);

    List<Message> selectMessageIdByUserId(@Param("userId") String userId);
}
