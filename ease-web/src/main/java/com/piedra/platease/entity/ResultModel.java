package com.piedra.platease.entity;

import com.piedra.platease.constants.Constants;
import org.apache.commons.lang3.StringUtils;

/**
 * 返回结果
 * @author webinglin
 * @since 2017-04-06
 */
public class ResultModel {
    /** 返回的状态码 */
    private String code = Constants.WEB_SUCCESS;
    /** 返回的错误信息 */
    private String msg;

    public ResultModel setError(String msg){
        if(null == msg){
            msg = StringUtils.EMPTY;
        }
        this.code = Constants.WEB_FAIL;
        this.msg = msg;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
