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
                    "\"rbid\": 1," +
                    "\"sgid\": 1," +
                    "\"lno\": \"8-12\"," +
                    "\"pid\": \"2020071500002\"" +
                    "}";

            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/storage/label")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());

            /**
             异常情况1：printLabelId 不为null 返回status：2000
             */
            assertEquals(Integer.valueOf(2000), resultVO.getStatus());

        }
    }

    @Test
    public void edit() throws Exception {
        {
            //StorageDetailDTO
            String reqData = "{" +
                    "\"sid\": 1," +
                    "\"sgid\": 1," +
                    "\"sdid\": 1," +
                    "\"plid\": 2" +
                    "}";

            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/storage/edit")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
    }


    @Test
    public void delete() throws Exception {
        {
            //StorageDetailDTO
            String reqData = "{" +
                    "\"sid\": 1," +
                    "\"sgid\": 1," +
                    "\"sdid\": 1," +
                    "\"plid\": 2" +
                    "}";

            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/storage/delete")
                    .contentType(MediaType.APPLICATION_JSON).content(reqData))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
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
    }

    @Test
    public void storage() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/storage/storage")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("sid", String.valueOf(3)))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());

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

        }
    }
}
