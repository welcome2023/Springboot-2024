package com.example.springboot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.bean.Map;
import org.springframework.stereotype.Repository;

/**
 * @author cmsxyz@163.com
 * @date 2024-07-18 0:31
 * @usage BaseMapper<Map> 泛型指定的就是当前mapper接口所操作的实体类类型
 */
@Repository
public interface MapMapper extends BaseMapper<Map> {

}
