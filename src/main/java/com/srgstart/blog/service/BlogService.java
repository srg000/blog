package com.srgstart.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.srgstart.blog.entity.Blog;
import com.srgstart.blog.vo.BlogVO;
import com.srgstart.blog.vo.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.Nullable;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author srgstart
 * @Create 2021/8/15 19:53
 * @Description TODO
 */
public interface BlogService extends IService<Blog> {

    PageInfo<BlogVO> getAllBlog(Integer currentPage,
                          Integer limit);

    PageInfo<BlogVO> getPerBlog(Long userId,
                                int currentPage,
                                int limit,
                                @Nullable String searchTitle,
                                @Nullable String typeId,
                                @Nullable Boolean isRecommend);

    Blog getBlogById(Long id);

    /**
     * 根据博客id修改博客状态，达到逻辑删除的效果
     * @param id 博客id
     * @return 是否修改成功
     */
    int updateBlog(Long id);

    /**
     * 返回指定数量，根据访问量排好序的博客列表
     * @param maxBlogCount 指定的数量
     * @return 博客列表
     */
    List<Blog> getBlogBySort(int maxBlogCount);

    List<BlogVO> searchBlog(String searchKey);

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
    List<BlogVO> getBlogListByYear(@Param("year") String year);

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
    List<Blog> getPerBlogBySort(int maxBlogCount, Long userId);

    /**
     * 根据归档年份查询对应的博客数量
     * @param year 归档年份
     * @return 博客数量
     */
    int getBlogCountByYear( String year);

    List<BlogVO> getBlogListVO(Integer page, Integer limit);

    Long getIdByTiltle( String title);

    /**
     * 根据用户id查询用户博客信息
     * @param userId 用户id
     * @return map信息
     */
    Map<String, String> findUserBlogInfo(Long userId);
}
