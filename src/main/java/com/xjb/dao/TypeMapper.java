package com.xjb.dao;


import com.xjb.pojo.Type;
import com.xjb.vo.TypeIndex;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Bo
 * @create 2021-04-17 16:33
 */
@Mapper
@Repository
public interface TypeMapper {

    /**
     * 插入新的分类
     * @param type
     * @return
     */
    int saveType(Type type);

    /**
     * 根据type中的id更新分类
     * @param
     * @param type
     * @return
     */
    @Update("update t_type set `name`= #{name} where `id` = #{id}")
    int updateType(Type type);

    /**
     * 查询所有分类
     * @return
     */
    @Select("select * from t_type")
    List<Type> getAllType();

    /**
     * 根据id删除分类
     * @param id
     */
    @Delete("delete from t_type where id = #{id}")
    int deleteType(Long id);

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    @Select("select * from t_type where id = #{id}")
    Type getTypeId(Long id);

    /**
     * 根据名字查询分类
     * @param name
     * @return
     */
    @Select("select * from t_type where name = #{name}")
    Type getTypeName(String name);

    /**
     * 查询各分类所对应的已发布博客数量，并按博客数量倒叙排序返回typeCount条数据
     * (分类对应的博客为空的分类不返回)
     * @return
     */
    List<TypeIndex> getTypeDescIndex(Integer typeCount);

}
