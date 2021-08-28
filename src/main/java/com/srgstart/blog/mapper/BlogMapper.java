package com.srgstart.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.srgstart.blog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/15 19:52
 * @Description 博客的DAO层，使用了mybatis-plus
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

    /**
     * 根据blogId查询博客信息
     * @param blogId：博客Id
     * @return 查询到的博客信息，也可能为null
     */
    Blog findBlogById(@Param("blogId") Long blogId);

    /**
     * 首页排序查询所有有人的博客
     * @param currentPage : 当前页
     * @param limit : 每页数据
     * @return 查询到的博客信息，也可能为null
     */
    List<Blog> allBlogList(@Param("currentPage") int currentPage,
                           @Param("limit") int limit);


    /**
     * 显示个人的博客列表
     * @param userId : 用户Id
     * @param currentPage : 当前页
     * @param limit : 每页数据
     * @return 查询到的博客信息，也可能为null
     */
    List<Blog> personalBlogList(@Param("userId") Long userId,
                                @Param("currentPage") int currentPage,
                                @Param("limit") int limit,
                                @Param("searchTitle") String searchTitle,
                                @Param("typeId") Long typeId,
                                @Param("recommendResult") int recommendResult);

    /**
     * 根据博客id修改博客状态，达到逻辑删除的效果
     * @param id 博客id
     * @return 是否修改成功
     */
    int updateBlog(@Param("id") Long id);

    /**
     * 返回指定数量，根据访问量排好序的博客列表
     * @param maxBlogCount 指定的数量
     * @return 博客列表
     */
    List<Blog> getBlogBySort(@Param("maxBlogCount") int maxBlogCount);

    /**
     * 全局查询博客
     * @param searchKey 关键字
     * @return 查询到的博客集合
     */
    List<Blog> searchBlog(@Param("searchKey") String searchKey);

    /**
     * 博客浏览量自增
     * @param blogId 博客id
     * @return 是否自增成功
     */
    int updateBlogViews(@Param("blogId") Long blogId);

    /**
     * 根据分类id查询所有的博客
     * @param typeId 分类id
     * @param currentPage 当前页
     * @param limit 每页数量
     * @return 返回的博客集合
     */
    List<Blog> getBlogListByTypeId(@Param("typeId") Long typeId,
                                   @Param("currentPage") int currentPage,
                                   @Param("limit") int limit);

    List<Blog> getBlogListByIds(@Param("ids") List<Long> ids,
                                @Param("currentPage") int currentPage,
                                @Param("limit") int limit);

    /**
     * 获取归档年份
     * @return 年份集合
     */
    List<String> getArchiveYear();

    /**
     * 根据归档年份查询对应的博客信息
     * @param year 归档年份
     * @return 博客集合
     */
    List<Blog> getBlogListByYear(@Param("year") String year);

    /**
     * 根据归档年份查询对应的博客数量
     * @param year 归档年份
     * @return 博客数量
     */
    int getBlogCountByYear(@Param("year") String year);

    /**
     * 查询所有的博客数量
     * @return 博客数量
     */
    int blogCount();

    /**
     * 返回指定数量，根据访问量排好序的博客列表
     * @param maxBlogCount 指定的数量
     * @param userId userId
     * @return 博客列表
     */
    List<Blog> getPerBlogBySort(@Param("maxBlogCount") int maxBlogCount,
                                @Param("userId") Long userId);

    Long getIdByTiltle(@Param("title") String title);


    List<Integer> getViewsByUserId(@Param("userId") Long userId);
}
