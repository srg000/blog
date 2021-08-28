package com.srgstart.blog.controller;

import com.srgstart.blog.entity.Type;
import com.srgstart.blog.service.TypeService;
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
 * @Create 2021/8/16 12:54
 * @Description TODO
 */
@Controller
@RequestMapping("/type")
public class TypeController {
    public static final String NO_LOGIN_ = "no_permission/";

    @Autowired
    private TypeService typeService;

    /**
     * 前往博客分类页面
     * @param typeId 分类id
     * @param currentPage 当前页
     * @param limit 每页显示数量
     * @param model 携带数据到页面
     * @return types页面
     */
    @GetMapping("/goTypesPage/{typeId}/{currentPage}/{limit}")
    public String goTypesPage(@PathVariable Long typeId,
                              @PathVariable int currentPage,
                              @PathVariable int limit,
                              Model model) {
        // 查询所有的博客分类
        List<Type> typeList = typeService.getTypeList();
        model.addAttribute("typeList",typeList);

        // 分页查询指定分类的所有博客
        if (typeId == -1) {
            typeId = typeList.get(0).getId();
        }
        PageInfo<BlogVO> blogList = typeService.getBlogListByTypeId(typeId, currentPage, limit);
        model.addAttribute("blogList",blogList);

        // 返回查询的分类id
        model.addAttribute("activeTypeId",typeId);

        return NO_LOGIN_ +"types";
    }
}
