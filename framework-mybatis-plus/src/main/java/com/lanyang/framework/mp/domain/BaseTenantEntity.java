package com.lanyang.framework.mp.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lanyang
 * @date 2025/5/6
 * @des
 */
@Data
public class BaseTenantEntity extends BaseEntity {

    @ApiModelProperty("租户id")
    private Long tenantId;
}
