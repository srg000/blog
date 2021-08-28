package com.srgstart.blog.controller;

import com.srgstart.blog.entity.Tag;
import com.srgstart.blog.service.TagService;
import com.srgstart.blog.vo.BlogVO;
import com.srgstart.blog.vo.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/23 20:13
 * @Description TODO
 */
@Controller
@RequestMapping("/tag")
public class TagController {
    public static final String NO_LOGIN_ = "no_permission/";

    @Autowired
    private TagService tagService;

    @GetMapping("/goTagsPage/{tagId}/{currentPage}/{limit}")
    public String goTagsPage(@PathVariable Long tagId,
                             @PathVariable int currentPage,
                             @PathVariable int limit,
                             Model model) {
        // 查询所有的标签集合
        List<Tag> tagList = tagService.findAllTags();
        model.addAttribute("tagList",tagList);

        // 分页查询指定标签下的博客列表
        if (tagId == -1) {
            tagId = tagList.get(0).getId();
        }
        PageInfo<BlogVO> blogList = tagService.getBlogListByTagId(tagId, currentPage, limit);
        model.addAttribute("blogList",blogList);

        // 返回查询的分类id
        model.addAttribute("activeTagId",tagId);

        return NO_LOGIN_ + "tags";
    }
}
