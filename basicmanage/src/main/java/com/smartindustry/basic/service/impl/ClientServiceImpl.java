package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.constant.BasicConstant;
import com.smartindustry.basic.dto.ClientDTO;
import com.smartindustry.basic.dto.OperateDTO;
import com.smartindustry.basic.service.IClientService;
import com.smartindustry.basic.vo.ClientRecordVO;
import com.smartindustry.basic.vo.ClientVO;
import com.smartindustry.common.bo.si.ClientBO;
import com.smartindustry.common.bo.si.ClientRecordBO;
import com.smartindustry.common.mapper.om.PickHeadMapper;
import com.smartindustry.common.mapper.si.ClientMapper;
import com.smartindustry.common.mapper.si.ClientRecordMapper;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.om.PickHeadPO;
import com.smartindustry.common.pojo.si.ClientPO;
import com.smartindustry.common.pojo.si.ClientRecordPO;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:35 2020/9/16
 * @version: 1.0.0
 * @description:
 */
@EnableTransactionManagement
@Service
public class ClientServiceImpl implements IClientService {
    @Autowired
    private ClientMapper clientMapper;
    @Autowired
    private ClientRecordMapper clientRecordMapper;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private PickHeadMapper pickHeadMapper;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<ClientBO> page = PageQueryUtil.startPage(reqData);
        List<ClientBO> bos = clientMapper.pageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), ClientVO.convert(bos)));
    }

    @Override
    public ResultVO delete(List<Long> cids){
        for(Long cid:cids){
            //todo
            ClientPO po = clientMapper.selectByPrimaryKey(cid);
            List<PickHeadPO> pos = pickHeadMapper.queryByCname(po.getClientName());
            if(!pos.isEmpty()){
                //存在客户有关联订单，不可删除
                return new ResultVO(1007);
            }
        }
        clientMapper.batchDelete(cids);
        return ResultVO.ok();
    }

    @Override
    public ResultVO edit(ClientDTO dto){
        UserPO user = tokenService.getLoginUser();
        // 客户代码唯一
        ClientPO existPO = clientMapper.queryNo(dto.getCno());
        if (null != existPO && (null == dto.getCid() || !dto.getCid().equals(existPO.getClientId()))) {
            return new ResultVO(1004);
        }
        if(null == dto.getCid()){
            // 新增
            ClientPO po = ClientDTO.createPO(dto);
            clientMapper.insert(po);
            clientRecordMapper.insert(new ClientRecordPO(po.getClientId(),user.getUserId(),new Date(), BasicConstant.RECORD_ADD));
            return ResultVO.ok();
        }
        // 编辑
        ClientPO clientPO = clientMapper.selectByPrimaryKey(dto.getCid());
        if (null == clientPO) {
            return new ResultVO(1002);
        }
        ClientDTO.buildPO(clientPO, dto);
        clientPO.setUpdateTime(new Date());
        clientMapper.updateByPrimaryKeySelective(clientPO);
        clientRecordMapper.insert(new ClientRecordPO(clientPO.getClientId(),user.getUserId(),new Date(), BasicConstant.RECORD_EDIT));
        return ResultVO.ok();
    }


    @Override
    public ResultVO queryRecord(OperateDTO dto){
        List<ClientRecordBO> bos = clientRecordMapper.queryByCid(dto.getCid());
        Map<String, Object> map = new HashMap<>();
        map.put("record", bos.isEmpty() ? null : ClientRecordVO.convert(bos));
        return ResultVO.ok().setData(map);
    }
}
