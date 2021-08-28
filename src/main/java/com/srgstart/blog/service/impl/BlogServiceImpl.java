package com.srgstart.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.srgstart.blog.entity.Blog;
import com.srgstart.blog.entity.Type;
import com.srgstart.blog.entity.User;
import com.srgstart.blog.mapper.*;
import com.srgstart.blog.service.BlogService;
import com.srgstart.blog.util.HtmlToPlainText;
import com.srgstart.blog.util.MarkdownToHtml;
import com.srgstart.blog.vo.BlogVO;
import com.srgstart.blog.vo.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author srgstart
 * @Create 2021/8/15 19:53
 * @Description TODO
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    public static final String NULL_ = "null";

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private BlogAndTagMapper blogAndTagMapper;

    @Override
    public PageInfo<BlogVO> getAllBlog(Integer currentPage, Integer limit) {

        // 判断页码
        if (currentPage < 0) {
            currentPage = 0;
        }
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("is_deleted",0);
        Integer dataNum = blogMapper.selectCount(wrapper);
        int pageNum = (dataNum%limit == 0) ? dataNum/limit : dataNum/limit + 1;
        if (currentPage+1 > pageNum) {
            if (pageNum != 0) {
                currentPage = pageNum - 1;
            }
        }

        // 改变开始页面
        int begin = currentPage * limit;

        if (limit <= 0) {
            limit = 9;
        }
        // 查询数据并返回到页面
        List<Blog> blogList = blogMapper.allBlogList(begin, limit);
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
    }

    @Override
    public PageInfo<BlogVO> getPerBlog(Long userId, int currentPage, int limit, String searchTitle, String typeId, Boolean isRecommend) {

        if (currentPage < 0) {
            currentPage = 0;
        }

        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.eq("is_deleted",0);
        Integer dataNum = blogMapper.selectCount(wrapper);
        int pageNum = (dataNum%limit == 0) ? dataNum/limit : dataNum/limit + 1;
        if (currentPage+1  > pageNum) {
            if (pageNum != 0) {
                currentPage = pageNum - 1;
            }
        }

        // 改变开始页面
        int begin = currentPage * limit;

        if (limit <= 0) {
            limit = 4;
        }

        int recommendResult;
        if (isRecommend) {
            recommendResult = 1;
        } else {
            recommendResult = 0;
        }

        // 根据name查找id
        Long typeIdResult;
        if (NULL_.equals(typeId)) {
            typeIdResult = 0L;
        } else {
            typeIdResult = typeMapper.getTypeIdByName(typeId);
        }

        // 查询数据库
        List<Blog> blogList = blogMapper.personalBlogList(userId, begin, limit, searchTitle, typeIdResult, recommendResult);
        // 将查询到的blog转换为blogVO
        List<BlogVO> blogVOList = new ArrayList<>();
        for (Blog blog : blogList) {
            BlogVO blogVO = new BlogVO();
            BeanUtils.copyProperties(blog,blogVO);
            // 填充vo里面的type对象
            blogVO.setBlogType(typeMapper.getTypeById(blog.getTypeId()));

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
    }

    @Override
    public Blog getBlogById(Long id) {
        blogMapper.updateBlogViews(id);
        return blogMapper.findBlogById(id);
    }

    @Override
    public int updateBlog(Long id) {
        Blog blog = blogMapper.findBlogById(id);
        typeMapper.updateTypeBlogCount(blog.getTypeId());
        List<Long> tagIdList = blogAndTagMapper.findTagIdByBlogId(blog.getId());
        for (Long tagId : tagIdList) {
            tagMapper.updateTagBlogCount(tagId);
        }
        return blogMapper.updateBlog(id);
    }

    @Override
    public List<Blog> getBlogBySort(int maxBlogCount) {
        return blogMapper.getBlogBySort(maxBlogCount);
    }

    @Override
    public List<BlogVO> searchBlog(String searchKey) {
        List<Blog> blogList = blogMapper.searchBlog(searchKey);
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
        return blogVOList;
    }

    @Override
    public List<String> getArchiveYear() {
        return blogMapper.getArchiveYear();
    }

    @Override
    public List<BlogVO> getBlogListByYear(String year) {
        List<Blog> blogList = blogMapper.getBlogListByYear(year);
        List<BlogVO> blogVOList = new ArrayList<>();

        for (Blog blog : blogList) {
            BlogVO blogVO = new BlogVO();
            BeanUtils.copyProperties(blog,blogVO);
            User user = userMapper.findUserById(blog.getUserId());
            blogVO.setUser(user);
            blogVOList.add(blogVO);
        }

        return blogVOList;
    }

    @Override
    public int blogCount() {
        return blogMapper.blogCount();
    }

    @Override
    public List<Blog> getPerBlogBySort(int maxBlogCount, Long userId) {
        return blogMapper.getPerBlogBySort(maxBlogCount, userId);
    }

    @Override
    public int getBlogCountByYear(String year) {
        return blogMapper.getBlogCountByYear(year);
    }

    @Override
    public List<BlogVO> getBlogListVO(Integer page, Integer limit) {
        // 实现分页
        IPage<Blog> blogByPage = new Page<>(page,limit);
        IPage<Blog> blogByPage1 = blogMapper.selectPage(blogByPage, null);
        List<Blog> blogList = blogByPage1.getRecords();

        List<BlogVO> blogVOList = new ArrayList<>();
        for (Blog blog : blogList) {
            BlogVO blogVO = new BlogVO();
            BeanUtils.copyProperties(blog,blogVO);
            User user = userMapper.findUserById(blog.getUserId());
            blogVO.setUser(user);

            Type type = typeMapper.getTypeById(blog.getTypeId());
            blogVO.setBlogType(type);

            blogVOList.add(blogVO);
        }
        return blogVOList;
    }

    @Override
    public Long getIdByTiltle(String title) {
        return blogMapper.getIdByTiltle(title);
    }

    @Override
    public Map<String, String> findUserBlogInfo(Long userId) {
        Map<String, String> map = new HashMap<>();
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        Integer count = blogMapper.selectCount(wrapper);
        map.put("count",count.toString());
        List<Integer> views = blogMapper.getViewsByUserId(userId);
        Integer total = 0;
        for (Integer view : views) {
            total += view;
        }
        map.put("views",total.toString());
        return map;
    }
}
