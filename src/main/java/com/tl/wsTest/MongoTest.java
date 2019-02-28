package com.tl.wsTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Ron
 * @date : 2019/2/27 下午5:49
 * @description Copyright (C) 2018 TL.Crop, All Rights Reserved.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApp.class)
@Slf4j
public class MongoTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void getUserList() {

        //String tempName = "temp" + UUID.randomUUID().toString().hashCode();
        //List<TDocument> allResult = mongoTemplate.find(new Query(), DBObject.class, tempName);
        //mongoTemplate.getCollection(tempName).insertMany(allResult);

        for (int i = 0; i < 3; i++) {
            //mongoTemplate.createCollection("test111111" + i);
            mongoTemplate.executeCommand("{create:\"sfe"+ i +"\", autoIndexId:true}");
        }
    }


    public void insert(){
        String classStr = "{'classId':'1','Students':[{'studentId':'1','name':'zhangsan'}]}";
        JSONObject parseObject = JSON.parseObject(classStr);
        mongoTemplate.insert(parseObject,"class");
    }
}
