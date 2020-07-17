package com.smartindustry.storage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.vo.PrintLabelVO;
import org.junit.After;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author: velve
 * @date: Created in 2020/7/9 12:34
 * @description: TODO
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class LabelManageControllerTest extends BaseTest {

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void query() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/label/query")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(1L)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            List<PrintLabelVO> list = JSONObject.parseArray(JSON.toJSONString(resultVO.getData()), PrintLabelVO.class);
            //rbid = 1，有6条记录
            //plid
            assertEquals(Long.valueOf(1), list.get(0).getPlid());
            assertEquals(Long.valueOf(2), list.get(1).getPlid());
            assertEquals(Long.valueOf(3), list.get(2).getPlid());
            assertEquals(Long.valueOf(4), list.get(3).getPlid());
            assertEquals(Long.valueOf(5), list.get(4).getPlid());
            //num
            assertEquals(Integer.valueOf(200), list.get(3).getNum());
        }

    }

    @Test
    public void queryPid() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/label/queryPid")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("rbid", String.valueOf(1L)).param("pid", "2020071500006"))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            PrintLabelVO printLabelVO = JSONObject.toJavaObject(JSON.parseObject(String.valueOf(resultVO.getData())), PrintLabelVO.class);
            //plid
            assertEquals(Long.valueOf(6), printLabelVO.getPlid());
            //num
            assertEquals(Integer.valueOf(500), printLabelVO.getNum());
        }

        {
            /**
             * 异常情况1：数据缺失，返回status：1002
             */
            {
                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/label/queryPid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("rbid", "").param("pid", "2020071500006"))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1002), resultVO.getStatus());
            }
        }
    }

    @Test
    public void reprint() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/label/reprint")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("plid", String.valueOf(2L)).param("num", "500"))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

        {
            /**
             * 异常情况：
             * 异常情况1：dr=2；返回status：1002
             */
            {
                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/label/reprint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("plid", String.valueOf(9L)).param("num", "500"))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1002), resultVO.getStatus());
            }

            /**
             * 异常情况：
             * 异常情况2：未传数据plid；返回status：1002
             */
            {
                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/label/reprint")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("plid", "").param("num", "500"))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1002), resultVO.getStatus());
            }
        }
    }

    @Test
    public void insert() throws Exception {
        {
            //PrintLabelDto
            /**
             手动录入
             */
            String reqData = "{" +
                    "\"rbid\": 1," +
                    "\"mno\": \"5101000496\"," +
                    "\"scode\": \"\"," +
                    "\"pdate\": \"20200708\"," +
                    "\"pbatch\": \"20200708\"," +
                    "\"mnum\": 500," +
                    "\"pnum\": 2" +
                    "}";
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/label/insert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(reqData))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());

        }
        {
            //PrintLabelDto
            /**
             扫描录入
             */
            String reqData1 = "{" +
                    "\"scode\": \"123131231\"" +
                    "}";
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/label/insert")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(reqData1))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
    }

    @Test
    public void delete() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/label/delete")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("rbid", String.valueOf(4)).param("plid", String.valueOf(11L)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

        {
            /**
             * 异常情况：
             * 异常情况1：未输入rbId，返回status：1002
             */
            {
                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/label/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("rbid", "").param("plid", String.valueOf(161L)))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1002), resultVO.getStatus());
            }

            /**
             * 异常情况2：receiptBodyPO.getStatus() != RECEIPT_ENTRY_LABEL
             */
            {
                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/label/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("rbid", String.valueOf(11)).param("plid", ""))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1003), resultVO.getStatus());
            }
        }
    }

    @Test
    public void finish() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/label/finish")
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(5)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

        {
            /**
             * 异常情况：
             * 异常情况1：未传入rbid，返回status：1002
             */
            {
                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/label/finish")
                        .contentType(MediaType.APPLICATION_JSON).param("rbid", ""))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1002), resultVO.getStatus());
            }

            /**
             * 异常情况2：rbid:1,bodyPO.getStatus() != 1，返回status：1003
             */
            {
                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/label/finish")
                        .contentType(MediaType.APPLICATION_JSON).param("rbid", "1"))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1003), resultVO.getStatus());
            }

            /**
             * 异常情况3：rbid:2, labelNum < bodyPO.getAcceptNum()，返回status：1005
             */
            {
                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/label/finish")
                        .contentType(MediaType.APPLICATION_JSON).param("rbid", "3"))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

                ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1005), resultVO.getStatus());
            }

        }
    }

    @Test
    public void split() throws Exception {
        {
            //LabelSplitDTO
            String reqData = "{" +
                    "\"plid\": 4," +
                    "\"gnum\": 800," +
                    "\"bnum\": 200" +
                    "}";
            System.out.println(reqData);
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/label/split")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            System.out.println(res);
            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

        {
            /**
             * 异常情况：
             * 异常情况1：dto.getGnum() + dto.getBnum() != labelPO.getNum()，返回 status：1001；
             */
            {
                //LabelSplitDTO
                String reqData = "{" +
                        "\"plid\": 13," +
                        "\"gnum\": 1000," +
                        "\"bnum\": 0" +
                        "}";
                System.out.println(reqData);
                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/label/split")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
                System.out.println(res);
                ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1001), resultVO.getStatus());
            }

            /**
             * 异常情况2：未传入plid，返回 status：1002；
             */
            {
                //LabelSplitDTO
                String reqData = "{" +
                        "\"plid\": \"\"," +
                        "\"gnum\": 0," +
                        "\"bnum\": 0" +
                        "}";
                System.out.println(reqData);
                MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/label/split")
                        .contentType(MediaType.APPLICATION_JSON).content(reqData))
                        .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
                System.out.println(res);
                ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
                assertNotNull(resultVO.getStatus());
                assertEquals(Integer.valueOf(1002), resultVO.getStatus());
            }
        }
    }
}
