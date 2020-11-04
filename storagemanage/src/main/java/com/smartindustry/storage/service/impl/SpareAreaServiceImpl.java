package com.smartindustry.storage.service.impl;

import com.smartindustry.common.bo.si.LocationBO;
import com.smartindustry.common.mapper.si.ForkliftMapper;
import com.smartindustry.common.mapper.si.LocationMapper;
import com.smartindustry.common.mapper.si.MaterialMapper;
import com.smartindustry.common.mapper.sm.StorageBodyMapper;
import com.smartindustry.common.mapper.sm.StorageDetailMapper;
import com.smartindustry.common.mapper.sm.StorageHeadMapper;
import com.smartindustry.common.pojo.si.ForkliftPO;
import com.smartindustry.common.pojo.si.MaterialPO;
import com.smartindustry.common.pojo.sm.StorageBodyPO;
import com.smartindustry.common.pojo.sm.StorageDetailPO;
import com.smartindustry.common.pojo.sm.StorageHeadPO;
import com.smartindustry.common.util.DateUtil;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.constant.StorageConstant;
import com.smartindustry.storage.dto.StoragePreDTO;
import com.smartindustry.storage.service.ISpareAreaService;
import com.smartindustry.storage.util.StorageNoUtil;
import com.smartindustry.storage.vo.MaterialVO;
import com.smartindustry.storage.vo.SpareMaterialVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:27 2020/11/2
 * @version: 1.0.0
 * @description:
 */
@Service
public class SpareAreaServiceImpl implements ISpareAreaService {
    @Autowired
    private LocationMapper locationMapper;
    @Autowired
    private StorageHeadMapper storageHeadMapper;
    @Autowired
    private StorageBodyMapper storageBodyMapper;
    @Autowired
    private ForkliftMapper forkliftMapper;
    @Autowired
    private StorageDetailMapper storageDetailMapper;
    @Autowired
    private MaterialMapper materialMapper;
    @Override
    public ResultVO enterSpare(StoragePreDTO dto, HttpSession session){
        // 当前叉车信息
        String imei = (String) session.getAttribute(StorageConstant.SESSION_IMEI);
        // 根据imei查询出叉车id
        ForkliftPO forkliftPO = forkliftMapper.queryByImei(imei);
        // 根据栈板rfid查询入库单
        StorageHeadPO storageHeadPO = storageHeadMapper.queryByRfid(dto.getPrfid());
        // 查询当前储位的基本信息
        Date date = new Date();
        LocationBO locationBO = locationMapper.queryByRfid(dto.getLrfid());

        // 当前入库表体
        StorageBodyPO bodyPO = storageBodyMapper.queryByShidAndLid(storageHeadPO.getStorageHeadId(),locationBO.getLocationId());

        //# 当前储位为成品区并且来源订单类型是生产入库
        if(locationBO.getLocationTypeId().equals(StorageConstant.TYPE_FINISHED_AREA) && storageHeadPO.getSourceType().equals(StorageConstant.TYPE_PRODUCT_STORAGE)){
            //1. 新增入库详情表
            StorageDetailPO detailPO = new StorageDetailPO();
            detailPO.setLocationId(locationBO.getLocationId());
            detailPO.setStorageTime(date);
            detailPO.setRfid(dto.getPrfid());
            storageDetailMapper.insert(detailPO);
            //2. 入库单表体已入库数量+1
            if(null != bodyPO){
                bodyPO.setStorageNum(bodyPO.getStorageNum().add(new BigDecimal(1)));
                storageBodyMapper.updateByPrimaryKeySelective(bodyPO);
            }else {
                bodyPO.setStorageNum(new BigDecimal(1));
                bodyPO.setStorageHeadId(storageHeadPO.getStorageHeadId());
                bodyPO.setLocationId(locationBO.getLocationId());
                bodyPO.setCreateTime(date);
                bodyPO.setDr((byte) 1);
                storageBodyMapper.insert(bodyPO);
            }
            storageHeadPO.setStorageNum(bodyPO.getStorageNum().add(new BigDecimal(1)));
            //判断入库单的状态
            if(storageHeadPO.getStorageNum().add(new BigDecimal(1)).compareTo(storageHeadPO.getExpectNum()) == -1){
                storageHeadPO.setStatus(StorageConstant.STATUS_INSTORAGE);
            }else {
                storageHeadPO.setStatus(StorageConstant.STATUS_STORED);
                storageHeadPO.setStorageTime(date);
            }
            // 判断储位是否已满
            if(!judgeFull(locationBO.getLocationId())){
                // 储位已满
                return new ResultVO(1007);
            }
            storageHeadMapper.updateByPrimaryKeySelective(storageHeadPO);
        }

        //# 叉车运送到备货区,rfid 和 入库单解绑,也就是删除其生产来源单号
        if(locationBO.getLocationTypeId().equals(StorageConstant.TYPE_PREPARATION_AREA) && storageHeadPO.getSourceType().equals(StorageConstant.TYPE_PRODUCT_STORAGE)){
            // 首先新增入库详情表
            StorageDetailPO detailPO = new StorageDetailPO();
            detailPO.setLocationId(locationBO.getLocationId());
            detailPO.setStorageTime(date);
            detailPO.setRfid(dto.getPrfid());
            detailPO.setMaterialId(dto.getMid());
            storageDetailMapper.insert(detailPO);
            // 解绑,就是将入库单的来源单号给置空,根据入库单表头id和rfid删除
            storageDetailMapper.deleteByShidAndRfid(storageHeadPO.getStorageHeadId(),dto.getPrfid());
            // 判断储位是否已满
            if(!judgeFull(locationBO.getLocationId())){
                // 储位已满
                return new ResultVO(1007);
            }
        }

        //# 阅读器扫描备货区成品，准备运往成品区
        if(locationBO.getLocationTypeId().equals(StorageConstant.TYPE_PREPARATION_AREA) && storageHeadPO == null){
            // 那么入库详情表需要删除
            storageDetailMapper.deleteDetail(locationBO.getLocationId(),dto.getPrfid());
        }

        //# 阅读器扫描成品区库位，并且来源是备货入库单，入库一条成功后，此时生成备货入库单
        if(locationBO.getLocationTypeId().equals(StorageConstant.TYPE_FINISHED_AREA) && storageHeadPO == null){
            // 如果当前时间段没有备料入库单，那么就新增一条备料入库单
            Map<String,Date> map = DateUtil.belongCalendar(date);
            StorageHeadPO po = storageHeadMapper.queryPrepareNo(new Date(),map.get("start"),map.get("end"));
            if(null == po){
                // 生成备料区入库单
                StorageHeadPO headPO1 = new StorageHeadPO();
                headPO1.setStorageNo(StorageNoUtil.genStorageHeadNo(storageHeadMapper,StorageNoUtil.RECEIPT_HEAD_YP,new Date()));
                headPO1.setSourceType(StorageConstant.TYPE_PRE_STORAGE);
                headPO1.setStorageNum(new BigDecimal(1));
                headPO1.setStatus(StorageConstant.STATUS_INSTORAGE);
                headPO1.setCreateTime(date);
                headPO1.setDr((byte)1);
                storageHeadMapper.insert(headPO1);
            }else {
                po.setStorageNum(po.getStorageNum().add(new BigDecimal(1)));
                storageHeadMapper.updateByPrimaryKey(po);
            }
            StorageDetailPO detailPO = new StorageDetailPO();
            detailPO.setLocationId(locationBO.getLocationId());
            detailPO.setStorageTime(date);
            detailPO.setRfid(dto.getPrfid());
            detailPO.setStorageStatus(StorageConstant.STATUS_STORED);
            // 判断储位是否已满
            if(!judgeFull(locationBO.getLocationId())){
                // 储位已满
                return new ResultVO(1007);
            }
        }
        return ResultVO.ok();
    }

    /**
     * 查看当前储位是否已满
     * @param lid
     * @return
     */
    public boolean judgeFull(Long lid){
        // 获取当前储位的在库数量
        BigDecimal curLnoNum = storageDetailMapper.queryStored(lid);
        BigDecimal trayNum = locationMapper.selectByPrimaryKey(lid).getHoldTrayNum();
        if(curLnoNum.compareTo(trayNum)==-1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public ResultVO chooseMaterial(StoragePreDTO dto){
        // 根据栈板rfid查询入库单
        StorageHeadPO storageHeadPO = storageHeadMapper.queryByRfid(dto.getPrfid());
        List<MaterialPO> pos = storageBodyMapper.queryMaterial(storageHeadPO.getStorageHeadId());
        return ResultVO.ok().setData(MaterialVO.convertPO(pos));
    }

    @Override
    public ResultVO showSpare(StoragePreDTO dto){
        // 根据栈板rfid查询入库单号
        StorageHeadPO storageHeadPO = storageHeadMapper.queryByRfid(dto.getPrfid());
        LocationBO locationBO = locationMapper.queryByRfid(dto.getLrfid());
        StorageDetailPO po = storageDetailMapper.queryByLidAndRfid(locationBO.getLocationId(),dto.getPrfid());
        MaterialPO materialPO = materialMapper.selectByPrimaryKey(po.getMaterialId());
        SpareMaterialVO vo = new SpareMaterialVO();
        if(null == storageHeadPO){
            vo.setFlag(true);
        }
        vo.setRfid(dto.getPrfid());
        vo.setMmodel(materialPO.getMaterialModel());
        vo.setMname(materialPO.getMaterialName());
        return ResultVO.ok().setData(vo);
    }
}
