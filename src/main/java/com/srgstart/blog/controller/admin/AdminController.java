package com.srgstart.blog.controller.admin;

import com.srgstart.blog.service.BlogService;
import com.srgstart.blog.vo.BlogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/26 19:24
 * @Description TODO
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    public static final String ADMIN_ = "admin/";

    @Autowired
    private BlogService blogService;

    @GetMapping("/login")
    public String login() {
        return ADMIN_ + "index";
    }

    @GetMapping("/main")
    public String main() {
        return ADMIN_ + "main";
    }

    @GetMapping("/dataList")
    @ResponseBody
    public List<BlogVO> dataList(Integer page, Integer limit) {
        return blogService.getBlogListVO(page, limit);
    }

    @GetMapping("/dataList1")
    public String dataList() {
        return ADMIN_ + "dataList";
    }



}
