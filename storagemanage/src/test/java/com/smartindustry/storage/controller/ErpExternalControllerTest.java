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
 * @date: Created in 2020/7/9 12:34
 * @description: TODO
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ErpExternalControllerTest extends BaseTest {

    @Test
    public void order() throws Exception {
        {
            MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/erp/order")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("ono", "PO2020020220200202").param("otype", "1"))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            ReceiptVO receiptVO = JSONObject.toJavaObject(JSON.parseObject(String.valueOf(resultVO.getData())), ReceiptVO.class);
            //buyer
            assertEquals("轩辕先生", receiptVO.getHead().getBuyer());
            //ototal
            assertEquals(Integer.valueOf(1000), receiptVO.getBody().get(0).getOtotal());


        }
    }
}
