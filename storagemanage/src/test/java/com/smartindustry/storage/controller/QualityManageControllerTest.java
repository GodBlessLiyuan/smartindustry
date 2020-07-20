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
        }

        {
            /**
             * 异常情况：
             * 异常情况1：未传入rbid，返回status：1002
             */
            {
                //IqcTestDTO
                String reqData1 = "{" +
                        "\"rbid\": \"\"," +
                        "\"status\": 3" +
                        "}";

                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/quality/iqcTest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqData1)).andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1002), resultVO.getStatus());
            }

            /**
             * 异常情况2：receiptBodyPO.getStatus() != RECEIPT_IQC_DETECT  ||  IQC_ALLOW != iqcDetectPO.getStatus(), 返回 status：1003
             */
            {
                //IqcTestDTO
                String reqData2 = "{" +
                        "\"rbid\": 12," +
                        "\"status\": \"\"" +
                        "}";

                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/quality/iqcTest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqData2)).andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1003), resultVO.getStatus());
            }
        }
    }

    @Test
    public void qeConfirm() throws Exception {
        {
            //QeConfirmDTO
            String reqData = "{" +
                    "\"rbid\": 13," +
                    "\"status\": 3" +
                    "}";

            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeConfirm")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

        {
            /**
             * 异常情况：
             * 异常情况1：未传入rbid，返回 status：1002
             */
            {
                //QeConfirmDTO
                String reqData1 = "{" +
                        "\"rbid\": \"\"," +
                        "\"status\": 3" +
                        "}";

                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeConfirm")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData1))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1002), resultVO.getStatus());
            }

            /**
             * 异常情况2：receiptBodyPO.getStatus() != RECEIPT_QE_DETECT || qeDetectPO.getStatus() == RECEIPT_QE_DETECT ;返回 status ： 1003
             */
            {
                //QeConfirmDTO
                String reqData2 = "{" +
                        "\"rbid\": 8," +
                        "\"status\": 3" +
                        "}";

                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeConfirm")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData2))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1003), resultVO.getStatus());
            }

            /**
             * 异常情况3：输入参数有误，返回status：1001
             */
            {
                //QeConfirmDTO
                String reqData3 = "{" +
                        "\"rbid\": 13," +
                        "\"status\": 1" +
                        "}";

                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeConfirm")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData3))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1001), resultVO.getStatus());
            }

        }
    }

    @Test
    public void qeTest() throws Exception {
        {
            //QeTestDTO
            String reqData = "{" +
                    "\"rbid\": 12," +
                    "\"status\": 3," +
                    "\"remark\": \"\"" +
                    "}";

            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeTest")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

        {
            /**
             * 异常情况：
             * 异常情况1：未传入rbid，返回status：1002
             */
            {
                //QeTestDTO
                String reqData1 = "{" +
                        "\"rbid\": \"\"," +
                        "\"status\": 3," +
                        "\"remark\": \"\"" +
                        "}";

                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeTest")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData1))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1002), resultVO.getStatus());
            }

            /**
             * 异常情况2：receiptBodyPO.getStatus() != RECEIPT_QE_DETECT || qeDetectPO.getStatus() == QE_WAIT，返回status：1003
             */
            {
                //QeTestDTO
                String reqData2 = "{" +
                        "\"rbid\": 8," +
                        "\"status\": 3," +
                        "\"remark\": \"\"" +
                        "}";

                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeTest")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData2))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1003), resultVO.getStatus());
            }

            /**
             * 异常情况3：输入参数有误，返回status：1001
             */
            {
                //QeTestDTO
                String reqData3 = "{" +
                        "\"rbid\": 12," +
                        "\"status\": 1," +
                        "\"remark\": \"\"" +
                        "}";

                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeTest")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData3))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1001), resultVO.getStatus());
            }
        }
    }

    @Test
    public void storage() throws Exception {
        {
            //传入数据 Long rbid
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/quality/storage")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(3)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

        /**
         * 异常情况：
         * 异常情况1：输入 rbid：11，其中 iqcDetectPO.getStatus() == IQC_ALLOW -- 3;返回 status: 1003
         */
        {
            //传入数据 Long rbid
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/quality/storage")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(11)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1003), resultVO.getStatus());
        }
        /**
         * 异常情况2：未输入rbid， 返回status：1002
         */
        {
            //传入数据 Long rbid
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/quality/storage")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", ""))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
    }

    @Test
    public void record() throws Exception {
        {
            //传入rbId 和 status
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/quality/record")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("rbid", String.valueOf(1L)).param("status", String.valueOf(1L)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
    }

    @Test
    public void iqcTestProcessing() throws Exception {
        {
            /**
             * iqc检测子流程1：iqctest “允许：1” ->  生产入库 "形成入库单"
             */
            {
                //Iqc检测
                String reqData = "{" +
                        "\"rbid\": 20," +
                        "\"status\": 1," +
                        "\"gnum\": 500," +
                        "\"bnum\": 500" +
                        "}";

                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/quality/iqcTest")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1000), resultVO.getStatus());

                //生产入库 "形成入库单"
                MvcResult res1 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/storage")
                        .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(20)))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO1 = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO1.getStatus());
                assertEquals(Integer.valueOf(1000), resultVO1.getStatus());

            }

            /**
             * iqc检测子流程2：iqctest “非良品”-> qeconfirm -> 特采: 是 特采数量：400 -> 入库
             */
            {
                //IqcTest-Iqc检测
                String reqData2 = "{" +
                        "\"rbid\": 21," +
                        "\"status\": 2," +
                        "\"gnum\": 500," +
                        "\"bnum\": 500" +
                        "}";

                MvcResult res2 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/iqcTest")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData2))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO2 = JSONObject.toJavaObject(JSON.parseObject(res2.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO2.getStatus());
                assertEquals(Integer.valueOf(1000), resultVO2.getStatus());

                //QeConfirm-Qe确认-特采: 特采数量400
                String reqData3 = "{" +
                        "\"rbid\": 21," +
                        "\"status\": 4," +
                        "\"num\": 400" +
                        "}";

                MvcResult res3 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeConfirm")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData3))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ReceiptVO> resultVO3 = JSONObject.toJavaObject(JSON.parseObject(res3.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO3.getStatus());
                assertEquals(Integer.valueOf(1000), resultVO3.getStatus());

                //生产入库 "形成入库单"
                MvcResult res4 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/storage")
                        .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(21)))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO4 = JSONObject.toJavaObject(JSON.parseObject(res4.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO4.getStatus());
                assertEquals(Integer.valueOf(1000), resultVO4.getStatus());
            }

            /**
             * iqc检测子流程3：iqctest “非良品” -> qeconfirm -> 特采:否 驳回 -> iqc重验
             */
            {
                //Iqc检测
                String reqData5 = "{" +
                        "\"rbid\": 22," +
                        "\"status\": 2" +
                        "}";

                MvcResult res5 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/iqcTest")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData5))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO5 = JSONObject.toJavaObject(JSON.parseObject(res5.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO5.getStatus());
                assertEquals(Integer.valueOf(1000), resultVO5.getStatus());

                //Qe确认 -- 驳回
                String reqData6 = "{" +
                        "\"rbid\": 22," +
                        "\"status\": 2" +
                        "}";
                MvcResult res6 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeConfirm")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData6))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO6 = JSONObject.toJavaObject(JSON.parseObject(res6.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO6.getStatus());
                assertEquals(Integer.valueOf(1000), resultVO6.getStatus());

                //Iqc重验
                String reqData7 = "{" +
                        "\"rbid\": 22," +
                        "\"status\": 2," +
                        "\"gnum\": 0," +
                        "\"bnum\": 1000" +
                        "}";
                //IqcTest-Iqc检测
                MvcResult res7 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/iqcTest")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData7))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO7 = JSONObject.toJavaObject(JSON.parseObject(res7.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO7.getStatus());
                assertEquals(Integer.valueOf(1000), resultVO7.getStatus());
            }

            /**
             * iqc检测子流程4： iqctest “非良品”-> qeconfirm -> 特采: 否 退回供应商
             */
            {
                //Iqc检测
                String reqData8 = "{" +
                        "\"rbid\": 23," +
                        "\"status\": 2" +
                        "}";

                MvcResult res8 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/iqcTest")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData8))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO8 = JSONObject.toJavaObject(JSON.parseObject(res8.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO8.getStatus());
                assertEquals(Integer.valueOf(1000), resultVO8.getStatus());

                //Qe确认-- 退回供应商
                String reqData9 = "{" +
                        "\"rbid\": 23," +
                        "\"status\": 5" +
                        "}";
                MvcResult res9 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeConfirm")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData9))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO9 = JSONObject.toJavaObject(JSON.parseObject(res9.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO9.getStatus());
                assertEquals(Integer.valueOf(1000), resultVO9.getStatus());


            }
        }
    }

    @Test
    public void qeTestProcessing() throws Exception {
        /**
         * QE检测子流程1：qetest “允许：1” -> 形成入库单
         */
        {
            //qeTest -- 允许
            String data = "{" +
                    "\"rbid\": 24," +
                    "\"status\": 1," +
                    "\"gnum\": 1000," +
                    "\"bnum\": 0," +
                    "\"remark\": \"\"" +
                    "}";

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeTest")
                    .contentType(MediaType.APPLICATION_JSON).content(data))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultvo = JSONObject.toJavaObject(JSON.parseObject(result.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultvo.getStatus());

            //形成入库单
            MvcResult result1 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/storage")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(24)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultvo1 = JSONObject.toJavaObject(JSON.parseObject(result1.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultvo1.getStatus());

        }


        /**
         * QE检测子流程2：qetest “特采：4 ” -> 形成入库单
         */
        {
            //qeTest -- 特采
            String data2 = "{" +
                    "\"rbid\": 25," +
                    "\"status\": 4," +
                    "\"gnum\": 800," +
                    "\"bnum\": 0," +
                    "\"remark\": \"\"" +
                    "}";

            MvcResult result2 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeTest")
                    .contentType(MediaType.APPLICATION_JSON).content(data2))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultvo2 = JSONObject.toJavaObject(JSON.parseObject(result2.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultvo2.getStatus());

            //形成入库单
            MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/storage")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(25)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultvo3 = JSONObject.toJavaObject(JSON.parseObject(result3.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultvo3.getStatus());
        }

        /**
         * QE检测子流程5：qetest “退回供应商：5 ” -> 形成入库单
         */
        {
            //qeTest -- 特采
            String data3 = "{" +
                    "\"rbid\": 26," +
                    "\"status\": 5," +
                    "\"gnum\": 800," +
                    "\"bnum\": 0," +
                    "\"remark\": \"\"" +
                    "}";

            MvcResult result3 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeTest")
                    .contentType(MediaType.APPLICATION_JSON).content(data3))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultvo3 = JSONObject.toJavaObject(JSON.parseObject(result3.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultvo3.getStatus());

        }
    }

}
