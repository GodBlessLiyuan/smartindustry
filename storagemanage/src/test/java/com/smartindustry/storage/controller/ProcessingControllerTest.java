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
    public void overallProcess() throws Exception {
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
                    "\"pid\": \"2020072100006\"" +
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

}
