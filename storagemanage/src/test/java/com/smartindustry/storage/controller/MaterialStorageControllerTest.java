package com.smartindustry.storage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.vo.ReceiptVO;
import com.smartindustry.storage.vo.StorageDetailVO;
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
public class MaterialStorageControllerTest extends BaseTest {

    @Test
    public void pageQuery() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/storage/pageQuery")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("pageNum", String.valueOf(1))
                    .param("pageSize", String.valueOf(10))
                    .param("type", "1")
            ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
        }
    }

    @Test
    public void location() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/storage/location")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("lno", "8-10"))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
    }

    @Test
    public void label() throws Exception {
        {
            //StorageGroupDTO
            String reqData = "{" +
                    "\"sid\": 1," +
                    "\"sgid\": 1," +
                    "\"rbid\": 1," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020071500004\"" +
                    "}";

            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/storage/label")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

        {
            /**
             * 异常情况：
             * 异常情况1："rbid = 1, dr = 2", 数据被删除，找不到PrintLabelBO；返回status：1002 --> reqData1
             * 异常情况2：未传入sid，返回status：1002 --> reqData2
             * 异常情况3：未传入pid，返回status：1002 --> reqData3
             */
            {
                //StorageGroupDTO
                String reqData1 = "{" +
                        "\"sid\": 1," +
                        "\"rbid\": 1," +
                        "\"sgid\": 1," +
                        "\"lno\": \"8-12\"," +
                        "\"pid\": \"2020071500001\"" +
                        "}";

                //StorageGroupDTO
                String reqData2 = "{" +
                        "\"sid\": \"\"," +
                        "\"rbid\": 1," +
                        "\"sgid\": 1," +
                        "\"lno\": \"8-12\"," +
                        "\"pid\": \"2020071500001\"" +
                        "}";

                //StorageGroupDTO
                String reqData3 = "{" +
                        "\"sid\": 1," +
                        "\"rbid\": 1," +
                        "\"sgid\": 1," +
                        "\"lno\": \"8-12\"," +
                        "\"pid\": \"\"" +
                        "}";

                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/storage/label")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData3))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1002), resultVO.getStatus());
            }

            /**
             * 异常情况4：StorageDetailPO != null, 标签已使用 返回status：1004
             */
            {
                //StorageGroupDTO
                String reqData4 = "{" +
                        "\"sid\": 2," +
                        "\"sgid\": \"\"," +
                        "\"lno\": \"\"," +
                        "\"rbid\": \"1\"," +
                        "\"pid\": \"2020071500003\"" +
                        "}";

                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/storage/label")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData4))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1004), resultVO.getStatus());
            }
        }
    }

    @Test
    public void edit() throws Exception {
        {
            //StorageDetailDTO
            String reqData = "{" +
                    "\"sid\": 1," +
                    "\"sgid\": 1," +
                    "\"sdid\": 2," +
                    "\"plid\": 3" +
                    "}";

            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/storage/edit")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

        {
            /**
             * 异常情况：
             * 异常情况1：StorageDetailDTO中sid：null；返回status：1002； --> reqData1
             * 异常情况2：StorageDetailDTO中sgid：null；返回status：1002； --> reqData2
             */
            {
                //StorageDetailDTO
                String reqData1 = "{" +
                        "\"sid\": \"\"," +
                        "\"sgid\": 1," +
                        "\"sdid\": 2," +
                        "\"plid\": 3" +
                        "}";

                //StorageDetailDTO
                String reqData2 = "{" +
                        "\"sgid\": 3," +
                        "\"sid\": 4" +
                        "\"lno\": \"8-11\"," +
                        "\"rbid\": 15," +
                        "\"pid\": \"2020071500019\"" +
                        "}";

                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/storage/edit")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData1))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            }
        }
    }


    @Test
    public void delete() throws Exception {
        {
            //StorageDetailDTO
            String reqData = "{" +
                    "\"sid\": 1," +
                    "\"sgid\": 1," +
                    "\"sdid\": 2," +
                    "\"plid\": 3" +
                    "}";

            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/storage/delete")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

        {
            /**
             * 异常情况：
             * 异常情况1：StorageDetailDTO中sid：null；返回status：1002； --> reqData1
             * 异常情况2：StorageDetailDTO中sgid：null；返回status：1002； --> reqData2
             */
            {
                //StorageDetailDTO
                String reqData1 = "{" +
                        "\"sid\": \"\"," +
                        "\"sgid\": 1," +
                        "\"sdid\": 2," +
                        "\"plid\": 3" +
                        "}";

                //StorageGroupDTO
                String reqData2 = "{" +
                        "\"sid\": 1," +
                        "\"sgid\": \"\"," +
                        "\"sdid\": 2," +
                        "\"plid\": 3" +
                        "}";

                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/storage/delete")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData1))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1002), resultVO.getStatus());
            }
        }
    }

    @Test
    public void save() throws Exception {
        {
            //StorageGroupDTO
            String reqData = "{" +
                    "\"sid\": 1," +
                    "\"rbid\": 1," +
                    "\"sgid\": 1," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020071500002\"" +
                    "}";

            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/storage/save")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

        {
            /**
             * 异常情况：
             * 异常情况1：StorageGroupDTO中sid：null，返回status：1002； --> reqData1
             * 异常情况2：StorageGroupDTO中rbid：null，返回status：1002； --> reqData2
             * 异常情况3：StorageGroupDTO中sgid：null，返回status：1002； --> reqData3
             */
            {
                //StorageGroupDTO
                String reqData1 = "{" +
                        "\"sid\": \"\"," +
                        "\"rbid\": 1," +
                        "\"sgid\": 1," +
                        "\"lno\": \"8-12\"," +
                        "\"pid\": \"2020071500002\"" +
                        "}";

                //StorageGroupDTO
                String reqData2 = "{" +
                        "\"sid\": 1," +
                        "\"rbid\": \"\"," +
                        "\"sgid\": 1," +
                        "\"lno\": \"8-12\"," +
                        "\"pid\": \"2020071500002\"" +
                        "}";

                //StorageGroupDTO
                String reqData3 = "{" +
                        "\"sid\": 1," +
                        "\"rbid\": 1," +
                        "\"sgid\": \"\"," +
                        "\"lno\": \"8-12\"," +
                        "\"pid\": \"2020071500002\"" +
                        "}";

                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/storage/save")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData1))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1002), resultVO.getStatus());
            }

            /**
             * 异常情况4：StorageGroupDTO中sgid：null，返回status：1001； --> reqData2
             */
            {
                //StorageGroupDTO
                String reqData4 = "{" +
                        "\"sid\": 1," +
                        "\"rbid\": 1," +
                        "\"sgid\": 1," +
                        "\"lno\": \"8-12\"," +
                        "\"pid\": \"2020071500002\"" +
                        "}";

                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/storage/save")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData4))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1001), resultVO.getStatus());
            }
        }
    }

    @Test
    public void storage() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/storage/storage")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("sid", String.valueOf(4)))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

        {
            /**
             * 异常情况：
             * 异常情况1：sid:5,-> storagePO=null; 返回status：1002
             */
            {
                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/storage/storage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("sid", String.valueOf(5)))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1002), resultVO.getStatus());
            }

            /**
             * 异常情况2：sid:1,-> pendingNum != storedNum,数据不匹配; 返回status：1005
             */
            {
                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/storage/storage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("sid", String.valueOf(1)))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1005), resultVO.getStatus());
            }

            /**
             * 异常情况3：sid:4,-> storagePO.getStatus() != MATERIAL_STORAGE_BEING,单据状态有误; 返回status：1003
             */
            {
                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/storage/storage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("sid", String.valueOf(1)))
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1003), resultVO.getStatus());
            }
        }
    }

    @Test
    public void detail() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/storage/detail")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("sid", String.valueOf(3)))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            StorageDetailVO storageDetailVO = JSONObject.toJavaObject(JSON.parseObject(String.valueOf(resultVO.getData())), StorageDetailVO.class);
            //入库单
            assertEquals(Long.valueOf(3), storageDetailVO.getSid());
            //待入库数
            assertEquals(Integer.valueOf(1000), storageDetailVO.getPnum());
            //已入库数
            assertEquals(Integer.valueOf(200), storageDetailVO.getSnum());
        }

        {
            /**
             * 异常情况：
             * 异常情况1：未传入sid，storagePO=null；返回status=1002；
             */
            {
                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/storage/detail")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("sid", "")).andDo(MockMvcResultHandlers.print())
                        .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1002), resultVO.getStatus());
            }
        }
    }
}
