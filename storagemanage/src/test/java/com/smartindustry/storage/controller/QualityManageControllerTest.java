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
public class QualityManageControllerTest extends BaseTest {

    @Test

    public void pageQuery() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/quality/pageQuery")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("pageNum", String.valueOf(1))
                    .param("pageSize", String.valueOf(10))
                    .param("status", "5")
            ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());

        }
    }

    @Test
    public void iqcTest() throws Exception {
        {
            //IqcTestDTO
            String reqData = "{" +
                    "\"rbid\": 11," +
                    "\"status\": 3" +
                    "}";

            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/quality/iqcTest")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(reqData))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            //

        }
    }

    @Test
    public void qeConfirm() throws Exception {
//        {
//            //QeConfirmDTO
//            String reqData = "{" +
//                    "\"rbid\": 13," +
//                    "\"status\": 3" +
//                    "}";
//
//            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeConfirm")
//                    .contentType(MediaType.APPLICATION_JSON).content(reqData))
//                    .andDo(MockMvcResultHandlers.print())
//                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//
//            ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
//            assertNotNull(resultVO.getStatus());
//            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
//        }
    }

    @Test
    public void qeTest() throws Exception {
//        {
//            //QeTestDTO
//            String reqData = "{" +
//                    "\"rbid\": 12," +
//                    "\"status\": 3," +
//                    "\"remark\": \"\"" +
//                    "}";
//
//            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeTest")
//                    .contentType(MediaType.APPLICATION_JSON).content(reqData))
//                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//
//            ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
//            assertNotNull(resultVO.getStatus());
//            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
//
//        }
    }

    @Test
    public void storage() throws Exception {
//        {
//            //传入数据 Long rbid
//            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/quality/storage")
//                    .contentType(MediaType.APPLICATION_JSON).param("rbid",String.valueOf(4)))
//                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//
//            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
//            assertNotNull(resultVO.getStatus());
//            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
//        }
    }

    @Test
    public void record() throws Exception {
        {
            //传入rbId 和 status
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/quality/record")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("rbid", String.valueOf(6L)).param("status", String.valueOf(1L)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());

        }
    }
}
