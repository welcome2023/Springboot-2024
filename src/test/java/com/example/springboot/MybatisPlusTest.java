package com.example.springboot;

import com.example.springboot.bean.Old;
import com.example.springboot.mapper.OldMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sun.reflect.generics.tree.VoidDescriptor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cmsxyz@163.com
 * @date 2024-07-18 1:00
 * @usage
 */
@SpringBootTest
public class MybatisPlusTest {

    @Autowired
    private OldMapper oldMapper;

    @Test
    public void testSelectList() {
        List<Old> list = oldMapper.selectList(null);
        list.forEach(System.out::println);
    }
    @Test
    public void testInsert(){
        Old old = new Old();
        old.setId(7);
        old.setCode("20202");
        old.setPath("/a/b/c");
        oldMapper.insert(old);
    }


    @Test
    public void testDeleteSingle(){
        oldMapper.deleteById(4);
    }


    @Test
    public void testDeleteMapSingle(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("path","/a/b/c");
        oldMapper.deleteByMap(map);
    }

    @Test
    public void testDeleteList() {
        List<Long> list = Arrays.asList(5L, 7L);
        oldMapper.deleteBatchIds(list);
    }

    @Test
    public void testUpdate(){
        Old old = new Old();
        old.setId(6);
        old.setPath("/d/e");
        oldMapper.updateById(old);
    }

    @Test
    public void testSelect(){
//        List<Long> list = Arrays.asList(1L, 6L);
//        List<Old> oldList = oldMapper.selectBatchIds(list);
//        oldList.forEach(System.out::println);
        Map<String, Object> map = oldMapper.selectCmsById(6L);
        System.out.println(map);
    }


}
