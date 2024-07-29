package com.example.springboot;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.bean.AllInfo;
import com.example.springboot.bean.Map;
import com.example.springboot.bean.New;
import com.example.springboot.bean.Old;
import com.example.springboot.mapper.AllInfoMapper;
import com.example.springboot.mapper.MapMapper;
import com.example.springboot.mapper.NewMapper;
import com.example.springboot.mapper.OldMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author cmsxyz@163.com
 * @date 2024-07-28 23:39
 * @usage
 */
@SpringBootTest
public class BrushData {
    @Autowired
    private OldMapper oldMapper;
    @Autowired
    private NewMapper newMapper;
    @Autowired
    private MapMapper mapMapper;
    @Autowired
    private AllInfoMapper allInfoMapper;


    @Test
    public void brushData() {
        // 1.循环读取old表
        List<Old> olds = oldMapper.selectList(null);
        List<Map> maps = mapMapper.selectList(null);

        // 2.与map表做匹配
        long startTime = System.currentTimeMillis();
        for (Old old : olds) {
            AllInfo allInfo = getAllInfo(old);
            if (allInfo.getId() != null) {
                New aNew1 = BeanUtil.copyProperties(old, New.class);
                aNew1.setId(allInfo.getId());
                aNew1.setCode(allInfo.getCode());
                aNew1.setPath(allInfo.getPath());
                newMapper.insert(aNew1);
            } else {
                // 3.如果匹配上写入new表,没有则打印
                System.out.println("old.getId() = " + old.getId());
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("程序执行时长为: " + (endTime - startTime));

    }

    private AllInfo getAllInfo(Old old) {
        QueryWrapper<Map> mapQueryWrapper = new QueryWrapper<>();
        mapQueryWrapper.eq("old_id", old.getId());
        Map map = mapMapper.selectOne(mapQueryWrapper);
        if (map == null) {
            return new AllInfo();
        } else {
            QueryWrapper<AllInfo> allInfoQueryWrapper = new QueryWrapper<>();
            allInfoQueryWrapper.eq("id", map.getNewId());
            return allInfoMapper.selectOne(allInfoQueryWrapper);
        }
    }


}
