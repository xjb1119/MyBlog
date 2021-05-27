package com.xjb.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bo
 * @create 2021-04-19 19:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogSearch {

    private String title;
    private Long TypeId;
    private boolean recommend;
}
