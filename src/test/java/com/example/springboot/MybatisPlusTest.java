package com.example.springboot;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.bean.New;
import com.example.springboot.bean.Old;
import com.example.springboot.mapper.MapMapper;
import com.example.springboot.mapper.NewMapper;
import com.example.springboot.mapper.OldMapper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.ibatis.annotations.Mapper;
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
    @Autowired
    private NewMapper newMapper;
    @Autowired
    private MapMapper mapMapper;

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
    @Test
    public void update(){
        QueryWrapper<Old> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("code","1")
                .and(i->i.eq("id",1).or().eq("id",6));
        Old old = new Old();
        old.setPath("666");
        oldMapper.update(old,queryWrapper);

    }
    @Test
    public void test1(){
        QueryWrapper<Old> query = new QueryWrapper<>();
        query.le("id",2).select("id","code");

        List<Map<String, Object>> maps = oldMapper.selectMaps(query);
        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }

    @Test
    public void test2(){
        QueryWrapper<Old> oldQueryWrapper = new QueryWrapper<>();
        oldQueryWrapper.eq("id",1);
        Old old = oldMapper.selectOne(oldQueryWrapper);

        QueryWrapper<New> newQueryWrapper = new QueryWrapper<>();
        newQueryWrapper.eq("id",old.getId());
        New newa = newMapper.selectOne(newQueryWrapper);
        System.out.println(newa);

    }

    @Test
    public void test3(){
        int pageNum=1,pageSize=2;
        Page<com.example.springboot.bean.Map> page = new Page<>(pageNum,pageSize);
        IPage<com.example.springboot.bean.Map> list = mapMapper.selectPage(page, null);
        long amount = list.getPages();
        for (long i = 0; i < amount; i++) {
            for (com.example.springboot.bean.Map map : list.getRecords()) {
                UpdateWrapper<Old> oldUpdateWrapper = new UpdateWrapper<>();
                oldUpdateWrapper.eq("id",map.getOldId());
                oldUpdateWrapper.set("id",map.getNewId());
                oldMapper.update(null,oldUpdateWrapper);
            }
            page=new Page<>(++pageNum,pageSize);
            list=mapMapper.selectPage(page,Wrappers.emptyWrapper());
            System.out.println("i: "+i);
        }


    }


}
