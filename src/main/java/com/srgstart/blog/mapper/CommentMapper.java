package com.srgstart.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.srgstart.blog.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/22 13:20
 * @Description TODO
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 根据博客id查询该博客的所有评论
     * @param blogId 博客id
     * @return 评论集合
     */
    List<Comment> getCommentsById(@Param("blogId") Long blogId);

    /**
     * 根据博客父id查询该博客的所有评论
     * @param parentId 博客父id
     * @return 评论集合
     */
    List<Comment> getCommentByParentId(@Param("parentId") Long parentId);
}
