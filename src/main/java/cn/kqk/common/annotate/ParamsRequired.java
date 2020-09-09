package cn.kqk.common.annotate;

import cn.kqk.common.exception.BaseException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ParamsRequired {

    public void validate() throws Exception {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            NotNull notNull = field.getAnnotation(NotNull.class);//Annotation:注解
            if (notNull != null) {
                Method m = this.getClass().getMethod("get" + getMethodName(field.getName()));
                Object obj = m.invoke(this);
                if (obj instanceof String && "".equals(obj)) {
                    throw new BaseException(300, notNull.message());
                }
                if (obj == null) {
                    throw new BaseException(300, notNull.message());
                }
            }
        }
    }

    /**
     * 把一个字符串的第一个字母大写
     */
    private String getMethodName(String fildeName) throws Exception {
        byte[] items = fildeName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }
}
