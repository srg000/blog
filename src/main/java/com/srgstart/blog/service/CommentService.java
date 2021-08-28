package com.srgstart.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.srgstart.blog.entity.Comment;
import com.srgstart.blog.vo.CommentVO;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/22 9:55
 * @Description TODO
 */
public interface CommentService extends IService<Comment> {

    /**
     * 根据博客id查询该博客的所有评论
     * @param blogId 博客id
     * @return 评论集合
     */
    List<CommentVO> getCommentsById(Long blogId);

    /**
     * 保存评论信息
     * @param comment 评论表单
     * @return  返回是否成功
     */
    int saveComment(Comment comment, Long userId);
}

