package com.xjb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bo
 * @create 2021-04-16 16:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type {

    private long id;    //分类id

    //用@NotBlank注解标识name不能为空
    @NotBlank(message = "分类的名称不能为空")
    private String name;    //分类名

    private List<Blog> blogs = new ArrayList<>();   //该分类下的所有博客

}
