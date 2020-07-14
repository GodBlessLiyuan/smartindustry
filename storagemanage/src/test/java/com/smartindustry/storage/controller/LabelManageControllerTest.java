package com.smartindustry.storage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smartindustry.common.vo.ResultVO;
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
                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(2L)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
    }

    @Test
    public void queryPid() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/label/queryPid")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("rbid", String.valueOf(7L)).param("pid", "2020071300003"))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
    }

    @Test
    public void reprint() throws Exception {
        {

            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/label/reprint")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("plid", String.valueOf(9L)).param("num", "500"))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

    }

    @Test
    public void insert() throws Exception {
//        {
//            //PrintLabelDto
//            String reqData = "{" +
//                    "\"rbid\": 5," +
//                    "\"mno\": \"2020071000003\"," +
//                    "\"scode\": \"20200708\"," +
//                    "\"pdate\": \"202007085\"," +
//                    "\"pbatch\": 300," +
//                    "\"mnum\": \"2020071000001\"," +
//                    "\"pnum\": \"2020-07-10 11:24:21\"" +
//                    "}";
//            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/label/insert")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(reqData))
//                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//
//            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
//            assertNotNull(resultVO.getStatus());
//            assertEquals(Integer.valueOf(1000),resultVO.getStatus());
//        }
    }

    @Test
    public void delete() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/label/delete")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("rbid", String.valueOf(10L)).param("plid", String.valueOf(161L)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
    }

    @Test
    public void finish() throws Exception {
//        {
//            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/label/finish")
//                    .contentType(MediaType.APPLICATION_JSON).param("rbid", String.valueOf(17)))
//                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//
//            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
//            assertNotNull(resultVO.getStatus());
//            assertEquals(Integer.valueOf(1000),resultVO.getStatus());
//        }
    }

    @Test
    public void split() throws Exception {
//        {
//            //LabelSplitDTO
//            String reqData = "{" +
//                    "\"plid\": 9," +
//                    "\"gnum\": 500," +
//                    "\"bnum\": 500" +
//                    "}";
//            System.out.println(reqData);
//            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/label/split")
//                    .contentType(MediaType.APPLICATION_JSON).content(reqData))
//                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//            System.out.println(res);
//            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
//            assertNotNull(resultVO.getStatus());
//            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
//
//
//        }
    }
}
