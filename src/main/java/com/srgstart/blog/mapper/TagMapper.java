package com.srgstart.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.srgstart.blog.entity.Tag;
import com.srgstart.blog.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/16 20:54
 * @Description TODO
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 获取所有的标签
     * @return 返回标签集合
     */
    List<Tag> findAllTags();

    /**
     * 根据标签id修改标签下博客的数量
     * @param tagId 标签id
     * @return 返回是否修改成功
     */
    int updateTagCount(@Param("tagId") Long tagId);

    /**
     *获取指定数量的标签列表（通过排序）
     * @param  maxTagCount 指定的数量
     * @return 标签集合
     */
    List<Tag> getTagBySort(@Param("maxTagCount") int maxTagCount);

    /**
     * 获取所有的标签的id
     * @return id集合
     */
    List<Long> getAllTagIds();


    /**
     * 当博客删除时，标签对应的博客数要自减一
     * @param tagId 标签id
     * @return 是否自增成功
     */
    int updateTagBlogCount(@Param("tagId") Long tagId);

}
