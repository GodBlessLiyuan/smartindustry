package com.smartindustry.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 10:57
 * @description: 返回前端统一封装结构
 * @version: 1.0
 */
@Data
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer status;

    private T data;

    public ResultVO(Integer status) {
        this(status, null);
    }

    public ResultVO(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    /**
     * 构造成功执行的构造参数
     * @return
     */
    public static ResultVO ok(){
        return new ResultVO(1000);
    }

    public ResultVO setData(T data){
        this.data = data;
        return this;
    }

    public ResultVO setPageInfoVO(PageInfoVO page){
        if(page == null){
            return this;
        }
        this.setData((T) page);
        return this;
    }

}
