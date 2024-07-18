package com.example.springboot.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

/**
 * @author cmsxyz@163.com
 * @date 2024-07-18 0:25
 * @usage
 */
@Data
public class Old {

    private int id;
    private String code;
    private String path;
}
