package cn.kqk.community_park.service;

import cn.kqk.community_park.bean.Type;
import cn.kqk.community_park.mapper.TypeMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 *
 * @date 2020/4/10 19:53
 * @author lhr
 */
@Service
public class TypeService{

    @Resource
    private TypeMapper typeMapper;


    public int updateBatch(List<Type> list) {
        return typeMapper.updateBatch(list);
    }


    public int updateBatchSelective(List<Type> list) {
        return typeMapper.updateBatchSelective(list);
    }


    public int batchInsert(List<Type> list) {
        return typeMapper.batchInsert(list);
    }


    public int insertOrUpdate(Type record) {
        return typeMapper.insertOrUpdate(record);
    }


    public int insertOrUpdateSelective(Type record) {
        return typeMapper.insertOrUpdateSelective(record);
    }

}
