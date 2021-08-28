package com.srgstart.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.srgstart.blog.entity.Tag;
import com.srgstart.blog.vo.BlogVO;
import com.srgstart.blog.vo.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/16 20:57
 * @Description TODO
 */
public interface TagService extends IService<Tag> {

    /**
     * 获取全部的标签
     * @return 标签集合
     */
    List<Tag> findAllTags();

    /**
     *获取指定数量的标签列表（通过排序）
     * @param  maxTagCount 指定的数量
     * @return 标签集合
     */
    List<Tag> getTagBySort(int maxTagCount);

    /**
     * 根据标签id查询所有的博客。
     * @param tagId 标签id
     * @return 查询到的博客集合
     */
    PageInfo<BlogVO> getBlogListByTagId(Long tagId,
                                         int currentPage,
                                         int limit);
}
