package com.smartindustry.storage.controller;

import com.alibaba.fastjson.JSON;
import com.smartindustry.common.bo.sm.ReceiptBO;
import com.smartindustry.common.mapper.sm.ReceiptBodyMapper;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.ReceiptDTO;
import com.smartindustry.storage.service.IReceiptManageService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static reactor.core.publisher.Mono.when;

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
        String reqData = "{\n" +
                "\t\"head\": {\n" +
                "\t\t\"ono\": \"PO2020020220200202\",\n" +
                "\t\t\"otype\": 1,\n" +
                "\t\t\"odate\": \"2020-07-01 00:00:00\",\n" +
                "\t\t\"supplier\": \"PO收单-东南院供应商\",\n" +
                "\t\t\"buyer\": \"轩辕先生\",\n" +
                "\t\t\"pdate\": \"2020-07-05 00:00:00\",\n" +
                "\t\t\"loco\": \"京东快递\",\n" +
                "\t\t\"lono\": \"JD00000000001\",\n" +
                "\t\t\"way\": 1,\n" +
                "\t\t\"remark\": \"这是一条测试数据，请不要操作此条数据.\"\n" +
                "\t},\n" +
                "\t\"body\":[{\n" +
                "\t\t\"mno\": \"5101000496\",\n" +
                "\t\t\"mname\": \"原料物料001\",\n" +
                "\t\t\"mtype\": 1,\n" +
                "\t\t\"mmodel\": \"SH0001\",\n" +
                "\t\t\"mdesc\": \"测试物料，原料类型.\",\n" +
                "\t\t\"ototal\": 1000,\n" +
                "\t\t\"anum\": 1000,\n" +
                "\t\t\"adate\": null\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"mno\": \"5101000497\",\n" +
                "\t\t\"mname\": \"半成品物料002\",\n" +
                "\t\t\"mtype\": 2,\n" +
                "\t\t\"mmodel\": \"SH0002\",\n" +
                "\t\t\"mdesc\": \"测试物料2，半成品类型.\",\n" +
                "\t\t\"ototal\": 2000,\n" +
                "\t\t\"anum\": 2000,\n" +
                "\t\t\"adate\": null\n" +
                "\t}]\n" +
                "}";

        reqData = reqData.replaceAll("\t|\n", "");


        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reqData)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        res = null;
    }

    @Test
    public void delete() {
    }

    @Test
    public void editLog() {
    }

    @Test
    public void record() {
    }
}