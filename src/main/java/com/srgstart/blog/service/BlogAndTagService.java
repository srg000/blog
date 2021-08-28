package com.srgstart.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.srgstart.blog.entity.BlogAndTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/17 21:02
 * @Description TODO
 */
public interface BlogAndTagService extends IService<BlogAndTag> {

    List<Long> findTagIdByBlogId( Long blogId);
}
