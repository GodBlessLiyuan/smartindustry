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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    public void pageQuery() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/pageQuery")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("pageNum", String.valueOf(1))
                    .param("pageSize", String.valueOf(2))
                    .param("keyword", "")
                    .param("supplier", "")
                    .param("ono", "")
                    .param("status", "")
                    .param("type", "")
            ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

    }

    @Test
    public void insert() throws Exception {
        {
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
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            ReceiptVO receiptVO = JSONObject.toJavaObject(JSON.parseObject(String.valueOf(resultVO.getData())), ReceiptVO.class);
            assertEquals("PO2020020220200202", receiptVO.getHead().getOno());
            //收货方式：
            assertEquals(Byte.valueOf(String.valueOf(1)), receiptVO.getHead().getWay());
            //采购员：
            assertEquals("轩辕先生", receiptVO.getHead().getBuyer());
            //物料类型：1
            assertEquals(Byte.valueOf(String.valueOf(1)), receiptVO.getBody().get(0).getMtype());
            //物料类型：2
            assertEquals(Byte.valueOf(String.valueOf(2)), receiptVO.getBody().get(1).getMtype());
            //订单总数
            assertEquals(Integer.valueOf(1000), receiptVO.getBody().get(0).getAnum());
        }
    }

    @Test
    public void delete() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/delete")
                    .contentType(MediaType.APPLICATION_JSON).param("rbids[]", String.valueOf(2)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
    }


    @Test
    public void editLog() throws Exception {
        {
            String reqData = "{" +
                    "\"rbid\": 9," +
                    "\"company\": \"京东快递\"," +
                    "\"no\": \"JD00000000001\"," +
                    "\"way\": 1," +
                    "\"remark\": \"这是一条测试数据，请不要操作此条数据.\"" +
                    "}";

            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/editLog")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(reqData)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
    }

    @Test
    public void record() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/record")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("rbid", String.valueOf(6)).param("status", String.valueOf(1))).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
        }
    }

}
