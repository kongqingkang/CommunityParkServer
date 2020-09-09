package cn.kqk.common.utils.reflect;

import java.lang.reflect.Field;

/**
 * @author lhr
 * 2019/12/29 19:27
 * 反射工具类
 */
public class ReflectUtil {
    /**
     * 根据属性名获取属性值
     *
     * @param fieldName 属性名
     * @param object    要获取值的对象
     * @return 属性值
     */
    public static String getFieldValueByFieldName(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            //设置对象的访问权限，保证对private的属性的访问
            field.setAccessible(true);
            return (String) field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
