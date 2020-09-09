package cn.kqk.common.bean.vo.response;

import cn.kqk.common.bean.vo.BaseVO;
import lombok.Data;

import java.util.List;

/**
 * @author lhr
 * 2019/12/29 21:33
 * 树
 */
@Data
public class TreeData extends BaseVO {
    private String id;
    private String title;
    private String label;
    private Boolean expand;
    private Boolean loading;
    private Boolean checked;
    private Object data;
    private List<TreeData> children;
}
