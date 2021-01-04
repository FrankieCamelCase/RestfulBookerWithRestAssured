package com.restfulbooker.api.utils;

import com.restfulbooker.api.pojo.TokenBody;
import groovyjarjarantlr.Token;

public class AuthUtil {
    public static TokenBody getTokenBodyPojo(){

        TokenBody tokenBody = new TokenBody();

        tokenBody.setUsername(ConfigurationReader.getProperty("username"));
        tokenBody.setPassword(ConfigurationReader.getProperty("password"));

        return tokenBody;
    }
}
