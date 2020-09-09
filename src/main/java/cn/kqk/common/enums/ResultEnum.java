package cn.kqk.common.enums;

/**
 * @ClassName ResultEnum
 * @Description
 * @Author sl
 * @Date 2018/10/25 16:02
 * @Version 1.0
 */
public enum ResultEnum {
    UNKOWN_ERROR(-1, "未知错误"),
    SUCCESS(0, "成功");
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
