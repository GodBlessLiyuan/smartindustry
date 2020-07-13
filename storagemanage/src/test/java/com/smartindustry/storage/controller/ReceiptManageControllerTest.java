package com.smartindustry.storage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.vo.ReceiptVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author: velve
 * @date: Created in 2020/7/9 12:35
 * @description: TODO
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ReceiptManageControllerTest extends BaseTest {

    @Test
    public void pageQuery() {
    }

    @Test
    public void insert() throws Exception {

        String reqData = "{" +
                "\"head\": {" +
                "\"ono\": \"PO2020020220200202\",\n" +
                "\"otype\": 1,\n" +
                "\"odate\": \"2020-07-01 00:00:00\"," +
                "\"supplier\": \"PO收单-东南院供应商\"," +
                "\"buyer\": \"轩辕先生\"," +
                "\"pdate\": \"2020-07-05 00:00:00\"," +
                "\"loco\": \"京东快递\"," +
                "\"lono\": \"JD00000000001\"," +
                "\"way\": 1," +
                "\"remark\": \"这是一条测试数据，请不要操作此条数据.\"" +
                "}," +
                "\"body\":[{\n" +
                "\"mno\": \"5101000496\"," +
                "\"mname\": \"原料物料001\"," +
                "\"mtype\": 1," +
                "\"mmodel\": \"SH0001\"," +
                "\"mdesc\": \"测试物料，原料类型.\"," +
                "\"ototal\": 1000," +
                "\"anum\": 1000," +
                "\"adate\": null" +
                "}," +
                "{" +
                "\"mno\": \"5101000497\",\n" +
                "\"mname\": \"半成品物料002\",\n" +
                "\"mtype\": 2," +
                "\"mmodel\": \"SH0002\"," +
                "\"mdesc\": \"测试物料2，半成品类型.\"," +
                "\"ototal\": 2000," +
                "\"anum\": 2000," +
                "\"adate\": null" +
                "}]" +
                "}";


        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqData)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
    }

    @Test
    public void delete()  {


    }

    @Test
    public void editLog() throws Exception {
        String reqData = "{" +
                "\"rbid\": 1," +
                "\"company\": \"顺丰快递\"," +
                "\"no\": \"SF0000000001\"," +
                "\"way\": 2," +
                "\"remark\": \"由东京快递改为顺丰快递\"" +
                "}";

        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/editLog")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqData)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertEquals(Integer.valueOf(1000),resultVO.getStatus());
    }

    @Test
    public void record() {
    }

}
