package com.lts;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class SpringBootDataRedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testOpsForValue01() {
//不修改序列化配置
        redisTemplate.opsForValue().set("stone","pageee");
    }

    @Test
    void testOpsForValue02() {
//修改序列化配置

//        正常设置
        redisTemplate.opsForValue().set("stone1","pageee");
        System.out.println(redisTemplate.opsForValue().get("stone1"));

//        存活时间设置
        redisTemplate.opsForValue().set("stone2","pageee",10, TimeUnit.SECONDS);

//        判断是否存在设置
        Boolean aBoolean = redisTemplate.opsForValue().setIfAbsent("stone", "page");
        System.out.println(aBoolean);
    }

    @Test
    void testOpsForHash(){
        /**
         * 操作hash类型的数据
         */
        HashOperations hashOperations = redisTemplate.opsForHash();

        //存值
        hashOperations.put("003","name","xiaoli");
        hashOperations.put("003","age",20);
        hashOperations.put("003","addr","河北");

        //取值
        System.out.println(hashOperations.get("003", "name"));
        System.out.println(hashOperations.get("003","age"));
        System.out.println(hashOperations.get("003","addr"));

        //获取hash结构中所有字段
        System.out.println(hashOperations.keys("003"));
        //获取hash结构中所有值
        System.out.println(hashOperations.values("003"));
    }

    @Test
    void testOpsForList(){
        /**
         * 操作hash类型的数据
         */
        ListOperations listOperations = redisTemplate.opsForList();

        //存值
        listOperations.leftPush("mylist","a");
        listOperations.leftPushAll("mylist","b","c","d");

        //取值
        System.out.println(listOperations.range("mylist", 0, -1));

        //获取列表长度
        Long size = listOperations.size("mylist");
        int lSize = size.intValue();

        for (int i = 0; i < lSize; i++) {
            String mylist1 = (String) listOperations.rightPop("mylist");
            System.out.println(mylist1);
        }
    }

    @Test
    void testOpsForSet(){
        SetOperations setOperations = redisTemplate.opsForSet();

        setOperations.add("myset","a","b","b");

        System.out.println(setOperations.members("myset"));
    }
}
