package com.lanyang.framework.web.util.page;

import com.lanyang.framework.web.util.ServletUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 表格数据处理
 *
 * @author lanyang
 */
public class TableSupport {
    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 分页参数合理化
     */
    public static final String REASONABLE = "reasonable";

    /**
     * 封装分页对象
     */
    public static PageDomain getPageDomain() {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(StringUtils.isBlank(ServletUtils.getParameter(PAGE_NUM))?1:Integer.parseInt(ServletUtils.getParameter(PAGE_NUM)));
        pageDomain.setPageSize(StringUtils.isBlank(ServletUtils.getParameter(PAGE_SIZE))?10:Integer.parseInt(ServletUtils.getParameter(PAGE_SIZE)));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(IS_ASC));
        pageDomain.setReasonable(ServletUtils.getParameterToBool(REASONABLE));
        return pageDomain;
    }

    public static PageDomain buildPageRequest() {
        return getPageDomain();
    }
}
