package com.lanyang.framework.web.domain;

import lombok.Data;

import java.util.Set;

/**
 * @author lanyang
 * @date 2025/6/12
 * @des 数据权限
 */
@Data
public class DataAuth {

    private String dimension;

    private Set<String> authCodes;
}
