package com.bonc.token.domain;

/**
 * 解析结果
 * @author lidefu
 * @date 2018/12/11 12:13
 */
public class AnalysisResult<T> {

    /**
     * 是否成功
     */
    private boolean isSuccess;

    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 错误信息
     */
    private String errorMsg;

    /**
     * 解析结果
     */
    private T result;

    @Override
    public String toString(){
        return "{" +
                "isSuccess=" + this.isSuccess +
                ",errorCode=" + this.errorMsg +
                ",errorMsg=" + this.errorMsg +
                ",result=" + this.result +
                "}";
    }

    public static<T> AnalysisResult ok(T t){
        AnalysisResult result = new AnalysisResult();
        result.setSuccess(true);
        result.setResult(t);
        return result;
    }

    public static AnalysisResult fail(int errorCode, String msg){
        AnalysisResult result = new AnalysisResult();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMsg(msg);
        return result;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
