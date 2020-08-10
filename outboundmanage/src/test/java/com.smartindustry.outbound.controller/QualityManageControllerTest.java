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
public class QualityManageControllerTest extends BaseTest {

    @Test
    public void pickOqcButton() throws Exception {
//        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/quality/pickOqcButton")
//                .contentType(MediaType.APPLICATION_JSON).param("phid", ""))
//                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//
//        ResultVO resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
//        assertNotNull(resultVO.getStatus());
//        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
    }

    @Test
    public void queryOqc() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/quality/queryOqc")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNum", "1")
                .param("pageSize", "10000")
                .param("pno", "")
                .param("ono", ""))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
    }
}
