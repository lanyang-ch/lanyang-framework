package com.lanyang.framework.common.domain;

import java.util.function.Predicate;

/**
 * @author lanyang
 * @date 2025/12/3
 * @des
 */
public class RootPredicates {

    // parentId为null的节点作为根节点
    public static <T> Predicate<TreeVO<T>> parentIdIsNull() {
        return item -> item.getParentId() == null;
    }

    // parentId为特定值（如-1）的节点作为根节点
    public static <T> Predicate<TreeVO<T>> parentIdEquals(T value) {
        return item -> value.equals(item.getParentId());
    }
}
