package com.bonc.token.domain;

import io.jsonwebtoken.Claims;

/**
 * @author lidefu
 * @date 2018/12/11 11:45
 */
public class CheckResult {
    private int errCode;

    private boolean success;

    private Claims claims;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Claims getClaims() {
        return claims;
    }

    public void setClaims(Claims claims) {
        this.claims = claims;
    }

    public static CheckResult ok(Claims claims){
        CheckResult checkResult = new CheckResult();
        checkResult.setSuccess(true);
        checkResult.setClaims(claims);
        return checkResult;
    }

    public static CheckResult fail(int errorCode){
        CheckResult checkResult = new CheckResult();
        checkResult.setSuccess(false);
        checkResult.setErrCode(errorCode);
        return checkResult;
    }
}
