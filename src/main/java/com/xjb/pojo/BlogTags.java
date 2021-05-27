package com.xjb.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bo
 * @create 2021-04-23 10:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogTags {

    private Long blogId;
    private Long tagId;

}
