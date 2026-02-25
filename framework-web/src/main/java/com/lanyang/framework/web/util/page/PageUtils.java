package com.lanyang.framework.web.util.page;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lanyang.framework.web.util.SqlUtil;

import java.util.List;

/**
 * 分页工具类
 *
 * @author lanyang
 */
public class PageUtils extends PageHelper {
    /**
     * 设置请求分页数据
     */
    public static void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
        Boolean reasonable = pageDomain.getReasonable();
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    public static <T> PageData<T> buildDataTable(List<T> list) {
        PageData rspData = new PageData();
        rspData.setRows(list);
        rspData.setTotal((new PageInfo(list)).getTotal());
        return rspData;
    }

    /**
     * 清理分页的线程变量
     */
    public static void clearPage() {
        PageHelper.clearPage();
    }
}
