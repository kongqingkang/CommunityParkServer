package cn.kqk.common.bean.vo;

import cn.kqk.common.annotate.ParamsRequired;
import lombok.Data;

/**
 * @author lhr
 * 2019/12/29 1:30
 * 基础vo
 */
@Data
public class BaseVO extends ParamsRequired {
    private Integer pageSize;
    private Integer pageNum;
    private String keyword;
}
