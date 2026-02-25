package com.lanyang.framework.web.util.page;

import lombok.Data;

import java.util.List;

/**
 * @author lanyang
 * @date 2025/6/17
 * @des
 */

@Data
public class PageData<T> {

    private long total;

    private List<T> rows;
}
