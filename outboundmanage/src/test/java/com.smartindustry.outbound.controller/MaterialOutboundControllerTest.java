package com.smartindustry.outbound.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smartindustry.common.vo.ResultVO;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MaterialOutboundControllerTest extends BaseTest {
    @Test
    public void pageQuery() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/outbound/pageQuery")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNum", String.valueOf(1))
                .param("pageSize", String.valueOf(1))
                .param("otype", String.valueOf(1)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        assertNotNull(resultVO.getData());
    }

    @Test
    public void detail() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/outbound/detail")
                .contentType(MediaType.APPLICATION_JSON).param("oid", String.valueOf(2)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        assertNotNull(resultVO.getData());
        OutboundDetailVO outboundDetailVO = JSONObject.toJavaObject(JSON.parseObject(String.valueOf(resultVO.getData())), OutboundDetailVO.class);
        assertEquals("PO2020071200001", outboundDetailVO.getOno());

    }

    @Test
    public void outbound() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/outbound/outbound")
                    .contentType(MediaType.APPLICATION_JSON).param("oid", String.valueOf(1)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

        {
            /**
             * 异常情况1：OutboundPO 为 null 返回status：1002
             */
//            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/outbound/outbound")
//                    .contentType(MediaType.APPLICATION_JSON).param("oid", String.valueOf(4)))
//                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//
//            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
//            assertNotNull(resultVO.getStatus());
//            assertEquals(Integer.valueOf(1002), resultVO.getStatus());
        }
    }

    @Test
    public void record() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/outbound/detail")
                .contentType(MediaType.APPLICATION_JSON).param("oid", String.valueOf(2)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        assertNotNull(resultVO.getData());
    }

    @Test
    public void logEdit() throws Exception {

    }

    @Test
    public void upload() {

    }
}
