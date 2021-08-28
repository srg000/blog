package com.srgstart.blog.controller;

import com.srgstart.blog.entity.User;
import com.srgstart.blog.service.FileService;
import com.srgstart.blog.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author srgstart
 * @Create 2021/8/17 11:03
 * @Description TODO
 */
@RestController
@RequestMapping("/api/oss")
public class FileApiController {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    /**
     * 上传Editor里面的图片文件到阿里云oss
     * @param file : 上传的文件
     * @return ：文件的路径
     */
    @PostMapping("/fileUpload")
    public String fileUploadForEditor(@RequestParam(value = "editormd-image-file",required = false) MultipartFile file) {
        //获取上传文件(通过MultipartFile 获取)

        //上传文件
        return fileService.upload(file);
    }

    /**
     * 上传文件到阿里云oss
     * @param file : 上传的文件
     * @return ：文件的路径
     */
    @PostMapping("/fileUpload/file")
    public Map<String, String> fileUpload(MultipartFile file) {
        //获取上传文件(通过MultipartFile 获取)

        //上传文件
        String imgUrl = fileService.upload(file);
        // 由于layui类似于 ajax 所以我们要处理回调的数据为json格式
        Map<String, String> imgUrlMap = new HashMap<>(5);
        imgUrlMap.put("imgUrl",imgUrl);
        return imgUrlMap;
    }

    private Map<String, String> upload(MultipartFile file) {
        //获取上传文件(通过MultipartFile 获取)

        //上传文件
        String imgUrl = fileService.upload(file);
        // 由于layui类似于 ajax 所以我们要处理回调的数据为json格式
        Map<String, String> imgUrlMap = new HashMap<>(5);
        imgUrlMap.put("imgUrl",imgUrl);
        return imgUrlMap;
    }

    @PostMapping("/fileUpload/userFile")
    public Map<String, String> uploadUserAvatar(MultipartFile file, HttpSession session) {
        Map<String, String> imgUrlMap = upload(file);
        User user = (User) session.getAttribute("loginUser");
        userService.updateAvatarUrlById(user.getId(),imgUrlMap.get("imgUrl"));
        return imgUrlMap;
    }
}




