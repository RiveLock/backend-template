package com.template.response;

import lombok.Data;

import java.util.List;

/**
 * @Title: PageDataResult
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/12 17:18
 */
@Data
public class PageDataResult {
    private Integer code=200;

    //总记录数量
    private Integer totals;

    private List<?> list;
}
