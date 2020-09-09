package cn.kqk.common.utils.database;

import java.util.UUID;

/**
 * @author wj
 * @version 1.0
 * @className UUIDS
 * @description java自带的UUID
 * @date 2019/8/14 8:45
 */
public class UUIDS {


    /**
     * 生成一个不带 - 的UUID
     *
     * @return 返回一个ID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
