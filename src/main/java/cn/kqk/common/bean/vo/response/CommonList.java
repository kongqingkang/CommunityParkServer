package cn.kqk.common.bean.vo.response;

import lombok.Data;

import java.util.List;

/**
 * @description: 通用列表返回类
 * @Author: zxl on 2020/3/21
 * @create: 2020-03-21 09:28
 */
@Data
public class CommonList<T> {

    private List<T> list;

    private Long total;
}
