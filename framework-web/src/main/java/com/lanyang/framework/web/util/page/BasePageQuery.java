package com.lanyang.framework.web.util.page;

import com.lanyang.framework.web.domain.BaseQueryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lanyang
 * @date 2025/6/18
 * @des
 */
@ApiModel("分页查询条件基类")
@Data
public class BasePageQuery extends BaseQueryEntity {

    @ApiModelProperty("第几页")
    private Integer pageNum;

    @ApiModelProperty("每页条数")
    private Integer pageSize;
}
