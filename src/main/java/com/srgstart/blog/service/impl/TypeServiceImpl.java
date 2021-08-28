package com.srgstart.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.srgstart.blog.entity.Blog;
import com.srgstart.blog.entity.Type;
import com.srgstart.blog.mapper.BlogMapper;
import com.srgstart.blog.mapper.TypeMapper;
import com.srgstart.blog.mapper.UserMapper;
import com.srgstart.blog.service.TypeService;
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
 * @Create 2021/8/16 11:36
 * @Description TODO
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<Type> getTypeList() {
        return typeMapper.getTypeList();
    }


    @Override
    public Type getTypeById(Long id) {
        // 参数校验
        if (id == null) {
            return new Type();
        }

        return typeMapper.getTypeById(id);
    }

    @Override
    public List<Type> getTypeBySort(int maxCount) {
        // 获取所有的分类id
        List<Long> idList = typeMapper.getAllTypeIds();
        // 遍历每一个分类id，并查询每个分类下对应的博客数
        for (Long typeId : idList) {
            typeMapper.updateTypeCount(typeId);
        }
        // 返回根据博客数排好序的分类集合。
        return typeMapper.getTypeBySort(maxCount);
    }

    @Override
    public PageInfo<BlogVO> getBlogListByTypeId(Long typeId, int currentPage, int limit) {
        // 判断页码
        if (currentPage < 0) {
            currentPage = 0;
        }
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();
        wrapper.eq("is_deleted",0);
        wrapper.eq("type_id",typeId);
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
            limit = 5;
        }
        // 查询数据并返回到页面
        List<Blog> blogList = blogMapper.getBlogListByTypeId(typeId,begin, limit);
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
}
