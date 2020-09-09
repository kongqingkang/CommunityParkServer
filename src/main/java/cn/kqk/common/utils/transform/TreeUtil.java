package cn.kqk.common.utils.transform;

import cn.kqk.common.bean.vo.response.TreeData;
import cn.kqk.common.utils.reflect.ReflectUtil;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 层级树工具类
 *
 * @author lhr
 * 2019年8月31日
 */
public class TreeUtil {
    /**
     * 每一级的字符串长度
     */
    // TODO: 2020/3/25 把这里改成动态传入的数据，而不是配置死的
    public static final int LEVEL_LENGTH = 3;

    /**
     * 将列表转换为树
     *
     * @param treeList       要转换的列表
     * @param idParamName    id属性名称
     * @param levelParamName 级别属性名称
     * @param titleParamName 标题属性名称
     * @param loading        true-开启异步加载 false-不开启
     * @return 树
     */
    public static List<TreeData> convertTreeWithLevelNum(List treeList, String baseLevel, String idParamName, String levelParamName, String titleParamName, boolean loading) {
        // 定义变量
        List<TreeData> result = new ArrayList<>();
        Map<String, TreeData> temp = new HashMap<>(16);

        // 循环添加节点
        for (Object data : treeList) {
            addNodeLevelNumType(data, result, temp, baseLevel, idParamName, levelParamName, titleParamName, loading);
        }
        return result;
    }

    /**
     * 将列表转换为树
     *
     * @param treeList        要转换的列表
     * @param idParamName     id属性名称
     * @param fatherParamName 父节点属性名称
     * @param titleParamName  标题属性名称
     * @param loading         true-开启异步加载 false-不开启
     * @return 树
     */
    public static List<TreeData> convertTreeWithFatherId(List treeList, String idParamName, String fatherParamName, String titleParamName, boolean loading) {
        // 定义变量
        List<TreeData> result = new ArrayList<>();
        Map<String, TreeData> temp = new HashMap<>(16);

        // 循环添加节点
        for (Object data : treeList) {
            addNodeFatherIdType(data, result, temp, fatherParamName, idParamName, titleParamName, loading);
        }
        return result;
    }

    /**
     * 设置树的选中状态
     *
     * @param tree        树
     * @param checkData   选中数据
     * @param idParamName id属性名称
     */
    public static void setTreeChecked(List<TreeData> tree, List checkData, String idParamName) {
        for (Object data : checkData) {
            String id = ReflectUtil.getFieldValueByFieldName(idParamName, data);
            checkTreeNode(tree, id);
        }
    }

    /**
     * 递归选中树节点
     *
     * @param tree 树
     * @param id   要选中的id
     */
    public static void checkTreeNode(List<TreeData> tree, String id) {
        for (TreeData treeNode : tree) {
            if (id.equals(treeNode.getId())) {
                treeNode.setChecked(true);
                return;
            }
            checkTreeNode(treeNode.getChildren(), id);
        }
    }

    /**
     * 设置节点数据
     *
     * @param node           节点
     * @param data           原始数据
     * @param idParamName    id属性名称
     * @param titleParamName 标题属性名称
     * @param loading        true-开启异步加载 false-不开启
     */
    public static void setNode(TreeData node, Object data, String idParamName, String titleParamName, boolean loading) {
        node.setId(ReflectUtil.getFieldValueByFieldName(idParamName, data));
        node.setTitle(ReflectUtil.getFieldValueByFieldName(titleParamName, data));
        node.setLabel(ReflectUtil.getFieldValueByFieldName(titleParamName, data));
        node.setData(data);
        node.setChildren(new ArrayList<>());
        if (loading) {
            node.setLoading(false);
        }
    }

    /**
     * 添加节点
     *
     * @param data           原始数据
     * @param result         最终结果
     * @param temp           缓存变量
     * @param idParamName    id属性名称
     * @param levelParamName 级别属性名称
     * @param titleParamName 标题属性名称
     * @param loading        true-开启异步加载 false-不开启
     */
    public static void addNodeLevelNumType(Object data, List<TreeData> result, Map<String, TreeData> temp, String baseLevel, String idParamName, String levelParamName, String titleParamName, boolean loading) {
        // 获取当前节点字符串
        String nodeStr = ReflectUtil.getFieldValueByFieldName(levelParamName, data);
        nodeStr = nodeStr.replace(baseLevel, "");
        // 计算需要循环的次数
        int times = (nodeStr.length() - LEVEL_LENGTH) / LEVEL_LENGTH;
        // 开始循环，从最顶级的节点开始添加
        for (int i = 0; i <= times; i++) {
            // 获取当前循环的节点字符串
            String currentNodeStr = nodeStr.substring(0, (i + 1) * LEVEL_LENGTH);
            // 获取当前循环父节点的字符串
            String fatherNodeStr = currentNodeStr.substring(0, currentNodeStr.length() - LEVEL_LENGTH);
            // 当前节点对象
            TreeData currentNode;
            // 判断节点对象是否已存在
            if (!temp.containsKey(currentNodeStr)) {
                // 不存在则新建
                currentNode = new TreeData();
                // 并添加到缓存变量中
                temp.put(currentNodeStr, currentNode);
                // 如果是顶级节点，添加到结果数组中
                if (fatherNodeStr.length() == 0) {
                    result.add(currentNode);
                }
            } else {
                // 如果已存在，则直接从临时变量中取出
                currentNode = temp.get(currentNodeStr);
            }
            // 如果已经循环到最后一次，将数据库中取到的数据存入节点对象中
            if (i == times) {
                setNode(currentNode, data, idParamName, titleParamName, loading);
            }
            // 如果当前循环存在父节点，将节点添加到父节点的children下
            if (fatherNodeStr.length() > 0) {
                List<TreeData> children;
                // 判断子节点数组对象是否已经存在
                if (temp.get(fatherNodeStr).getChildren() != null) {
                    // 已存在则直接取出
                    children = temp.get(fatherNodeStr).getChildren();
                } else {
                    // 不存在则新建
                    children = new ArrayList<>();
                }
                temp.get(fatherNodeStr).setExpand(true);
                // 并将子节点数组对象存入
                temp.get(fatherNodeStr).setChildren(children);
                // 如果子节点数组对象中没有重复的，则将当前节点添加到数组中
                if (!children.contains(currentNode)) {
                    children.add(currentNode);
                }
            }
        }
    }

    /**
     * 添加节点
     *
     * @param data              原始数据
     * @param result            最终结果
     * @param temp              缓存变量
     * @param fatherIdParamName 父节点属性名称
     * @param idParamName       id属性名称
     * @param titleParamName    标题属性名称
     * @param loading           true-开启异步加载 false-不开启
     */
    public static void addNodeFatherIdType(Object data, List<TreeData> result, Map<String, TreeData> temp, String fatherIdParamName, String idParamName, String titleParamName, boolean loading) {
        // 定义变量
        String id = ReflectUtil.getFieldValueByFieldName(idParamName, data);
        String fatherId = ReflectUtil.getFieldValueByFieldName(fatherIdParamName, data);
        TreeData currentNode;

        // 获取或创建当前节点
        if (temp.containsKey(id)) {
            currentNode = temp.get(id);
        } else {
            currentNode = new TreeData();
            temp.put(id, currentNode);
        }
        // 设置当前节点数据
        currentNode.setId(id);
        currentNode.setTitle(ReflectUtil.getFieldValueByFieldName(titleParamName, data));
        currentNode.setLabel(ReflectUtil.getFieldValueByFieldName(titleParamName, data));
        currentNode.setData(data);
        currentNode.setChildren(new ArrayList<>());
        if (loading) {
            currentNode.setLoading(false);
        }
        // 判断是否有父节点
        if (StringUtils.isEmpty(fatherId)) {
            // 没有父节点，说明是顶级节点，加入到最终结果中
            result.add(currentNode);
        } else {
            // 有父节点，获取或创建父节点，并将当前节点加入父节点的children中
            TreeData fatherNode;
            if (temp.containsKey(fatherId)) {
                fatherNode = temp.get(fatherId);
            } else {
                fatherNode = new TreeData();
                temp.put(fatherId, fatherNode);
            }
            fatherNode.setExpand(true);
            if (fatherNode.getChildren() == null) {
                fatherNode.setChildren(new ArrayList<>());
            }
            fatherNode.getChildren().add(currentNode);
        }
    }

    public static String generateNextNodeString(String level, List<Object> list) {
        int number = 0;
        int length = level.length();
        for (Object markers : list) {
            String currentLevel = ReflectUtil.getFieldValueByFieldName("deptNum", markers);
            int currentLength = currentLevel.length();
            if (currentLength == length + LEVEL_LENGTH) {
                String partLevel = currentLevel.substring(length);
                int currentNumber = Integer.parseInt(partLevel);
                if (currentNumber > number) {
                    number = currentNumber;
                }
            }
        }
        number++;
        return level + String.format("%0" + LEVEL_LENGTH + "d", number);
    }

    public static String getNewDeptNo( String deptNo){
        String newDeptNo = "";

        if(deptNo != null && !deptNo.isEmpty()){
            int newEquipment = Integer.parseInt(deptNo) + 1;
            newDeptNo = String.format("%03d", newEquipment);
        }

        return newDeptNo;
    }
}
