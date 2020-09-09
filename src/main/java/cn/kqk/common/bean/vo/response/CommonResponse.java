package cn.kqk.common.bean.vo.response;

import cn.kqk.common.bean.vo.BaseVO;
import lombok.Data;

/**
 * @author lhr
 * 2019/12/27 8:25
 * 通用返回对象
 */
@Data
public class CommonResponse extends BaseVO {
    /**
     * 错误码
     */
    int code;
    /**
     * 提示信息
     */
    String msg;
    /**
     * 业务数据
     */
    Object data;
}
