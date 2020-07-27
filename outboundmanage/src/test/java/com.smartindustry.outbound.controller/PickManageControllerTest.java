package com.smartindustry.outbound.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.outbound.vo.LackMaterialVO;
import com.smartindustry.outbound.vo.PickDetailVO;
import com.smartindustry.outbound.vo.PickHeadVO;
import com.smartindustry.outbound.vo.ScanOutVO;
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

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class PickManageControllerTest extends BaseTest {
    @Test
    public void queryPickHeadMsg() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/pick/queryPick")
                .contentType(MediaType.APPLICATION_JSON).param("mstatus", String.valueOf(1)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        assertNotNull(resultVO.getData());
    }

    @Test
    public void queryPickGoods() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/pick/queryPickGoods")
                .contentType(MediaType.APPLICATION_JSON).param("mstatus", String.valueOf(1)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        ResultVO<ResultVO> resultVO1 = JSONObject.toJavaObject(JSON.parseObject(String.valueOf(resultVO.getData())), ResultVO.class);
        List<PickHeadVO> pickHeadVOS = JSONObject.parseArray(String.valueOf(resultVO1.getData()), PickHeadVO.class);
        //phid == 1
        assertEquals(Long.valueOf(1), pickHeadVOS.get(0).getPhid());
        //cproject "ES7035W"
        assertEquals("ES7035W", pickHeadVOS.get(0).getCproject());
    }

    @Test
    public void materialLoss() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/pick/materialLoss")
                .contentType(MediaType.APPLICATION_JSON).param("phid", String.valueOf(1)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        assertNotNull(resultVO.getData());
        List<LackMaterialVO> lackMaterialVOS = JSONObject.parseArray(JSON.toJSONString(resultVO.getData()), LackMaterialVO.class);
        //dnum == 500
        assertEquals(Integer.valueOf(500), lackMaterialVOS.get(0).getDnum());
        //flag == 1
        assertEquals(Byte.valueOf(String.valueOf(1)), lackMaterialVOS.get(0).getFlag());
    }

    @Test
    public void queryExItems() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/pick/queryExItems")
                .contentType(MediaType.APPLICATION_JSON).param("phid", String.valueOf(1)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
    }

    @Test
    public void detail() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/pick/detail")
                .contentType(MediaType.APPLICATION_JSON).param("phid", String.valueOf(1)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        assertNotNull(resultVO.getData());

        PickDetailVO pickDetailVO = JSONObject.toJavaObject(JSON.parseObject(String.valueOf(resultVO.getData())), PickDetailVO.class);
        //phid == 1
        assertEquals(Long.valueOf(1), pickDetailVO.getPhid());
        //cproject "ES7035W"
        assertEquals("ES7035W", pickDetailVO.getCproject());
    }

    @Test
    public void pickPidOut() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/pick/pickPidOut")
                .contentType(MediaType.APPLICATION_JSON)
                .param("phid", String.valueOf(4))
                .param("pid", "2020072400004"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        assertNotNull(resultVO.getData());
        ScanOutVO scanOutVO = JSONObject.toJavaObject(JSON.parseObject(String.valueOf(resultVO.getData())), ScanOutVO.class);
        //num == 500
        assertEquals(Integer.valueOf(500), scanOutVO.getNum());
    }

    @Test
    public void queryByPhId() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/pick/queryByPhId")
                .contentType(MediaType.APPLICATION_JSON).param("phid", String.valueOf(1)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        assertNotNull(resultVO.getData());
        PickHeadVO pickHeadVO = JSONObject.toJavaObject(JSON.parseObject(String.valueOf(resultVO.getData())), PickHeadVO.class);
        //ostatus == 3
        assertEquals(Byte.valueOf((byte) 3), pickHeadVO.getOstatus());
        //mstatus == 20
        assertEquals(Byte.valueOf((byte) 20), pickHeadVO.getMstatus());
    }

    @Test
    public void packageIdDiv() throws Exception {

        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/pick/packageIdDiv")
                .contentType(MediaType.APPLICATION_JSON)
                .param("plid", String.valueOf(7)).param("snum", String.valueOf(100)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());

        /**
         * 异常情况1：当前PID的是否已在某工单拣货单扫码列表中  返回status：2024
         */
        {
            MvcResult res1 = mockMvc.perform(MockMvcRequestBuilders.post("/pick/packageIdDiv")
                    .contentType(MediaType.APPLICATION_JSON)
                    .param("plid", String.valueOf(2)).param("snum", String.valueOf(200)))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO1 = JSONObject.toJavaObject(JSON.parseObject(res1.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO1.getStatus());
            assertEquals(Integer.valueOf(2024), resultVO1.getStatus());
        }
    }

    @Test
    public void showMsgByPid() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/pick/showMsgByPid")
                .contentType(MediaType.APPLICATION_JSON).param("pid", "2020072200002"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        ScanOutVO scanOutVO = JSONObject.toJavaObject(JSON.parseObject(String.valueOf(resultVO.getData())), ScanOutVO.class);
        //plid == 2
        assertEquals(Long.valueOf(2), scanOutVO.getPlid());
        //num == 500
        assertEquals(Integer.valueOf(500), scanOutVO.getNum());

        /**
         * 异常情况1：pid不存在 返回status：2040
         */
        {
            MvcResult res1 = mockMvc.perform(MockMvcRequestBuilders.post("/pick/showMsgByPid")
                    .contentType(MediaType.APPLICATION_JSON).param("pid", "2020071700002"))
                    .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

            ResultVO<ResultVO> resultVO1 = JSONObject.toJavaObject(JSON.parseObject(res1.getResponse().getContentAsString()), ResultVO.class);
            assertNotNull(resultVO1.getStatus());
            assertEquals(Integer.valueOf(1000), resultVO1.getStatus());
            assertNotNull(resultVO1.getData());
        }
    }

    @Test
    public void showScanItems() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/pick/showScanItems")
                .contentType(MediaType.APPLICATION_JSON).param("phid", String.valueOf(1)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        ResultVO<ResultVO> resultVO1 = JSONObject.toJavaObject(JSON.parseObject(String.valueOf(resultVO.getData())), ResultVO.class);
        List<ScanOutVO> scanOutVOS = JSONObject.parseArray(String.valueOf(resultVO1.getData()), ScanOutVO.class);
        //plid == 2
        assertEquals(Long.valueOf(2), scanOutVOS.get(0).getPlid());
        //num == 500
        assertEquals(Integer.valueOf(500), scanOutVOS.get(0).getNum());

    }

    @Test
    public void deleteScanPid() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/pick/deleteScanPid")
                .contentType(MediaType.APPLICATION_JSON)
                .param("phid", String.valueOf(10))
                .param("plid", String.valueOf(11)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
    }

    @Test
    public void printLabelSplit() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/pick/printLabelSplit")
                .contentType(MediaType.APPLICATION_JSON).param("pid", "2020072200007"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
    }

    @Test
    public void judgeStatus() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.get("/pick/judgeStatus")
                .contentType(MediaType.APPLICATION_JSON).param("phid", String.valueOf(9)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
    }

    @Test
    public void updateException() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/pick/judgeStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .param("phid", String.valueOf(1))
                .param("mno", "5101000496")
                .param("exce", "异常"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
    }

    @Test
    public void outBoundItems() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/pick/outBoundItems")
                .contentType(MediaType.APPLICATION_JSON)
                .param("phid", String.valueOf(9)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
    }

    @Test
    public void deleteSplit() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/pick/deleteSplit")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pid", "2020072400003"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
    }

    @Test
    public void disAgree() throws Exception {

        // 20：取消发货，退货仓库
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/pick/disAgree")
                .contentType(MediaType.APPLICATION_JSON)
                .param("phid", String.valueOf(1))
                .param("status", "1")
                .param("msg", "驳回"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
    }

    @Test
    public void agreeOutBound() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/pick/agreeOutBound")
                .contentType(MediaType.APPLICATION_JSON)
                .param("phid", String.valueOf(4)))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
    }

    @Test
    public void outOrderCheck() throws Exception {
        MvcResult res = mockMvc.perform(MockMvcRequestBuilders.post("/pick/outOrderCheck")
                .contentType(MediaType.APPLICATION_JSON)
                .param("pageNum", String.valueOf(1))
                .param("pageSize", String.valueOf(10000))
                .param("pno", "")
                .param("cproject", ""))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        ResultVO<ResultVO> resultVO = JSONObject.toJavaObject(JSON.parseObject(res.getResponse().getContentAsString()), ResultVO.class);
        assertNotNull(resultVO.getStatus());
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        ResultVO<ResultVO> resultVO1 = JSONObject.toJavaObject(JSON.parseObject(String.valueOf(resultVO.getData())), ResultVO.class);
        List<PickHeadVO> pickHeadVOS = JSONObject.parseArray(JSON.toJSONString(resultVO1.getData()), PickHeadVO.class);
        //phid == 9
        assertEquals(Long.valueOf(9), pickHeadVOS.get(0).getPhid());
        //mstatus == 15
        assertEquals(Byte.valueOf((byte) 15), pickHeadVOS.get(0).getMstatus());
    }
}
