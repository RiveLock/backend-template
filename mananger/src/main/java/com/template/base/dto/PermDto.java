package com.template.base.dto;

import lombok.Data;

/**
 * @Title: PermDto
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/12/12 18:38
 */
@Data
public class PermDto {
    private Integer id;

    private String name;

    private Integer pid;

    private String pname;

    private String descpt;

    private String url;

    private String createTime;

    private String updateTime;

    private Integer delFlag;
}
