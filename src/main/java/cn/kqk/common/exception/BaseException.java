package cn.kqk.common.exception;

import cn.kqk.common.enums.ResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName BaseException
 * @Description
 * @Author sl
 * @Date 2018/11/19 15:44
 * @Version 1.0
 */
public class BaseException extends RuntimeException {

    @Getter
    @Setter
    private Integer code;

    public BaseException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
