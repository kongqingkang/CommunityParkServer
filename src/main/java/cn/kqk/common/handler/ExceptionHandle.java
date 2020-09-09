package cn.kqk.common.handler;

import cn.kqk.common.bean.vo.response.CommonResponse;
import cn.kqk.common.exception.BaseException;
import cn.kqk.common.utils.http.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 *
 * @ClassName ExceptionHandle
 * @Description
 * @Author sl
 * @Date 2018/10/25 15:41
 * @Version 1.0
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonResponse handle(Exception e) {
        e.printStackTrace();
        if (e instanceof BaseException) {
            return ResultUtils.error(((BaseException) e).getCode(), e.getMessage());
        } else if (e instanceof UnknownAccountException) {
            return ResultUtils.error(401, "账号不存在");
        } else if (e instanceof IncorrectCredentialsException) {
            return ResultUtils.error(402, "账号或密码不正确");
        } else if (e instanceof LockedAccountException) {
            return ResultUtils.error(403, "账号已被锁定,请联系管理员");
        } else if (e instanceof DisabledAccountException) {
            return ResultUtils.error(405, "账号已被删除");
        } else if (e instanceof AuthenticationException) {
            return ResultUtils.error(404, "账户验证失败");
        } else {
            return ResultUtils.error(500, "系统错误");
        }
    }
}



