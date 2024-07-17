package com.example.springboot.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.springboot.bean.Old;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author cmsxyz@163.com
 * @date 2024-07-18 0:31
 * @usage
 */
@Repository
public interface OldMapper extends BaseMapper<Old> {
    Map<String,Object> selectCmsById(Long id);

}
