package com.srgstart.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.srgstart.blog.entity.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author srgstart
 * @Create 2021/8/16 11:29
 * @Description TODO
 */
@Mapper
public interface TypeMapper extends BaseMapper<Type> {

    /**
     * 获取所有的分类
     * @return ：分类集合
     */
    List<Type> getTypeList();

    /**
     * 根据类型id查询
     * @param id 类型id
     * @return 查询到的类型
     */
    Type getTypeById(@Param("id") Long id);

    Long getTypeIdByName(@Param("name") String name);

    /**
     * 根据分类id修改分类下博客的数量
     * @param typeId 博客id
     * @return 返回是否修改成功
     */
    int updateTypeCount(@Param("typeId") Long typeId);

    /**
     *获取指定数量的分类列表（通过排序）
     * @param  maxCount 指定的数量
     * @return 分类集合
     */
    List<Type> getTypeBySort(@Param("maxCount") int maxCount);

    /**
     * 获取所有的分类的id
     * @return id集合
     */
    List<Long> getAllTypeIds();


    /**
     * 当博客删除时，分类对应的博客数要自减一
     * @param typeId 博客id
     * @return 是否自增成功
     */
    int updateTypeBlogCount(@Param("typeId") Long typeId);
}
