package com.srgstart.blog.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.srgstart.blog.entity.Blog;
import com.srgstart.blog.entity.BlogAndTag;
import com.srgstart.blog.entity.Type;
import com.srgstart.blog.entity.User;
import com.srgstart.blog.service.*;
import com.srgstart.blog.util.HtmlToPlainText;
import com.srgstart.blog.util.MarkdownToHtml;
import com.srgstart.blog.vo.BlogVO;
import com.srgstart.blog.vo.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author srgstart
 * @Create 2021/8/15 19:51
 * @Description 博客管理controller
 */
@Controller
@RequestMapping("/blog")
@Slf4j
public class BlogController {
    public static final String HAVE_LOGIN_ = "have_permission/";
    public static final String NO_LOGIN_ = "no_permission/";
    public static final int MAX_COUNT = 6;
    public static final int MAX_TAG_COUNT = 9;
    public static final int MAX_BLOG_COUNT = 5;

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogAndTagService blogAndTagService;

    /**
     * 博客首页获取所有人的博客
     * 需要向首页传递：所有人的博客【分页显示】、分类列表【根据对应博客数量排序展示6条】
     * 标签列表【根据对应的博客数量排序展示9条】、热文排行【根据博客浏览量进行排序5条】
     * @param currentPage：当前页
     * @param limit：每页显示条数
     * @param model：携带数据
     * @return ：跳转的页面
     */
    @GetMapping("/getAllBlog/{currentPage}/{limit}")
    public String getAllBlog(@PathVariable Integer currentPage,
                                 @PathVariable Integer limit,
                                 Model model) {
        // 分类列表【根据对应博客数量排序展示6条】
        model.addAttribute("typeList", typeService.getTypeBySort(MAX_COUNT));

        // 标签列表【根据对应的博客数量排序展示9条】
        model.addAttribute("tagList",tagService.getTagBySort(MAX_TAG_COUNT));

        // 热文排行【根据博客浏览量进行排序5条】
        model.addAttribute("rankList",blogService.getBlogBySort(MAX_BLOG_COUNT));

        // 调用service业务方法返回分页对象
        PageInfo<BlogVO> allBlogList = blogService.getAllBlog(currentPage, limit);
        model.addAttribute("allBlogList", allBlogList);
        return NO_LOGIN_ + "index";
    }

    /**
     * 个人博客列表
     * @param userId：当前登录人的Id
     * @param currentPage ： 当前页
     * @param limit ： 每页显示条数
     * @param searchTitle ： 搜索博客标题
     * @param typeId ：搜索博客分类
     * @param isRecommend ：是否推荐
     * @param model：携带数据
     * @return ： 跳转的页面
     */
    @GetMapping("/getPerBlog/{currentPage}/{limit}/{userId}/{searchTitle}/{typeId}/{isRecommend}")
    public String getPerBlog(@PathVariable Long userId,
                                 @PathVariable int currentPage,
                                 @PathVariable int limit,
                                 @PathVariable @Nullable String searchTitle,
                                 @PathVariable @Nullable String typeId,
                                 @PathVariable @Nullable Boolean isRecommend,
                                 Model model) {

        // 调用service业务方法，查询分页信息，并封装成对象
        PageInfo<BlogVO> perBlogList = blogService.getPerBlog(userId, currentPage, limit, searchTitle, typeId,isRecommend);
        // 查询所有的分类
        List<Type> typeList = typeService.getTypeList();

        // 向页面里携带数据
        model.addAttribute("perBlogList", perBlogList);
        model.addAttribute("typeList",typeList);
        return  HAVE_LOGIN_ +"blogs";
    }

    /**
     * 去发布博客页面
     * @param model 向页面携带数据
     * @return 跳转的页面
     */
    @GetMapping("/goEditBlogPage")
    public String goEditBlogPage(Model model) {
        // 获取全部的分类
        model.addAttribute("typeList",typeService.getTypeList());
        // 获取全部的标签
        model.addAttribute("tagList",tagService.findAllTags());
        return HAVE_LOGIN_ + "blog_input";
    }


    /**
     * 保存或者修改上传的博客
     * @param blogVO 博客表单数据
     * @param attributes 重定向携带数据到页面
     * @param session 获取用户id
     * @return 发布完跳转到博客列表页面
     */
    @PostMapping("/saveBlog")
    public String saveBlog(BlogVO blogVO, RedirectAttributes attributes, HttpSession session) {
        // 参数校验
        if (blogVO != null) {

            // 添加blog信息
            Blog blog = new Blog();
            BeanUtils.copyProperties(blogVO,blog);
            // 向blog添加blogvo没有的数据
            User user = (User) session.getAttribute("loginUser");
            blog.setUserId(user.getId());
            // 如果blogVo.getId 为空，说明时插入操作
            if (blogVO.getId() == null) {
                boolean save = blogService.save(blog);
                if (save) {
                    Long id = blogService.getIdByTiltle(blog.getTitle());
                    // 保存数据
                    // 使用hutool包将string转换为list
                    String tagIds = blogVO.getTagIds();
                    if (StringUtils.isNotBlank(tagIds)) {
                        Long[] tagIdList = Convert.toLongArray(tagIds);
                        BlogAndTag blogAndTag = new BlogAndTag();
                        // 一篇博客对应多个标签，把数据放入关联表中
                        for (Long tagId : tagIdList) {
                            blogAndTag.setBlogId(id);
                            blogAndTag.setTagId(tagId);
                            // 如果blogVo.getId 为空，说明时插入操作
                            if (blogVO.getId() == null) {
                                blogAndTagService.save(blogAndTag);
                            } else {
                                // 如果blogVo.getId 不为空，说明时更新操作

                                blogAndTagService.updateById(blogAndTag);
                            }
                        }
                    }
                    attributes.addFlashAttribute("message","恭喜，操作成功！");
                    return "redirect:/blog/getPerBlog/0/4/"+ user.getId() +"/null/null/false";
                } else {
                    attributes.addFlashAttribute("message","新增博客失败！");
                    return "redirect:/blog/goEditBlogPage";
                }
            } else {
                // 如果blogVo.getId 不为空，说明时更新操作
                boolean flag = blogService.updateById(blog);
                if (flag) {
                    attributes.addFlashAttribute("message","恭喜，修改成功！");
                    return "redirect:/blog/getPerBlog/0/4/"+ user.getId() +"/null/null/false";
                } else {
                    attributes.addFlashAttribute("message","修改博客失败！");
                    return "redirect:/goUpdateBlogPage/" + blogVO.getId();
                }
            }
        } else {
            attributes.addFlashAttribute("message","新增博客失败！");
            return "redirect:/blog/goEditBlogPage";
        }
    }

    /**
     * 前往博客修改页面
     * @param model 向页面携带数据
     * @return 跳转的页面
     */
    @GetMapping("/goUpdateBlogPage/{id}")
    public String goUpdateBlogPage(@PathVariable("id") Long id, Model model) {
        // 获取全部的分类
        model.addAttribute("typeList",typeService.getTypeList());
        // 获取全部的标签
        model.addAttribute("tagList",tagService.findAllTags());
        // 查询出指定博客
        BlogVO blogVO = new BlogVO();
        Blog blog = blogService.getBlogById(id);
        BeanUtils.copyProperties(blog,blogVO);
        List<Long> tagIdList = blogAndTagService.findTagIdByBlogId(blog.getId());
        String tagIds = StrUtil.removeAll(Convert.toStr(tagIdList),'[',']');
        System.out.println("======" + tagIds);

        blogVO.setTagIds(tagIds);

        model.addAttribute("blogDetail", blogVO);
        return HAVE_LOGIN_ + "blog_update";
    }

    /**
     * 根据博客id删除对应的博客
     * @param id : 要删除的博客id
     * @param attributes 重定向携带数据
     * @return 指定的页面
     */
    @GetMapping("/deleteBlogById/{id}")
    public String deleteBlogById(@PathVariable("id") Long id, RedirectAttributes attributes) {
        int flag = blogService.updateBlog(id);
        if (flag > 0) {
            attributes.addFlashAttribute("message","恭喜，删除成功！");
        } else {
            attributes.addFlashAttribute("message_fail","删除博客失败！");
        }
        return "redirect:/blog/getPerBlog/0/4/1/null/null/false";
    }


    @PostMapping("/searchBlog")
    public String searchBlog(String query,Model model) {
        model.addAttribute("blogList",blogService.searchBlog(query));
        return NO_LOGIN_ + "search";
    }





}
