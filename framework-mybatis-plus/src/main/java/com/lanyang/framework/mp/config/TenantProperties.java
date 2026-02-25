package com.lanyang.framework.mp.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lanyang
 * @date 2025/4/30
 * @des
 */
@ConfigurationProperties(prefix = "mp.tenant")
@Getter
@Setter
public class TenantProperties {

    @ApiModelProperty("是否开启多租户")
    private Boolean enable = true;

    @ApiModelProperty("多租户字段名称")
    private String column = "tenant_id";

    @ApiModelProperty("忽略多租户的表(表中没有租户信息)")
    private List<String> ignoreTables = new ArrayList<>();

    @ApiModelProperty("创建人列名")
    private String createUserColumn = "createBy";

    @ApiModelProperty("更新人列名")
    private String updateUserColumn = "updateBy";

    @ApiModelProperty("创建时间列名")
    private String createTimeColumn = "createTime";

    @ApiModelProperty("更新时间列名")
    private String updateTimeColumn = "updateTime";

    @ApiModelProperty("创建人更新人值特殊值，当时该值时，不做属性自动填充")
    private String defaultCreateUpdateUser = "system";
}
