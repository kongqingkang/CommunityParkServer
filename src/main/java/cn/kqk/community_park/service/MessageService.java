package cn.kqk.community_park.service;

import cn.kqk.community_park.bean.Message;
import cn.kqk.community_park.bean.dto.MessageDTO;
import cn.kqk.community_park.bean.dto.UserDTO;
import cn.kqk.community_park.mapper.MessageMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Service
public class MessageService{


    private MessageMapper messageMapper;

    public MessageService(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    /**
     * 获取数据（archive）
     * @param pageNum 当前页
     * @param pageSize 页面数据大小
     * @return
     */
    public PageInfo<MessageDTO> messageList(Integer pageNum, Integer pageSize){
//        User getUser = (User) SecurityUtils.getSubject().getPrincipal();
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        PageHelper.startPage(pageNum, pageSize);// 设定当前页码，以及当前页显示的条数
        List<MessageDTO> messageDTOList= messageMapper.getMessageByUserId(getUser.getUserId());
        PageHelper.clearPage();
        PageInfo<MessageDTO> info = new PageInfo<>(messageDTOList);
        info.getTotal();
        info.isHasNextPage();
        return info;
    }

    public int messageCount(){
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        int count = 0;
        count = messageMapper.selectMessageCountByUserId(getUser.getUserId());
        return count;
    }

    /**
     * 读取消息
     */
    public void readMessage(){
        UserDTO getUser = (UserDTO) SecurityUtils.getSubject().getPrincipal();
        //根据用户编号获取到所有的消息id
        List<Message> list = messageMapper.selectMessageIdByUserId(getUser.getUserId());
        //根据消息id更新状态
        messageMapper.ReadFlagUpdateBatch(list);
    }

    public int updateBatch(List<Message> list) {
        return messageMapper.updateBatch(list);
    }


    public int updateBatchSelective(List<Message> list) {
        return messageMapper.updateBatchSelective(list);
    }


    public int batchInsert(List<Message> list) {
        return messageMapper.batchInsert(list);
    }


    public int insertOrUpdate(Message record) {
        return messageMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(Message record) {
        return messageMapper.insertOrUpdateSelective(record);
    }

}
