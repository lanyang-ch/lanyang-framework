package com.lanyang.framework.common.utils;

import com.lanyang.framework.common.domain.RootPredicates;
import com.lanyang.framework.common.domain.TreeVO;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author lanyang
 * @date 2025/12/3
 * @des
 */
public class TreeUtils {

    /**
     * 构建树结构
     * @param list
     * @return
     */
    public static <T> List<TreeVO<T>> buildTree(List<TreeVO<T>> list) {

        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }

        List<TreeVO<T>> roots = list.stream().filter(item -> item.getParentId() == null || "0".equals(item.getParentId().toString())).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(roots)){
            return Collections.emptyList();
        }

        //过滤出非根节点，并根据parentId进行分组
        Map<T, List<TreeVO<T>>> parentChildrenMap = list.stream()
                .filter(e->e.getParentId() != null && !"0".equals(e.getParentId().toString()))
                .collect(Collectors.groupingBy(TreeVO::getParentId));

        for (TreeVO root : roots) {
            setChildren(root, parentChildrenMap);
        }
        return roots;

    }

    /**
     * 构建树结构
     * @param list
     * @param rootPredicates 根节点判断规则
     * @return
     */
    public static <T> List<TreeVO<T>> buildTree(List<TreeVO<T>> list, Predicate<TreeVO<T>> rootPredicates) {

        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }

        List<TreeVO<T>> roots = list.stream().filter(rootPredicates).collect(Collectors.toList());
        if(CollectionUtils.isEmpty(roots)){
            return Collections.emptyList();
        }

        //过滤出非根节点，并根据parentId进行分组
        Map<T, List<TreeVO<T>>> parentChildrenMap = list.stream()
                .filter(rootPredicates.negate())
                .collect(Collectors.groupingBy(TreeVO::getParentId));

        for (TreeVO root : roots) {
            setChildren(root, parentChildrenMap);
        }
        return roots;

    }


    /**
     * 构建排序后的树结构
     * @param list
     * @param rootPredicates 判断根节点规则
     * @return
     */
    public static <T> List<TreeVO<T>> buildSortedTree(List<TreeVO<T>> list, Predicate<TreeVO<T>> rootPredicates) {

        if(CollectionUtils.isEmpty(list)){
            return Collections.emptyList();
        }

        List<TreeVO<T>> sortedList = list.stream()
                .sorted(Comparator.comparingInt(TreeVO::getSort))
                .collect(Collectors.toList());

        return buildTree(sortedList, rootPredicates);
    }

    /**
     * 递归设置子节点
     * @param parent
     * @param parentChildrenMap
     */
    private static <T> void setChildren(TreeVO<T> parent, Map<T, List<TreeVO<T>>> parentChildrenMap) {
        List<TreeVO<T>> children = parentChildrenMap.get(parent.getId());
        parent.setChildren(children);
        if(CollectionUtils.isNotEmpty(children)){
            for (TreeVO<T> child : children) {
                setChildren(child, parentChildrenMap);
            }
        }
    }

    public static void main(String[] args) {
        // 创建测试节点（泛型为String）
        List<TreeVO<String>> nodeList = new ArrayList<>();

        // 4个根节点（parentId为null或"0"）
        nodeList.add(createNode("root1", null, "根节点1", 1));
        nodeList.add(createNode("root2", null, "根节点2", 2));
        nodeList.add(createNode("root3", null, "根节点3", 3));
        nodeList.add(createNode("root4", null, "根节点4", null));

        // 根节点1的子节点
        nodeList.add(createNode("root1-1", "root1", "根1-子节点1", 1));
        nodeList.add(createNode("root1-2", "root1", "根1-子节点2", 2));

        // 根节点2的子节点
        nodeList.add(createNode("root2-1", "root2", "根2-子节点1", 1));

        // 根节点1-1的子节点（二级子节点）
        nodeList.add(createNode("root1-1-1", "root1-1", "根1-子1-孙节点1", 1));

        // 构建树结构
        List<TreeVO<String>> tree = TreeUtils.buildTree(nodeList, RootPredicates.parentIdIsNull());

        // 打印树结构（简化输出）
        printTree(tree, 0);
    }

    // 工具方法：创建节点
    private static TreeVO<String> createNode(String id, String parentId, String label, Integer sort) {
        TreeVO<String> node = new TreeVO<>();
        node.setId(id);
        node.setParentId(parentId);
        node.setLabel(label);
        node.setSort(sort);
        return node;
    }

    // 递归打印树结构
    private static void printTree(List<TreeVO<String>> nodes, int level) {
        if (nodes == null){
            return;
        }
        String prefix = "  "; // 层级缩进
        for (int i = 0; i < level; i++) {
            prefix += "  ";
        }
        for (TreeVO<String> node : nodes) {
            System.out.println(prefix + "├─ " + node.getLabel() + " (id: " + node.getId() + ")");
            printTree(node.getChildren(), level + 1); // 递归打印子节点
        }
    }
}
