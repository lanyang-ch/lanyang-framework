package com.lanyang.framework.common.domain;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lanyang
 * @date 2025/6/12
 * @des
 */
@Data
public class BaseQueryEntity {

    private Map<String, Object> params = new HashMap<>();
}
