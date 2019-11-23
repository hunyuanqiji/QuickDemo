package cn.plugin.core.bean;

import java.io.Serializable;

import cn.plugin.core.api.RetrofitCallBack;


/**
 * Created by NJQ on 2017/8/10.
 */

public class BaseTResultBean<T> implements Serializable{
    private int result;
    private T data;
    private String message;
    private boolean isdes;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public boolean isIsdes() {
        return isdes;
    }

    public void setIsdes(boolean isdes) {
        this.isdes = isdes;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 判断result == 200
     *
     */
    public boolean checkResultIs200(){
        return result == RetrofitCallBack.ResultCode.SUCCESS;
    }
}
