package com.smartindustry.outbound.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smartindustry.common.bo.om.OutboundBO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.vo.OutboundDetailVO;
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
    public void pageQuery() {

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
        assertEquals("PO2020071200001",outboundDetailVO.getOno());

    }

    @Test
    public void outbound() {

    }

    @Test
    public void record() {

    }

    @Test
    public void logEdit() {

    }

    @Test
    public void upload() {

    }
}
