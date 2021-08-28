package com.srgstart.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.srgstart.blog.entity.Blog;
import com.srgstart.blog.entity.Type;
import com.srgstart.blog.vo.BlogVO;
import com.srgstart.blog.vo.PageInfo;

import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/16 11:33
 * @Description TODO
 */
public interface TypeService extends IService<Type> {

    /**
     * 获取所有分类
     * @return 分类集合
     */
    List<Type> getTypeList();

    /**
     * 根据id查询分类
     * @param id 分类id
     * @return 查询到的分类
     */
    Type getTypeById(Long id);

    /**
     *获取指定数量的分类列表（通过排序）
     * @param  maxCount 指定的数量
     * @return 分类集合
     */
    List<Type> getTypeBySort(int maxCount);

    /**
     * 根据分类id查询所有的博客。
     * @param typeId 分类id
     * @return 查询到的博客集合
     */
    PageInfo<BlogVO> getBlogListByTypeId(Long typeId,
                                         int currentPage,
                                         int limit);
}
