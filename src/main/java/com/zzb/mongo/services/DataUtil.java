package com.zzb.mongo.services;

import com.zzb.client.ExampleClient;
import com.zzb.mongo.dao.TokenInfoDao;
import com.zzb.mongo.entity.TokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    TokenInfoDao tokenInfoDao;

    public void loop(){
        final int[] count = {0};
        List<TokenInfo> list = tokenInfoDao.findByCondition(new TokenInfo());

        for (TokenInfo tokenInfo: list) {
            //if(count[0]++ > 3){
            //    return;
            //}

            String wsUrl = "ws://192.168.88.35:15247/websocket/24321165&"+ tokenInfo.getUserId() +"&2/" + tokenInfo.getToken();
            ExampleClient client = null;
            try {
                client = new ExampleClient(new URI(wsUrl), tokenInfo.getUserId());
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
