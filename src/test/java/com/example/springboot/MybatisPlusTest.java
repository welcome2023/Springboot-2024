package com.example.springboot;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.bean.CategoryNotMapperBean;
import com.example.springboot.bean.New;
import com.example.springboot.bean.Old;
import com.example.springboot.mapper.MapMapper;
import com.example.springboot.mapper.NewMapper;
import com.example.springboot.mapper.OldMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.apache.ibatis.annotations.Mapper;
import org.assertj.core.data.MapEntry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sun.reflect.generics.tree.VoidDescriptor;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

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
    public void testInsert() {
        Old old = new Old();
        old.setId(7);
        old.setCode("20202");
        old.setPath("/a/b/c");
        oldMapper.insert(old);
    }


    @Test
    public void testDeleteSingle() {
        oldMapper.deleteById(4);
    }


    @Test
    public void testDeleteMapSingle() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("path", "/a/b/c");
        oldMapper.deleteByMap(map);
    }

    @Test
    public void testDeleteList() {
        List<Long> list = Arrays.asList(5L, 7L);
        oldMapper.deleteBatchIds(list);
    }

    @Test
    public void testUpdate() {
        Old old = new Old();
        old.setId(6);
        old.setPath("/d/e");
        oldMapper.updateById(old);
    }

    @Test
    public void testSelect() {
//        List<Long> list = Arrays.asList(1L, 6L);
//        List<Old> oldList = oldMapper.selectBatchIds(list);
//        oldList.forEach(System.out::println);
        Map<String, Object> map = oldMapper.selectCmsById(6L);
        System.out.println(map);
    }

    @Test
    public void update() {
        QueryWrapper<Old> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("code", "1")
                .and(i -> i.eq("id", 1).or().eq("id", 6));
        Old old = new Old();
        old.setPath("666");
        oldMapper.update(old, queryWrapper);

    }

    @Test
    public void test1() {
        QueryWrapper<Old> query = new QueryWrapper<>();
        query.le("id", 2).select("id", "code");

        List<Map<String, Object>> maps = oldMapper.selectMaps(query);
        for (Map<String, Object> map : maps) {
            System.out.println(map);
        }
    }

    @Test
    public void test2() {
        QueryWrapper<Old> oldQueryWrapper = new QueryWrapper<>();
        oldQueryWrapper.eq("id", 1);
        Old old = oldMapper.selectOne(oldQueryWrapper);

        QueryWrapper<New> newQueryWrapper = new QueryWrapper<>();
        newQueryWrapper.eq("id", old.getId());
        New newa = newMapper.selectOne(newQueryWrapper);
        System.out.println(newa);

    }

    @Test
    public void test3() {
        int pageNum = 1, pageSize = 2;
        Page<com.example.springboot.bean.Map> page = new Page<>(pageNum, pageSize);
        IPage<com.example.springboot.bean.Map> list = mapMapper.selectPage(page, null);
        long amount = list.getPages();
        for (long i = 0; i < amount; i++) {
            for (com.example.springboot.bean.Map map : list.getRecords()) {
                UpdateWrapper<Old> oldUpdateWrapper = new UpdateWrapper<>();
                oldUpdateWrapper.eq("id", map.getOldId());
                oldUpdateWrapper.set("id", map.getNewId());
                oldMapper.update(null, oldUpdateWrapper);
            }
            page = new Page<>(++pageNum, pageSize);
            list = mapMapper.selectPage(page, Wrappers.emptyWrapper());
            System.out.println("i: " + i);
        }


    }

    @Test
    public void test4() throws IOException, NoSuchFieldException {
        QueryWrapper<com.example.springboot.bean.Map> mapWrapper = new QueryWrapper<>();
        ObjectMapper fileMapper = new ObjectMapper();
        String filePath = "C:\\Users\\11509\\Desktop\\a.txt";
        FileWriter fileWriter = new FileWriter(filePath, true);
        QueryWrapper<Old> oldQueryWrapper = new QueryWrapper<>();
        for (com.example.springboot.bean.Map map : mapMapper.selectList(null)) {
            if (oldQueryWrapper.eq("id", map.getOldId()) != null) {
                UpdateWrapper<Old> oldUpdateWrapper = new UpdateWrapper<>();
                oldUpdateWrapper.eq("id", map.getOldId());
                oldUpdateWrapper.set("id", map.getNewId());
                oldMapper.update(null, oldUpdateWrapper);
            } else {
                CategoryNotMapperBean notMapperBean = new CategoryNotMapperBean();
                notMapperBean.setId(mapWrapper.getEntity().getOldId());
                notMapperBean.setCode(mapWrapper.getEntity().getOldCode());
                String jsonStr = fileMapper.writeValueAsString(notMapperBean);
                fileWriter.write(jsonStr);
                fileWriter.write("\n"); // 如果需要，可以在每条记录后添加换行符
            }

        }
    }

    @Test
    public void test5() throws IOException, NoSuchFieldException {
        List<Old> oldList = oldMapper.selectList(null);
        List<com.example.springboot.bean.Map> mapList = mapMapper.selectList(null);
        Set<Integer> oldIdSet = new HashSet<>();
        for (Old old : oldList) {
            oldIdSet.add(old.getId());
        }

        ObjectMapper fileMapper = new ObjectMapper();
        String filePath = "C:\\Users\\11509\\Desktop\\a.txt";
        try( FileWriter fileWriter = new FileWriter(filePath, true)){
            for (com.example.springboot.bean.Map map : mapList) {
                if (oldIdSet.contains(map.getOldId())) {
                    UpdateWrapper<Old> oldUpdateWrapper = new UpdateWrapper<>();
                    oldUpdateWrapper.eq("id", map.getOldId()).set("id", map.getNewId());
                    oldMapper.update(null, oldUpdateWrapper);
                } else {
                    CategoryNotMapperBean notMapperBean = new CategoryNotMapperBean();
                    notMapperBean.setId(map.getOldId());
                    notMapperBean.setCode(map.getNewCode());
                    notMapperBean.setPath("cmsSuccess!!!");
                    String jsonStr = fileMapper.writeValueAsString(notMapperBean);
                    System.out.println(jsonStr+"--->");
                    fileWriter.write(jsonStr);
                    fileWriter.write("\n");
                }
            }
        }catch (IOException e){
            System.out.println("程序异常!!!");
        }

    }
}
