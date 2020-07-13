package com.smartindustry.storage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.StorageApplication;
import com.smartindustry.storage.vo.ReceiptVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;

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
    public void pageQuery() {
//        String reqDate = ;
//
//        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/storage/pageQuery")
//                .contentType(MediaType.APPLICATION_JSON).content(reqDate))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andReturn();
//        ResultVO<ReceiptVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
   }

    @Test
    public void location() {
    }

    @Test
    public void label() {
    }

    @Test
    public void edit() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void save() {
    }

    @Test
    public void storage() {
    }

    @Test
    public void detail() {
    }
}
