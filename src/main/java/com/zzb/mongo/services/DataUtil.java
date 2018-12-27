package com.zzb.mongo.services;

import com.zzb.client.ExampleClient;
import com.zzb.mongo.dao.TokenInfoDao;
import com.zzb.mongo.entity.TokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author Ron
 * @date : 2018/12/26 下午1:22
 * @description Copyright (C) 2018 TL.Crop, All Rights Reserved.
 */
@Component
public class DataUtil {

    @Value("${thread.count:50}")
    private int threadCount;

    @Value("${thread.pageIndex:1}")
    private int pageIndex;

    @Value("${test.ip:192.168.88.35}")
    private String ip;

    @Value("${test.liveId:24321165}")
    private String liveId;


    @Autowired
    TokenInfoDao tokenInfoDao;

    public void loop(){
        final int[] count = {0};
        //List<TokenInfo> list = tokenInfoDao.findByCondition(new TokenInfo());
        List<TokenInfo> list = tokenInfoDao.pageQueryManyCriteria(new Query(), TokenInfo.class, threadCount, pageIndex).getDataList();

        System.out.println("ip: " + ip + ", threadCount:" + list.size());

        for (TokenInfo tokenInfo: list) {

            System.out.println("正在加入第"+ count[0] +"个用户");

            if(count[0]++ > threadCount){
                return;
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String wsUrl = "ws://"+ ip +":15247/websocket/"+ liveId +"&"+ tokenInfo.getUserId() +"&2/" + tokenInfo.getToken();
            ExampleClient client = null;
            try {
                client = new ExampleClient(new URI(wsUrl), tokenInfo.getUserId(), liveId);
                client.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        //list.stream().forEach(tokenInfoConsumer->{
        //
        //    if(count[0]++ > 10){
        //        return;
        //    }
        //
        //    String wsUrl = "ws://192.168.88.35:15247/websocket/24321165&"+ tokenInfoConsumer.getUserId() +"&2/" + tokenInfoConsumer.getToken();
        //    ExampleClient client = null;
        //    try {
        //        client = new ExampleClient(new URI(wsUrl), tokenInfoConsumer.getUserId());
        //        client.connect();
        //    } catch (URISyntaxException e) {
        //        e.printStackTrace();
        //    }
        //});
    }
}
