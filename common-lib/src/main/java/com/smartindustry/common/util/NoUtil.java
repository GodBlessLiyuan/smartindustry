package com.smartindustry.common.util;

import com.smartindustry.common.mapper.BaseMapper;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: xiahui
 * @date: Created in 2020/6/23 19:25
 * @description: 编号工具
 * @version: 1.0
 */
public class NoUtil<Model, PK extends Serializable> {

    /**
     * 查询当前最新编号数据
     *
     * @param mapper
     * @param head
     * @param date
     * @return
     */
    public Model getPO(BaseMapper<Model, PK> mapper, String head, Date date) {
        return mapper.queryNo((StringUtils.isEmpty(head) ? "" : head) + DateUtil.date2Str(date, DateUtil.YMD));
    }

    /**
     * 获取当前编号顺序
     *
     * @param num
     * @param len
     * @return
     */
    public int getNum(String num, int len) {
        return Integer.parseInt(num.substring(num.length() - len - 1, num.length() - 1));
    }

    /**
     * 生成编号
     *
     * @param head
     * @param date
     * @param len
     * @param num
     * @return
     */
    public String genNum(String head, Date date, int num, int len) {
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isEmpty(head)) {
            sb.append(head);
        }
        sb.append(DateUtil.date2Str(date, DateUtil.YMD));
        for (int i = String.valueOf(num).length(); i < len; i++) {
            sb.append("0");
        }
        sb.append(num);
        return sb.toString();
    }
}
