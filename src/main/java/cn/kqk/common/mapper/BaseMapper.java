package cn.kqk.common.mapper;

import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.ids.DeleteByIdsMapper;

/**
 * 通用基础mapper，提供大多数单表操作
 *
 * @author lhr
 * @date 2018/6/27 16:19
 */
@Repository
@RegisterMapper
public interface BaseMapper<T> extends Mapper<T>, InsertListMapper<T>, DeleteByIdsMapper<T>, ConditionMapper<T>, IdsMapper<T> {
}
