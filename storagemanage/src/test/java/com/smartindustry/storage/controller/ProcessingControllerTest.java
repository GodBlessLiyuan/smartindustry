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
public class ProcessingControllerTest extends BaseTest {

    @Test
    public void overallProcess1() throws Exception {
        /**
         * 整体流程：
         * 1. 新增表单
         * 2. 标签录入
         * 3. 录入完成
         * 4. iqc检测  -- 允许
         * 5. 打印良品/非良品入库单
         * 6. "/storage/label"
         * 7. "/storage/save"
         * 8. 入库
         */

        {
            /**
             * 1. 新增表单
             */
            String reqData1 = "{" +
                    "\"head\": {" +
                    "\"ono\": \"PO202007210002\",\n" +
                    "\"otype\": 1,\n" +
                    "\"odate\": \"2020-07-21 00:00:00\"," +
                    "\"supplier\": \"PO收单-东南院供应商\"," +
                    "\"buyer\": \"李大嘴\"," +
                    "\"pdate\": \"2020-07-21 00:00:00\"," +
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

            MvcResult res1 = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/insert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(reqData1)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO1 = JSONObject.toJavaObject(JSON.parseObject(res1.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO1.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO1.getStatus());

            /**
             * 2. 录入标签
             */
            String reqData2 = "{" +
                    "\"rbid\": 35," +
                    "\"mno\": \"5101000496\"," +
                    "\"scode\": \"\"," +
                    "\"pdate\": \"20200721\"," +
                    "\"pbatch\": \"20200721\"," +
                    "\"mnum\": 500," +
                    "\"pnum\": 2" +
                    "}";
            MvcResult res2 = mockMvc.perform(MockMvcRequestBuilders.post("/label/insert")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData2))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO2 = JSONObject.toJavaObject(JSON.parseObject(res2.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO2.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO2.getStatus());

            /**
             * 3. 录入完成
             */
            MvcResult res3 = mockMvc.perform(MockMvcRequestBuilders.get("/label/finish")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(35)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO3 = JSONObject.toJavaObject(JSON.parseObject(res3.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO3.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO3.getStatus());

            /**
             * 4. iqc检测 -- 允许
             */
            String reqData4 = "{" +
                    "\"rbid\": 35," +
                    "\"status\": 1," +
                    "\"gnum\": 500," +
                    "\"bnum\": 500" +
                    "}";

            MvcResult res4 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/iqcTest")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData4))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO4 = JSONObject.toJavaObject(JSON.parseObject(res4.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO4.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO4.getStatus());

            /**
             * 5. 入库单
             */
            MvcResult res5 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/storage")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(35)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO5 = JSONObject.toJavaObject(JSON.parseObject(res5.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO5.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO5.getStatus());

            /**
             * 6. "/storage/label"                 lno:8-11 "", 8-12 " "
             */
            //良品
            String reqData6 = "{" +
                    "\"sid\": 23," +
                    "\"sgid\": \"\"," +
                    "\"rbid\": 35," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072100009\"" +
                    "}";

            //非良品
            String reqData61 = "{" +
                    "\"sid\": 24," +
                    "\"sgid\": \"\"," +
                    "\"rbid\": 35," +
                    "\"lno\": \"8-11\"," +
                    "\"pid\": \"2020072100010\"" +
                    "}";

            MvcResult res6 = mockMvc.perform(MockMvcRequestBuilders.post("/storage/label")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData61))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO6 = JSONObject.toJavaObject(JSON.parseObject(res6.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO6.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO6.getStatus());

            /**
             * 7. "/storage/save"
             */
            //良品
            String reqData7 = "{" +
                    "\"sid\": 23," +
                    "\"sgid\": 18," +
                    "\"rbid\": 35," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072100009\"" +
                    "}";
            //非良品
            String reqData71 = "{" +
                    "\"sid\": 24," +
                    "\"sgid\": 19," +
                    "\"rbid\": 35," +
                    "\"lno\": \"8-11\"," +
                    "\"pid\": \"2020072100010\"" +
                    "}";

            MvcResult res7 = mockMvc.perform(MockMvcRequestBuilders.post("/storage/save")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData71))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO7 = JSONObject.toJavaObject(JSON.parseObject(res7.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO7.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO7.getStatus());

            /**
             * 8. 入库
             */
            //良品入库
            MvcResult res8 = mockMvc.perform(MockMvcRequestBuilders.get("/storage/storage")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("sid", String.valueOf(23)))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO8 = JSONObject.toJavaObject(JSON.parseObject(res8.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO8.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO8.getStatus());

            //非良品入库
            MvcResult res81 = mockMvc.perform(MockMvcRequestBuilders.get("/storage/storage")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("sid", String.valueOf(24)))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO81 = JSONObject.toJavaObject(JSON.parseObject(res81.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO81.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO81.getStatus());
        }
    }

    @Test
    public void overallProcess2() throws Exception {
        /**
         * 整体流程：
         * 1. 新增表单
         * 2. 删除
         * 3. 标签录入
         * 4. 录入完成
         * 5. iqc检测  -- 不允许
         * 6. qeConfirm -- 特采
         * 7. 打印良品/非良品入库单
         * 8. "/storage/label"
         * 9. "/storage/save"
         * 10. 入库
         */

        {
            /**
             * 1. 新增表单
             */
            String reqData1 = "{" +
                    "\"head\": {" +
                    "\"ono\": \"PO20200721\",\n" +
                    "\"otype\": 1,\n" +
                    "\"odate\": \"2020-07-21 00:00:00\"," +
                    "\"supplier\": \"PO收单-东南院供应商\"," +
                    "\"buyer\": \"李大嘴\"," +
                    "\"pdate\": \"2020-07-21 00:00:00\"," +
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

            MvcResult res1 = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/insert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(reqData1)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO1 = JSONObject.toJavaObject(JSON.parseObject(res1.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO1.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO1.getStatus());

            /**
             * 2. 删除
             */
            MvcResult res2 = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/delete")
                    .contentType(MediaType.APPLICATION_JSON).param("rbids[]", String.valueOf(34)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO2 = JSONObject.toJavaObject(JSON.parseObject(res2.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO2.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO2.getStatus());

            /**
             * 3. 录入标签
             */
            String reqData3 = "{" +
                    "\"rbid\": 33," +
                    "\"mno\": \"5101000496\"," +
                    "\"scode\": \"\"," +
                    "\"pdate\": \"20200721\"," +
                    "\"pbatch\": \"20200721\"," +
                    "\"mnum\": 500," +
                    "\"pnum\": 2" +
                    "}";
            MvcResult res3 = mockMvc.perform(MockMvcRequestBuilders.post("/label/insert")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData3))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO3 = JSONObject.toJavaObject(JSON.parseObject(res3.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO3.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO3.getStatus());

            /**
             * 4. 录入完成
             */
            MvcResult res4 = mockMvc.perform(MockMvcRequestBuilders.get("/label/finish")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(33)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO4 = JSONObject.toJavaObject(JSON.parseObject(res4.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO4.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO4.getStatus());

            /**
             * 5. iqc检测 -- 不允许
             */
            String reqData5 = "{" +
                    "\"rbid\": 33," +
                    "\"status\": 2," +
                    "\"gnum\": 500," +
                    "\"bnum\": 500" +
                    "}";

            MvcResult res5 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/iqcTest")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData5))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO5 = JSONObject.toJavaObject(JSON.parseObject(res5.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO5.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO5.getStatus());

            /**
             * 6. qeConfirm -- 特采
             */
            String reqData6 = "{" +
                    "\"rbid\": 33," +
                    "\"status\": 4," +
                    "\"num\": 500" +
                    "}";

            MvcResult res6 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeConfirm")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData6))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO6 = JSONObject.toJavaObject(JSON.parseObject(res6.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO6.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO6.getStatus());

            /**
             * 7. 入库单
             */
            MvcResult res7 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/storage")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(33)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO7 = JSONObject.toJavaObject(JSON.parseObject(res7.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO7.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO7.getStatus());

            /**
             * 8. "/storage/label"                 lno:8-11 "", 8-12 " "
             */
            //良品
            String reqData8 = "{" +
                    "\"sid\": 21," +
                    "\"sgid\": \"\"," +
                    "\"rbid\": 33," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072100005\"" +
                    "}";

            //非良品
            String reqData81 = "{" +
                    "\"sid\": 22," +
                    "\"sgid\": \"\"," +
                    "\"rbid\": 33," +
                    "\"lno\": \"8-11\"," +
                    "\"pid\": \"2020072100011\"" +
                    "}";

            MvcResult res8 = mockMvc.perform(MockMvcRequestBuilders.post("/storage/label")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData8))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO8 = JSONObject.toJavaObject(JSON.parseObject(res8.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO8.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO8.getStatus());

            /**
             * 9. "/storage/save"
             */
            //良品
            String reqData9 = "{" +
                    "\"sid\": 21," +
                    "\"sgid\": 17," +
                    "\"rbid\": 33," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072100005\"" +
                    "}";
            //非良品
            String reqData91 = "{" +
                    "\"sid\": 22," +
                    "\"sgid\": 17," +
                    "\"rbid\": 33," +
                    "\"lno\": \"8-11\"," +
                    "\"pid\": \"2020072100006\"" +
                    "}";

            MvcResult res9 = mockMvc.perform(MockMvcRequestBuilders.post("/storage/save")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData9))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO9 = JSONObject.toJavaObject(JSON.parseObject(res9.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO9.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO9.getStatus());

            /**
             * 10. 入库
             */
            //良品入库
            MvcResult res10 = mockMvc.perform(MockMvcRequestBuilders.get("/storage/storage")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("sid", String.valueOf(21)))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO10 = JSONObject.toJavaObject(JSON.parseObject(res10.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO10.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO10.getStatus());

            //非良品入库
            MvcResult res11 = mockMvc.perform(MockMvcRequestBuilders.get("/storage/storage")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("sid", String.valueOf(22)))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO11 = JSONObject.toJavaObject(JSON.parseObject(res11.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO11.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO11.getStatus());
        }


    }

    @Test
    public void overallProcess3() throws Exception {
        /**
         * 整体流程：
         * 1. 新增表单
         * 2. 删除
         * 3. 标签录入
         * 4. 录入完成
         * 5. iqc检测  -- 不允许
         * 6. qeConfirm -- 驳回
         * 7. iqc重验
         * 8. 打印非良品入库单
         * 9. "/storage/label"
         * 10. "/storage/save"
         * 11. 入库
         */

        {
            /**
             * 1. 新增表单
             */
            String reqData1 = "{" +
                    "\"head\": {" +
                    "\"ono\": \"PO20200721000221\",\n" +
                    "\"otype\": 1,\n" +
                    "\"odate\": \"2020-07-21 00:00:00\"," +
                    "\"supplier\": \"PO收单-东南院供应商\"," +
                    "\"buyer\": \"李大\"," +
                    "\"pdate\": \"2020-07-21 00:00:00\"," +
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

            MvcResult res1 = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/insert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(reqData1)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO1 = JSONObject.toJavaObject(JSON.parseObject(res1.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO1.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO1.getStatus());

            /**
             * 2. 删除
             */
            MvcResult res2 = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/delete")
                    .contentType(MediaType.APPLICATION_JSON).param("rbids[]", String.valueOf(40)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO2 = JSONObject.toJavaObject(JSON.parseObject(res2.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO2.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO2.getStatus());

            /**
             * 3. 录入标签
             */
            String reqData3 = "{" +
                    "\"rbid\": 39," +
                    "\"mno\": \"5101000496\"," +
                    "\"scode\": \"\"," +
                    "\"pdate\": \"20200721\"," +
                    "\"pbatch\": \"20200721\"," +
                    "\"mnum\": 500," +
                    "\"pnum\": 2" +
                    "}";
            MvcResult res3 = mockMvc.perform(MockMvcRequestBuilders.post("/label/insert")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData3))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO3 = JSONObject.toJavaObject(JSON.parseObject(res3.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO3.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO3.getStatus());

            /**
             * 4. 录入完成
             */
            MvcResult res4 = mockMvc.perform(MockMvcRequestBuilders.get("/label/finish")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(39)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO4 = JSONObject.toJavaObject(JSON.parseObject(res4.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO4.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO4.getStatus());

            /**
             * 5. iqc检测 -- 不允许
             */
            String reqData5 = "{" +
                    "\"rbid\": 39," +
                    "\"status\": 2" +
                    "}";

            MvcResult res5 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/iqcTest")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData5))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO5 = JSONObject.toJavaObject(JSON.parseObject(res5.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO5.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO5.getStatus());

            /**
             * 6. qeConfirm -- 驳回
             */
            String reqData6 = "{" +
                    "\"rbid\": 39," +
                    "\"status\": 2" +
                    "}";

            MvcResult res6 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeConfirm")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData6))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO6 = JSONObject.toJavaObject(JSON.parseObject(res6.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO6.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO6.getStatus());

            /**
             * 7. iqc重验
             */
            String reqData7 = "{" +
                    "\"rbid\": 39," +
                    "\"status\": 1," +
                    "\"gnum\": 0," +
                    "\"bnum\": 1000" +
                    "}";

            MvcResult res7 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/iqcTest")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData7))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO7 = JSONObject.toJavaObject(JSON.parseObject(res7.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO7.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO7.getStatus());


            /**
             * 8. 非良品入库单
             */
            MvcResult res8 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/storage")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(39)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO8 = JSONObject.toJavaObject(JSON.parseObject(res8.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO8.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO8.getStatus());

            /**
             * 9. "/storage/label"                 lno:8-11 "", 8-12 " "
             */
            //非良品
            String reqData9 = "{" +
                    "\"sid\": 25," +
                    "\"sgid\": \"\"," +
                    "\"rbid\": 39," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072100013\"" +
                    "}";

            String reqData91 = "{" +
                    "\"sid\": 25," +
                    "\"sgid\": \"\"," +
                    "\"rbid\": 39," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072100014\"" +
                    "}";

            MvcResult res9 = mockMvc.perform(MockMvcRequestBuilders.post("/storage/label")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData9))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO9 = JSONObject.toJavaObject(JSON.parseObject(res9.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO9.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO9.getStatus());

            /**
             * 10. "/storage/save"
             */
            //非良品
            String reqData10 = "{" +
                    "\"sid\": 25," +
                    "\"sgid\": 20," +
                    "\"rbid\": 39," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072100013\"" +
                    "}";

            String reqData101 = "{" +
                    "\"sid\": 25," +
                    "\"sgid\": 21," +
                    "\"rbid\": 39," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072100014\"" +
                    "}";


            MvcResult res10 = mockMvc.perform(MockMvcRequestBuilders.post("/storage/save")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData101))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO10 = JSONObject.toJavaObject(JSON.parseObject(res10.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO10.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO10.getStatus());

            /**
             * 11. 入库
             */
            //非良品入库
            MvcResult res11 = mockMvc.perform(MockMvcRequestBuilders.get("/storage/storage")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("sid", String.valueOf(25)))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO11 = JSONObject.toJavaObject(JSON.parseObject(res11.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO11.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO11.getStatus());

        }
    }


    @Test
    public void overallProcess4() throws Exception {
        /**
         * 整体流程：
         * 1. 新增表单
         * 2. 删除
         * 3. 标签录入
         * 4. 录入完成
         * 5. iqc检测  -- 不允许
         * 6. qeConfirm -- 退回供应商
         */

        {
            /**
             * 1. 新增表单
             */
            String reqData1 = "{" +
                    "\"head\": {" +
                    "\"ono\": \"PO202007220001\",\n" +
                    "\"otype\": 1,\n" +
                    "\"odate\": \"2020-07-22 00:00:00\"," +
                    "\"supplier\": \"PO收单-东南院供应商\"," +
                    "\"buyer\": \"李大\"," +
                    "\"pdate\": \"2020-07-22 00:00:00\"," +
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

            MvcResult res1 = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/insert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(reqData1)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO1 = JSONObject.toJavaObject(JSON.parseObject(res1.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO1.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO1.getStatus());

            /**
             * 2. 删除
             */
            MvcResult res2 = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/delete")
                    .contentType(MediaType.APPLICATION_JSON).param("rbids[]", String.valueOf(42)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO2 = JSONObject.toJavaObject(JSON.parseObject(res2.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO2.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO2.getStatus());

            /**
             * 3. 录入标签
             */
            String reqData3 = "{" +
                    "\"rbid\": 41," +
                    "\"mno\": \"5101000496\"," +
                    "\"scode\": \"\"," +
                    "\"pdate\": \"20200722\"," +
                    "\"pbatch\": \"20200722\"," +
                    "\"mnum\": 500," +
                    "\"pnum\": 2" +
                    "}";
            MvcResult res3 = mockMvc.perform(MockMvcRequestBuilders.post("/label/insert")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData3))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO3 = JSONObject.toJavaObject(JSON.parseObject(res3.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO3.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO3.getStatus());

            /**
             * 4. 录入完成
             */
            MvcResult res4 = mockMvc.perform(MockMvcRequestBuilders.get("/label/finish")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(41)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO4 = JSONObject.toJavaObject(JSON.parseObject(res4.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO4.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO4.getStatus());

            /**
             * 5. iqc检测 -- 不允许
             */
            String reqData5 = "{" +
                    "\"rbid\": 41," +
                    "\"status\": 2" +
                    "}";

            MvcResult res5 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/iqcTest")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData5))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO5 = JSONObject.toJavaObject(JSON.parseObject(res5.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO5.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO5.getStatus());

            /**
             * 6. qeConfirm -- 退回供应商
             */
            String reqData6 = "{" +
                    "\"rbid\": 41," +
                    "\"status\": 5" +
                    "}";

            MvcResult res6 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeConfirm")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData6))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO6 = JSONObject.toJavaObject(JSON.parseObject(res6.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO6.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO6.getStatus());

        }
    }

    @Test
    public void overallProcess5() throws Exception {
        /**
         * 整体流程：
         * 1. 新增表单
         * 2. 删除
         * 3. 标签录入
         * 4. 录入完成
         * 5. qe检验  -- 允许
         * 6. 良品入库单
         * 7. "/storage/label"
         * 8. "/storage/save"
         * 9. 入库
         */

        {
            /**
             * 1. 新增表单
             */
            String reqData1 = "{" +
                    "\"head\": {" +
                    "\"ono\": \"PO202007220002\",\n" +
                    "\"otype\": 1,\n" +
                    "\"odate\": \"2020-07-22 00:00:00\"," +
                    "\"supplier\": \"PO收单-东南院供应商\"," +
                    "\"buyer\": \"李大\"," +
                    "\"pdate\": \"2020-07-22 00:00:00\"," +
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

            MvcResult res1 = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/insert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(reqData1)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO1 = JSONObject.toJavaObject(JSON.parseObject(res1.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO1.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO1.getStatus());

            /**
             * 2. 删除
             */
            MvcResult res2 = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/delete")
                    .contentType(MediaType.APPLICATION_JSON).param("rbids[]", String.valueOf(43)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO2 = JSONObject.toJavaObject(JSON.parseObject(res2.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO2.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO2.getStatus());

            /**
             * 3. 录入标签
             */
            String reqData3 = "{" +
                    "\"rbid\": 44," +
                    "\"mno\": \"5101000496\"," +
                    "\"scode\": \"\"," +
                    "\"pdate\": \"20200722\"," +
                    "\"pbatch\": \"20200722\"," +
                    "\"mnum\": 1000," +
                    "\"pnum\": 2" +
                    "}";
            MvcResult res3 = mockMvc.perform(MockMvcRequestBuilders.post("/label/insert")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData3))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO3 = JSONObject.toJavaObject(JSON.parseObject(res3.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO3.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO3.getStatus());

            /**
             * 4. 录入完成
             */
            MvcResult res4 = mockMvc.perform(MockMvcRequestBuilders.get("/label/finish")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(44)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO4 = JSONObject.toJavaObject(JSON.parseObject(res4.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO4.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO4.getStatus());

            /**
             * 5. qe检测 -- 允许
             */
            String reqData5 = "{" +
                    "\"rbid\": 44," +
                    "\"status\": 1," +
                    "\"gnum\": 2000," +
                    "\"bnum\": 0," +
                    "\"remark\": \"允许\"" +
                    "}";

            MvcResult res5 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeTest")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData5))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO5 = JSONObject.toJavaObject(JSON.parseObject(res5.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO5.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO5.getStatus());

            /**
             * 6. 良品入库单
             */
            MvcResult res6 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/storage")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(44)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO6 = JSONObject.toJavaObject(JSON.parseObject(res6.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO6.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO6.getStatus());

            /**
             * 7. "/storage/label"                 lno:8-11 "", 8-12 " "
             */
            String reqData7 = "{" +
                    "\"sid\": 26," +
                    "\"sgid\": \"\"," +
                    "\"rbid\": 44," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072200003\"" +
                    "}";

            String reqData71 = "{" +
                    "\"sid\": 26," +
                    "\"sgid\": \"\"," +
                    "\"rbid\": 44," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072200004\"" +
                    "}";

            MvcResult res7 = mockMvc.perform(MockMvcRequestBuilders.post("/storage/label")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData71))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO7 = JSONObject.toJavaObject(JSON.parseObject(res7.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO7.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO7.getStatus());

            /**
             * 8. "/storage/save"
             */
            String reqData8 = "{" +
                    "\"sid\": 26," +
                    "\"sgid\": 22," +
                    "\"rbid\": 44," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072200003\"" +
                    "}";

            String reqData81 = "{" +
                    "\"sid\": 26," +
                    "\"sgid\": 23," +
                    "\"rbid\": 44," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072200004\"" +
                    "}";


            MvcResult res8 = mockMvc.perform(MockMvcRequestBuilders.post("/storage/save")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData81))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO8 = JSONObject.toJavaObject(JSON.parseObject(res8.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO8.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO8.getStatus());

            /**
             * 9. 入库
             */
            MvcResult res9 = mockMvc.perform(MockMvcRequestBuilders.get("/storage/storage")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("sid", String.valueOf(26)))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO9 = JSONObject.toJavaObject(JSON.parseObject(res9.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO9.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO9.getStatus());

        }
    }

    @Test
    public void overallProcess6() throws Exception {
        /**
         * 整体流程：
         * 1. 新增表单
         * 2. 删除
         * 3. 标签录入
         * 4. 录入完成
         * 5. qe检验  -- 特采
         * 6. 良品/非良品入库单
         * 7. "/storage/label"
         * 8. "/storage/save"
         * 9. 入库
         */

        {
            /**
             * 1. 新增表单
             */
            String reqData1 = "{" +
                    "\"head\": {" +
                    "\"ono\": \"PO202007220003\",\n" +
                    "\"otype\": 1,\n" +
                    "\"odate\": \"2020-07-22 00:00:00\"," +
                    "\"supplier\": \"PO收单-东南院供应商\"," +
                    "\"buyer\": \"李大\"," +
                    "\"pdate\": \"2020-07-22 00:00:00\"," +
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

            MvcResult res1 = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/insert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(reqData1)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO1 = JSONObject.toJavaObject(JSON.parseObject(res1.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO1.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO1.getStatus());

            /**
             * 2. 删除
             */
            MvcResult res2 = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/delete")
                    .contentType(MediaType.APPLICATION_JSON).param("rbids[]", String.valueOf(45)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO2 = JSONObject.toJavaObject(JSON.parseObject(res2.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO2.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO2.getStatus());

            /**
             * 3. 录入标签
             */
            String reqData3 = "{" +
                    "\"rbid\": 46," +
                    "\"mno\": \"5101000496\"," +
                    "\"scode\": \"\"," +
                    "\"pdate\": \"20200722\"," +
                    "\"pbatch\": \"20200722\"," +
                    "\"mnum\": 500," +
                    "\"pnum\": 4" +
                    "}";
            MvcResult res3 = mockMvc.perform(MockMvcRequestBuilders.post("/label/insert")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData3))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO3 = JSONObject.toJavaObject(JSON.parseObject(res3.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO3.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO3.getStatus());

            /**
             * 4. 录入完成
             */
            MvcResult res4 = mockMvc.perform(MockMvcRequestBuilders.get("/label/finish")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(46)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO4 = JSONObject.toJavaObject(JSON.parseObject(res4.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO4.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO4.getStatus());

            /**
             * 5. qe检测 -- 特采
             */
            String reqData5 = "{" +
                    "\"rbid\": 46," +
                    "\"status\": 4," +
                    "\"gnum\": 500," +
                    "\"remark\": \"特采\"" +
                    "}";

            MvcResult res5 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeTest")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData5))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO5 = JSONObject.toJavaObject(JSON.parseObject(res5.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO5.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO5.getStatus());

            /**
             * 6. 良品入库单
             */
            MvcResult res6 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/storage")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(46)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO6 = JSONObject.toJavaObject(JSON.parseObject(res6.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO6.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO6.getStatus());

            /**
             * 7. "/storage/label"                 lno:8-11 "", 8-12 " "
             */
            //良品
            String reqData7 = "{" +
                    "\"sid\": 27," +
                    "\"sgid\": \"\"," +
                    "\"rbid\": 46," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072200005\"" +
                    "}";
            MvcResult res7 = mockMvc.perform(MockMvcRequestBuilders.post("/storage/label")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData7))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO7 = JSONObject.toJavaObject(JSON.parseObject(res7.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO7.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO7.getStatus());

            //非良品
            String reqData71 = "{" +
                    "\"sid\": 28," +
                    "\"sgid\": \"\"," +
                    "\"rbid\": 46," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072200006\"" +
                    "}";

            String reqData72 = "{" +
                    "\"sid\": 28," +
                    "\"sgid\": \"\"," +
                    "\"rbid\": 46," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072200007\"" +
                    "}";

            String reqData73 = "{" +
                    "\"sid\": 28," +
                    "\"sgid\": \"\"," +
                    "\"rbid\": 46," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072200005\"" +
                    "}";

            MvcResult res70 = mockMvc.perform(MockMvcRequestBuilders.post("/storage/label")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData73))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO70 = JSONObject.toJavaObject(JSON.parseObject(res70.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO70.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO70.getStatus());

            /**
             * 8. "/storage/save"
             */
            //良品
            String reqData8 = "{" +
                    "\"sid\": 27," +
                    "\"sgid\": 24," +
                    "\"rbid\": 46," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072200005\"" +
                    "}";

            //非良品 -- one by one
            String reqData81 = "{" +
                    "\"sid\": 28," +
                    "\"sgid\": 25," +
                    "\"rbid\": 46," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072200006\"" +
                    "}";

            String reqData82 = "{" +
                    "\"sid\": 28," +
                    "\"sgid\": 26," +
                    "\"rbid\": 46," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072200006\"" +
                    "}";

            String reqData83 = "{" +
                    "\"sid\": 28," +
                    "\"sgid\": 27," +
                    "\"rbid\": 46," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020072200006\"" +
                    "}";


            MvcResult res8 = mockMvc.perform(MockMvcRequestBuilders.post("/storage/save")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData81))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO8 = JSONObject.toJavaObject(JSON.parseObject(res8.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO8.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO8.getStatus());

            /**
             * 9. 入库
             */
            //良品
            MvcResult res9 = mockMvc.perform(MockMvcRequestBuilders.get("/storage/storage")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("sid", String.valueOf(27)))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO9 = JSONObject.toJavaObject(JSON.parseObject(res9.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO9.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO9.getStatus());

            //非良品
            MvcResult res91 = mockMvc.perform(MockMvcRequestBuilders.get("/storage/storage")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("sid", String.valueOf(28)))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO91 = JSONObject.toJavaObject(JSON.parseObject(res91.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO91.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO91.getStatus());

        }
    }

    @Test
    public void overallProcess7() throws Exception {
        /**
         * 整体流程：
         * 1. 新增表单
         * 2. 删除
         * 3. 标签录入
         * 4. 录入完成
         * 5. qe检验  -- 退回供应商
         */

        {
            /**
             * 1. 新增表单
             */
            String reqData1 = "{" +
                    "\"head\": {" +
                    "\"ono\": \"PO202007220004\",\n" +
                    "\"otype\": 1,\n" +
                    "\"odate\": \"2020-07-22 00:00:00\"," +
                    "\"supplier\": \"PO收单-东南院供应商\"," +
                    "\"buyer\": \"李大\"," +
                    "\"pdate\": \"2020-07-22 00:00:00\"," +
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
                    "\"ototal\": 1000," +
                    "\"anum\": 1000," +
                    "\"adate\": null" +
                    "}]" +
                    "}";

            MvcResult res1 = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/insert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(reqData1)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO1 = JSONObject.toJavaObject(JSON.parseObject(res1.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO1.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO1.getStatus());

            /**
             * 2. 删除
             */
            MvcResult res2 = mockMvc.perform(MockMvcRequestBuilders.post("/receipt/delete")
                    .contentType(MediaType.APPLICATION_JSON).param("rbids[]", String.valueOf(1)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO2 = JSONObject.toJavaObject(JSON.parseObject(res2.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO2.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO2.getStatus());

            /**
             * 3. 录入标签
             */
            String reqData3 = "{" +
                    "\"rbid\": 2," +
                    "\"mno\": \"5101000496\"," +
                    "\"scode\": \"\"," +
                    "\"pdate\": \"20200722\"," +
                    "\"pbatch\": \"20200722\"," +
                    "\"mnum\": 500," +
                    "\"pnum\": 2" +
                    "}";
            MvcResult res3 = mockMvc.perform(MockMvcRequestBuilders.post("/label/insert")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData3))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO3 = JSONObject.toJavaObject(JSON.parseObject(res3.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO3.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO3.getStatus());

            /**
             * 4. 录入完成
             */
            MvcResult res4 = mockMvc.perform(MockMvcRequestBuilders.get("/label/finish")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(2)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO4 = JSONObject.toJavaObject(JSON.parseObject(res4.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO4.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO4.getStatus());

            /**
             * 5. qe检测 -- 退回供应商
             */
            String reqData5 = "{" +
                    "\"rbid\": 2," +
                    "\"status\": 5," +
                    "\"remark\": \"退回供应商\"" +
                    "}";

            MvcResult res5 = mockMvc.perform(MockMvcRequestBuilders.post("/quality/qeTest")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData5))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO5 = JSONObject.toJavaObject(JSON.parseObject(res5.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO5.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO5.getStatus());

        }
    }
}
