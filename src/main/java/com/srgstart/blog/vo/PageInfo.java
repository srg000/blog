package com.srgstart.blog.vo;

import lombok.Data;

import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/16 16:22
 * @Description TODO
 */
@Data
public class PageInfo<T> {

    /**
     * 当前页码
     */
    private Integer currentPage;
    /**
     * 总页码
     */
    private Integer pageNum;
    /**
     * 总记录数
     */
    private Integer dataNum;
    /**
     * 记录列表
     */
    private List<T> dataList;

}
