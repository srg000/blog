package com.srgstart.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.srgstart.blog.entity.Blog;
import com.srgstart.blog.entity.Comment;
import com.srgstart.blog.mapper.BlogMapper;
import com.srgstart.blog.mapper.CommentMapper;
import com.srgstart.blog.mapper.UserMapper;
import com.srgstart.blog.service.CommentService;
import com.srgstart.blog.vo.CommentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/22 13:26
 * @Description TODO
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<CommentVO> getCommentsById(Long blogId) {
        // 首先查询所有一级评论
        List<Comment> commentList = commentMapper.getCommentsById(blogId);

        // 把comment 转换为 commentVO
        List<CommentVO> commentVOList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment,commentVO);

            // 查询一级评论下的子评论
            commentVO.setReplyComments(commentMapper.getCommentByParentId(comment.getId()));

            commentVOList.add(commentVO);
        }
        return commentVOList;
    }

    @Override
    public int saveComment(Comment comment, Long userId) {
        Blog blog = blogMapper.findBlogById(comment.getBlogId());

        if (blog.getUserId().equals(userId)) {
            comment.setIsBlogger(1);
        }
        return commentMapper.insert(comment);
    }
}
