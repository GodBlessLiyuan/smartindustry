package com.smartindustry.outbound.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smartindustry.common.vo.ResultVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ProcessingControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void processingTest1() throws Exception {

        /**
         * 1. 从erp获取工单信息 "/erp/pick"  demand:200
         * 2. 扫描拣货 "/pick/pickPidOut"   200
         * 3. 根据当前工单拣货单id 生成 出库单 "/pick/outBoundItems"  "/pick/agreeOutBound"
         * 4. 完成出库 "/outbound/outbound"  --> mstatus:35
         */
        {
            /**
             * 1. 从erp获取工单信息 "/erp/pick"
             */
            String reqData1 = "{" +
                    "\"head\": {" +
                    "\"pno\": \"GDJH2020052200009\"," +
                    "\"ono\": \"PO2020071200002\"," +
                    "" +
                    "\"otype\": 1," +
                    "\"cproject\": \"ES7035W\"," +
                    "\"ptime\": \"2019-12-07 00:00:00\"" +
                    "}," +
                    "\"body\":[{" +
                    "\"mno\": \"5101000496\"," +
                    "\"dnum\": 200" +
                    "}]" +
                    "}";

            MvcResult res1 = mockMvc.perform(MockMvcRequestBuilders.get("/erp/pick")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData1))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO1 = JSONObject.toJavaObject(JSON.parseObject(res1.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO1.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO1.getStatus());

            /**
             * 2. 扫描拣货 "/pick/pickPidOut"
             */
            MvcResult res2 = mockMvc.perform(MockMvcRequestBuilders.post("/pick/pickPidOut")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("phid", String.valueOf(10))
                    .param("pid", "2020072200006"))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO2 = JSONObject.toJavaObject(JSON.parseObject(res2.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO2.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO2.getStatus());

            /**
             * 3. 根据当前工单拣货单id 生成 出库单 "/pick/outBoundItems"  "/pick/agreeOutBound"
             */
            MvcResult res3 = mockMvc.perform(MockMvcRequestBuilders.post("/pick/outBoundItems")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("phid", String.valueOf(10)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO3 = JSONObject.toJavaObject(JSON.parseObject(res3.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO3.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO3.getStatus());

            MvcResult res31 = mockMvc.perform(MockMvcRequestBuilders.post("/pick/agreeOutBound")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("phid", String.valueOf(10)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO31 = JSONObject.toJavaObject(JSON.parseObject(res31.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO31.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO31.getStatus());

            /**
             * 4. 完成出库 "/outbound/outbound"
             */
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/outbound/outbound")
                    .contentType(MediaType.APPLICATION_JSON).param("oid", String.valueOf(3)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

    }

    @Test
    public void processingTest2() throws Exception {
        /**
         * 1. 从erp获取工单信息 "/erp/pick"  demand:500
         * 2. 扫描拣货 "/pick/pickPidOut"   400
         * 3. 工单审核  -- 取消发货，退回仓库
         */
        {
            /**
             * 1. 从erp获取工单信息 "/erp/pick"
             */
            String reqData1 = "{" +
                    "\"head\": {" +
                    "\"pno\": \"GDJH2020052200010\"," +
                    "\"ono\": \"PO2020071200002\"," +
                    "" +
                    "\"otype\": 1," +
                    "\"cproject\": \"ES7035W\"," +
                    "\"ptime\": \"2019-12-07 00:00:00\"" +
                    "}," +
                    "\"body\":[{" +
                    "\"mno\": \"5101000496\"," +
                    "\"dnum\": 500" +
                    "}]" +
                    "}";

            MvcResult res1 = mockMvc.perform(MockMvcRequestBuilders.get("/erp/pick")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData1))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO1 = JSONObject.toJavaObject(JSON.parseObject(res1.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO1.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO1.getStatus());

            /**
             * 2. 扫描拣货 "/pick/pickPidOut"
             */
            MvcResult res2 = mockMvc.perform(MockMvcRequestBuilders.post("/pick/pickPidOut")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("phid", String.valueOf(11))
                    .param("pid", "2020072400004"))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO2 = JSONObject.toJavaObject(JSON.parseObject(res2.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO2.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO2.getStatus());

            /**
             * 3. 根据当前工单拣货单id 生成 出库单 "/pick/outBoundItems"
             */
            MvcResult res3 = mockMvc.perform(MockMvcRequestBuilders.post("/pick/outBoundItems")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("phid", String.valueOf(11)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO3 = JSONObject.toJavaObject(JSON.parseObject(res3.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO3.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO3.getStatus());

            /**
             * 4. 根据当前工单拣货单id 生成 出库单 "/pick/outBoundItems"
             */
            MvcResult res4 = mockMvc.perform(MockMvcRequestBuilders.post("/pick/disAgree")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("phid", String.valueOf(11))
                    .param("status", "25")
                    .param("msg", "驳回"))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO4 = JSONObject.toJavaObject(JSON.parseObject(res4.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO4.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO4.getStatus());
        }
    }

}
