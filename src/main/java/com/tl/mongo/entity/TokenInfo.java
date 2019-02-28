package com.tl.mongo.entity;

import com.tl.mongo.base.QueryField;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @program: matrix-tl
 * @description: token info
 * @author: Ron
 * @create: 2018-09-20 15:09
 * Copyright (C) 2018 TL.Crop, All Rights Reserved.
 */
@Document(collection = "TokenInfo")
public class TokenInfo {

    @Id
    @QueryField
    private String token;

    @QueryField
    private String userId;

    public TokenInfo(String token) {
        this.token = token;
    }

    public TokenInfo(String token, String userId) {
        this.token = token;
        this.userId = userId;
    }

    public TokenInfo() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
