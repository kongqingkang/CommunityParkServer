package cn.kqk.common.utils.encryption;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author lhr
 * 2019/12/28 23:34
 * 生成密码
 */
public class GeneratePassword {
    /**
     * 生成密码
     *
     * @param originPwd 原始密码
     * @param salt      盐
     * @return 加密后的密码
     */
    public static String password(String originPwd, String salt) {
        SimpleHash simpleHash = new SimpleHash("md5", originPwd, salt, 2);
        return simpleHash.toHex();
    }
}
