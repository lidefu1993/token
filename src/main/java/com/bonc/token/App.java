package com.bonc.token;

import com.bonc.token.domain.AnalysisResult;
import com.bonc.token.domain.UserInfo;
import com.bonc.token.helper.TokenHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import parsii.tokenizer.ParseException;

import java.io.IOException;

/**
 * @author lidefu
 * @date 2018/12/11 11:12
 */
public class App {

    public static void main(String[] args) throws IOException, ParseException {
        String s = generateTokenTest();
        AnalysisResult result = analysisTokenTest(s);
    }

    private static String security = "!@#$%^&*(SDFGHJK123456";


    private static String generateTokenTest() throws JsonProcessingException, ParseException {
        UserInfo userInfo = userInfo();
        String token = TokenHelper.generateToken(null, security, null, userInfo);
        System.out.println("token:" + token);
        return token;
    }

    private static AnalysisResult analysisTokenTest(String token) throws IOException {
        AnalysisResult result = TokenHelper.analysisToken(token, security, UserInfo.class);
        System.out.println("analysisResult:" + result.toString());
        return result;
    }

    private static UserInfo userInfo(){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(1111L);
        userInfo.setLoginId("admin");
        userInfo.setUserName("管理员");
        userInfo.setPassword("admin%123");
        userInfo.setRoleCode("role1");
        return userInfo;
    }

}
