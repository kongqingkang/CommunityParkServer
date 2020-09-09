package cn.kqk.common.aop;

import cn.kqk.common.bean.vo.BaseVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerAcpect {

    /**
     * 定义切入点
     */
    @Pointcut("execution(public * cn.kqk.*.controller..*.*(..))")
    public void controller() {
    }

    /**
     * 检查参数不可为空
     *
     * @param joinPoint 切入点对象
     * @throws Throwable 异常
     */
    @Before("controller()")
    public void validateNull(JoinPoint joinPoint) throws Throwable {
        Object[] objects = joinPoint.getArgs();
        for (Object object : objects) {
            if (object instanceof BaseVO) {
                ((BaseVO) object).validate();
            }
        }
    }
}
