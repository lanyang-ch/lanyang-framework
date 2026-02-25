package com.lanyang.framework.common.domain;

import lombok.Data;

import java.util.List;

/**
 * @author lanyang
 * @date 2025/12/3
 * @des 树结构
 */
@Data
public class TreeVO<T> {

    /** 节点id */
    private T id;

    /** 父节点id */
    private T parentId;

    /** 名称 */
    private String label;

    /** 排序 */
    private Integer sort;

    /** 子节点 */
    private List<TreeVO<T>> children;

}
