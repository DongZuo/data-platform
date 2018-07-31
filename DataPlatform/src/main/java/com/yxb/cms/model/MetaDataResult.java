package com.yxb.cms.model;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import java.util.Map;

/**
 * @author yangxin_ryan
 */
public class MetaDataResult {

    private int code;

    private String msg;

    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static String generateJson(Integer code, String msg, String data) {
        Map<String, Object> result = Maps.newHashMap();
        result.put("code", code);
        result.put("msg", msg);
        result.put("data", data);
        return JSONObject.toJSONString(result);
    }
}
