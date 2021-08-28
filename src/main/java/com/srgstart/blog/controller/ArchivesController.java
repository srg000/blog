package com.srgstart.blog.controller;

import com.srgstart.blog.entity.Blog;
import com.srgstart.blog.service.BlogService;
import com.srgstart.blog.vo.ArchiveVO;
import com.srgstart.blog.vo.ArchivesVO;
import com.srgstart.blog.vo.BlogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/23 21:58
 * @Description TODO
 */
@Controller
@RequestMapping("/archives")
public class ArchivesController {
    public static final String NO_LOGIN_ = "no_permission/";

    @Autowired
    private BlogService blogService;


//    @GetMapping("/goArchivesPage")
//    public String goArchivesPage(Model model) {
//        // 查询所有的归档年份
//        List<String> archiveYear = blogService.getArchiveYear();
//        // 根据归档年份查询对应的博客集合
//        List<ArchiveVO> archiveVOList = new ArrayList<>();
//        for (String year : archiveYear) {
//            ArchiveVO archiveVO = new ArchiveVO();
//            archiveVO.setYear(year);
//            List<Blog> blogList = blogService.getBlogListByYear(year);
//            archiveVO.setBlogList(blogList);
//            archiveVOList.add(archiveVO);
//        }
//
//        model.addAttribute("archiveList",archiveVOList);
//        model.addAttribute("blogTotalCount",blogService.blogCount());
//
//        return NO_LOGIN_ + "archives";
//    }

    @GetMapping("/archivesPage")
    public String archivesPage(Model model) {
        // 查询所有的归档年份
        List<String> archiveYear = blogService.getArchiveYear();
        // 获得最新一年
        String theNewYear = archiveYear.get(0);
        // 根据最新一年查询当前的博客信息
        List<BlogVO> blogVOList = blogService.getBlogListByYear(theNewYear);

        List<ArchivesVO> archives = new ArrayList<>();
        for (String year : archiveYear) {
            ArchivesVO archivesVO = new ArchivesVO();
            int count = blogService.getBlogCountByYear(year);
            archivesVO.setBlogCount(count);
            archivesVO.setYear(year);
            archives.add(archivesVO);
        }

        model.addAttribute("year",archiveYear.get(0));
        model.addAttribute("blogList",blogVOList);
        model.addAttribute("yearList",archives);
        return NO_LOGIN_ + "archive";
    }

    /**
     * 根据归档年份查询对应的博客集合
     * @param year 要查询的年份
     * @return 归档页面
     */
    @GetMapping("/getBlogListByYear/{year}")
    public String getBlogListByYear(@PathVariable String year, Model model) {
        List<BlogVO> blogList = blogService.getBlogListByYear(year);
        model.addAttribute("blogList",blogList);
        model.addAttribute("year",year);
        return NO_LOGIN_ + "archive :: archiveList";
    }
}
