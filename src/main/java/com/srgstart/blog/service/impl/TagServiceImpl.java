package com.srgstart.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.srgstart.blog.entity.Blog;
import com.srgstart.blog.entity.BlogAndTag;
import com.srgstart.blog.entity.Tag;
import com.srgstart.blog.mapper.*;
import com.srgstart.blog.service.TagService;
import com.srgstart.blog.util.HtmlToPlainText;
import com.srgstart.blog.util.MarkdownToHtml;
import com.srgstart.blog.vo.BlogVO;
import com.srgstart.blog.vo.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author srgstart
 * @Create 2021/8/16 20:58
 * @Description TODO
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private BlogAndTagMapper blogAndTagMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Tag> findAllTags() {
        return tagMapper.findAllTags();
    }

    @Override
    public List<Tag> getTagBySort(int maxTagCount) {
        // 查询所有的标签id
        List<Long> tagIds = tagMapper.getAllTagIds();
        // 给每个标签添加博客数量
        for (Long tagId : tagIds) {
            tagMapper.updateTagCount(tagId);
        }
        // 返回排好序的标签列表
        return tagMapper.getTagBySort(maxTagCount);
    }

    @Override
    public PageInfo<BlogVO> getBlogListByTagId(Long tagId, int currentPage, int limit) {
        // 判断页码
        if (currentPage < 0) {
            currentPage = 0;
        }
        QueryWrapper<BlogAndTag> wrapper = new QueryWrapper<>();
        wrapper.eq("tag_id",tagId);
        Integer dataNum = blogAndTagMapper.selectCount(wrapper);
        int pageNum = (dataNum%limit == 0) ? dataNum/limit : dataNum/limit + 1;
        if (currentPage+1 > pageNum) {
            if (pageNum != 0) {
                currentPage = pageNum - 1;
            }
        }

        // 改变开始页面
        int begin = currentPage * limit;

        if (limit <= 0) {
            limit = 5;
        }
        // 查询数据并返回到页面
        // 查询标签id对应的所有博客id
        List<Long> blogIdList = blogAndTagMapper.findBlogIdByTagId(tagId);

        if (blogIdList != null && blogIdList.size() > 0) {
            List<Blog> blogList = blogMapper.getBlogListByIds(blogIdList,begin, limit);
            List<BlogVO> blogVOList = new ArrayList<>();
            // 将blog 转换为 blogVO
            for (Blog blog : blogList) {
                BlogVO blogVO = new BlogVO();
                BeanUtils.copyProperties(blog,blogVO);
                // 将type对象填充进来
                blogVO.setBlogType(typeMapper.getTypeById(blog.getTypeId()));

                // 将博客内容从markdown语法，转换为纯文本
                String htmlContent = MarkdownToHtml.convert(blogVO.getContent());
                String content = HtmlToPlainText.convert(htmlContent);
                // 不是全部显示文本，要截取显示
                String finalContent = content.substring(0,100) + "...";

                blogVO.setContent(finalContent);

                // 将每篇文章的作者添加到vo中
                blogVO.setUser(userMapper.selectById(blogVO.getUserId()));

                // 把更新时间格式化
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

                //java.util.Date对象
                Date date = null;
                try {
                    date = (Date) sdf.parse(blog.getUpdateTime().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //2009-09-16 11:26:23
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = dateFormat.format(date);
                blogVO.setParseUpdateTime(format);
                blogVOList.add(blogVO);
            }
            PageInfo<BlogVO> pageInfo = new PageInfo<>();
            pageInfo.setDataNum(dataNum);
            pageInfo.setCurrentPage(currentPage);
            pageInfo.setPageNum(pageNum);
            pageInfo.setDataList(blogVOList);
            return pageInfo;
        } else {
            PageInfo<BlogVO> pageInfo = new PageInfo<>();
            pageInfo.setDataNum(dataNum);
            pageInfo.setCurrentPage(currentPage);
            pageInfo.setPageNum(pageNum);
            pageInfo.setDataList(null);
            return pageInfo;
        }

    }
}
