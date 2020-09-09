package cn.kqk.common.utils.http;

import cn.kqk.common.bean.vo.response.CommonResponse;

public class ResultUtils {
    public static CommonResponse result(Integer code, String msg, Object data) {
        CommonResponse result = new CommonResponse();
        result.setCode(code);
        result.setMsg(msg);
        if (null != data) {
            result.setData(data);
        }
        return result;
    }

    public static CommonResponse success(String msg, Object data) {
        return result(0, msg, data);
    }

    public static CommonResponse success(Object data) {
        return success("", data);
    }

    public static CommonResponse success() {
        return success(null);
    }

    public static CommonResponse success(String msg) {
        return success(msg, null);
    }

    public static CommonResponse error(Integer code, String msg) {
        return result(code, msg, null);
    }

    public static CommonResponse error(Integer code, String msg, Object data) {
        return result(code, msg, data);
    }
}
