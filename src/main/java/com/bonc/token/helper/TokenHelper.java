package com.bonc.token.helper;

import com.bonc.token.domain.AnalysisResult;
import com.bonc.token.domain.CheckResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.apache.log4j.Logger;
import parsii.eval.Expression;
import parsii.eval.Parser;
import parsii.tokenizer.ParseException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author lidefu
 * @date 2018/12/11 11:13
 */
public class TokenHelper {

    private static Logger logger = Logger.getLogger(TokenHelper.class.getName());
    /**
     * Token过期
     */
    public static final int JWT_ERRCODE_EXPIRE = 4001;

    /**
     * 验证不通过
     */
    public static final int JWT_ERRCODE_FAIL = 4002;

    /**
     * 未知异常
     */
    public static final int UNKOWN_ERROR = 4003;


    /**
     * 生成token串
     * @param id 标识（非必填）该ID可用于防止JWT被重播
     * @param security 秘钥 (必填)
     * @param expireTime 过期时间 毫秒 (非必填)
     * @param t 内容 (必填)
     * @param <T>
     * @return
     */
    public static <T> String generateToken(String id, String security, Long expireTime, T t) throws ParseException, JsonProcessingException {
        checkSecurity(security);
        checkSubject(t);
        id = idInit(id);
        expireTime = expireTimeInit(expireTime);
        return generateToken(id, security, generateSubject(t), expireTime);
    }

    /**
     * 解析token串
     * @param token token
     * @param secret 秘钥
     * @param tClass 返回类型
     * @return
     */
    public static AnalysisResult  analysisToken(String token, String secret, Class tClass) throws IOException {
        CheckResult checkResult = validate(token, secret);
        if(checkResult.isSuccess()){
            Claims claims = checkResult.getClaims();
            ObjectMapper objectMapper = new ObjectMapper();
            return AnalysisResult.ok(objectMapper.readValue(claims.getSubject(), tClass));
        }else {
            int errorCode = checkResult.getErrCode();
            return AnalysisResult.fail(errorCode, errorMsg(errorCode));
        }
    }

    /**
     * 生成token
     * @param id
     * @param security
     * @param subject
     * @param ttlMillis
     * @return
     */
    private static String generateToken(String id, String security, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey(security);
        JwtBuilder builder = Jwts.builder().setId(id).setSubject(subject).setIssuedAt(now).signWith(signatureAlgorithm,
                secretKey);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date expDate = new Date(expMillis);
            builder.setExpiration(expDate);
        }
        return builder.compact();
    }

    /**
     * 验证token
     * @param token token串
     * @param secret 秘钥
     * @return
     */
    public static CheckResult validate(String token, String secret){
        try {
            Claims claims = parse(token, secret);
            return CheckResult.ok(claims);
        } catch (ExpiredJwtException e) {
            logger.error(e);
            return CheckResult.fail(JWT_ERRCODE_EXPIRE);
        } catch (SignatureException e) {
            logger.error(e);
            return CheckResult.fail(JWT_ERRCODE_FAIL);
        } catch (Exception e){
            logger.error(e);
            return CheckResult.fail(UNKOWN_ERROR);
        }
    }

    private static Claims parse(String token, String secret){
        SecretKey secretKey = generalKey(secret);
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    /**
     * 生成秘钥key
     * @param secret 秘钥
     * @return
     */
    private static SecretKey generalKey(String secret) {
        byte[] encodedKey = DatatypeConverter.parseBase64Binary(secret);
        SecretKey key = new SecretKeySpec(encodedKey, "AES");
        return key;
    }

    /**
     * 错误码对应的错误信息
     * @param errorCode
     * @return
     */
    private static String errorMsg(int errorCode){
        String errorMsg;
        switch (errorCode){
            case JWT_ERRCODE_EXPIRE: errorMsg = "签名过期,请重新登陆"; break;
            case JWT_ERRCODE_FAIL: errorMsg = "用户未登陆"; break;
            default:errorMsg = "未知的异常";
        }
        return errorMsg;
    }

    /**
     * toString
     * @param t
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    private static <T> String generateSubject(T t) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(t);
    }

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    private static boolean strIsEmpty(String str){
        return (str == null || str.isEmpty());
    }

    /**
     * 检验秘钥
     * @param security 秘钥
     * @return
     */
    private static void checkSecurity(String security){
        if(strIsEmpty(security)){
            throw new IllegalArgumentException("秘钥不能为空");
        }
    }

    /**
     * 检验内容体
     * @param o
     */
    private static void checkSubject(Object o){
        if(o == null){
            throw new IllegalArgumentException("内容不能为空");
        }
    }

    /**
     * id初始化
     * @param id
     * @return
     */
    private static String idInit(String id){
        if(strIsEmpty(id)){
            return uuid();
        }
        return id;
    }

    /**
     * 过期时间初始化 默认半个小时
     * @param expireTime
     * @return
     */
    private static Long expireTimeInit(Long expireTime){
        if(expireTime == null){
            return 3000000L;
        }
        return expireTime;
    }

    private static String uuid(){
        return UUID.randomUUID().toString();
    }
}
