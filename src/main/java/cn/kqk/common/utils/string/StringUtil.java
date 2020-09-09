package cn.kqk.common.utils.string;

/**
 * @author lhr
 * 2020/3/31 8:30
 * 字符串工具
 */
public class StringUtil {
    /**
     * 检查所有字符串不为空
     * @param s 字符串
     * @return true-全都不为空，false-有为空的值
     */
    public static boolean checkAllNotEmpty(String... s){
        for (String item: s){
            if(item == null || "".equals(item)){
                return false;
            }
        }
        return true;
    }
}
