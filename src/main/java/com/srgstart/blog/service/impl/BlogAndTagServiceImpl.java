package com.srgstart.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.srgstart.blog.entity.BlogAndTag;
import com.srgstart.blog.mapper.BlogAndTagMapper;
import com.srgstart.blog.service.BlogAndTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/17 21:06
 * @Description TODO
 */
@Service
public class BlogAndTagServiceImpl extends ServiceImpl<BlogAndTagMapper, BlogAndTag> implements BlogAndTagService {

    @Autowired
    private BlogAndTagMapper blogAndTagMapper;

    @Override
    public List<Long> findTagIdByBlogId(Long blogId) {
        return blogAndTagMapper.findTagIdByBlogId(blogId);
    }
}
