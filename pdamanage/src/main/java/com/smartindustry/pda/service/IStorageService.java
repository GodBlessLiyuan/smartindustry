package com.smartindustry.pda.service;

import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.pda.dto.OperateDTO;
import com.smartindustry.pda.dto.StorageDTO;
import com.smartindustry.pda.dto.StoragePreDTO;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 11:08 2020/11/1
 * @ Description：成品入库服务类
 * @ Modified By：
 * @ Version:     1.0
 */
public interface IStorageService {
    /**
     * mes系统构建生产订单后，调用wms接口生成入库单
     *
     * @param dto
     * @return
     */
    ResultVO generateStockbill(OperateDTO dto);

    /**
     * mes系统打包时将rfid和入库单号绑定
     *
     * @param dto
     * @return
     */
    ResultVO rfidBound(OperateDTO dto);

    /**
     * pda打包时将rfid和入库单号绑定
     *
     * @param dto
     * @return
     */
    ResultVO rfidBoundStorageHeadId(StorageDTO dto);

    /**
     * 入库单详情
     *
     * @param dto
     * @return
     */
    ResultVO detail(HttpSession session, StorageDTO dto);

    /**
     * 叉车插货物动作
     *
     * @param mrfid
     * @return
     */
    ResultVO execute(HttpSession session, String mrfid);

    /**
     * 叉车插备料区货物动作
     *
     * @param mrfid
     * @return
     */
    ResultVO executeForPre(HttpSession session, String mrfid);

    /**
     * 叉车插成品区货物动作
     *
     * @param mrfid
     * @return
     */
    ResultVO executeForStorage(HttpSession session, String mrfid);

    /**
     * 备料区入库提示选择消息弹窗接口
     *
     * @param mrfid
     * @return
     */
    ResultVO chooseMaterialShow(HttpSession session, String mrfid);

    /**
     * 备料区入库提示选择那种物料确认
     *
     * @param dto
     * @return
     */
    ResultVO chooseMaterialConfirm(HttpSession session, StorageDTO dto);


    /**
     * @Description 选择物料确认入库到备料区
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/12
     * @Time 16:41
     */
    ResultVO chooseMaterialToSpareArea(HttpSession session, StorageDTO dto);

    /**
     * @Description 原产区入库到成品区
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/10
     * @Time 16:41
     */
    ResultVO finishedOriginToStorage(HttpSession session, String mrfid, String lrfid);

    /**
     * @Description 原产区入库到备料区
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/10
     * @Time 16:40
     */
    ResultVO finishedOriginToSpareArea(HttpSession session, String mrfid, String lrfid);

    /**
     * @Description 备料区入成品区
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/10
     * @Time 16:40
     */
    ResultVO finishedSpareAreaToStorage(HttpSession session, String mrfid, String lrfid);

    /**
     * @Description 库内完成平移操作
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/12
     * @Time 16:40
     */
    ResultVO finishedMove(HttpSession session, String mrfid, String lrfid, Byte ltype);

    /**
     * @Description 原产品放下货物动作
     * @Param
     * @Return
     * @Author AnHongxu.
     * @Date 2020/11/14
     * @Time 16:40
     */
    ResultVO finishedOriginToOrigin(HttpSession session, String mrfid, String lrfid);

}
