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
public class ErpExternalControllerTest extends BaseTest {

    @Test
    public void pick() throws Exception {
        String reqData = "{" +
                "\"head\": {" +
                "\"pno\": \"GDJH2020052200004\"," +
                "\"ono\": \"PO2020071200002\"," +
                "" +
                "\"otype\": 1," +
                "\"cproject\": \"ES7035W\"," +
                "\"ptime\": \"2019-12-07 00:00:00\"" +
                "}," +
                "\"body\":[{" +
                "\"mno\": \"5101000496\"," +
                "\"dnum\": 400" +
                "}]" +
                "}";
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/erp/pick")
                .contentType(MediaType.APPLICATION_JSON).content(reqData))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
    }
}
