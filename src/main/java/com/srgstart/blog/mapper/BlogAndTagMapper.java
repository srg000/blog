package com.srgstart.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.srgstart.blog.entity.BlogAndTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/17 20:58
 * @Description TODO
 */
@Mapper
public interface BlogAndTagMapper extends BaseMapper<BlogAndTag> {

    List<Long> findTagIdByBlogId(@Param("blogId") Long blogId);

    List<Long> findBlogIdByTagId(@Param("tagId") Long tagId);

}
