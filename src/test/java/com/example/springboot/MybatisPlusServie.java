package com.example.springboot;

import com.example.springboot.service.OldService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author cmsxyz@163.com
 * @date 2024-07-18 2:49
 * @usage
 */
@SpringBootTest
public class MybatisPlusServie {
    @Autowired
    private OldService oldService;

    @Test
    public void countNum(){
        System.out.println(oldService.count());
//        oldService.
    }
}
