package com.xjb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bo
 * @create 2021-04-16 16:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    private Long id;    //标签id
    private String name;    //标签名

    private List<Blog> blogs = new ArrayList<>();   //该标签对应的所有博客
}
