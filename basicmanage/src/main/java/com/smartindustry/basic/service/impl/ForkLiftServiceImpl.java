package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.dto.ForkLiftDTO;
import com.smartindustry.basic.service.IForkLiftService;
import com.smartindustry.basic.vo.ForkliftRecordVO;
import com.smartindustry.basic.vo.ForkliftVO;
import com.smartindustry.common.bo.si.ForkliftRecordBO;
import com.smartindustry.common.mapper.si.ForkliftMapper;
import com.smartindustry.common.mapper.si.ForkliftRecordMapper;
import com.smartindustry.common.pojo.am.UserPO;
import com.smartindustry.common.pojo.si.ForkliftPO;
import com.smartindustry.common.pojo.si.ForkliftRecordPO;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 14:16 2020/10/29
 * @version: 1.0.0
 * @description:
 */
@Service
public class ForkLiftServiceImpl implements IForkLiftService {
    @Autowired
    private ForkliftMapper forkliftMapper;
    @Autowired
    private ForkliftRecordMapper forkliftRecordMapper;
    @Autowired
    private TokenService tokenService;

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<ForkliftPO> page = PageQueryUtil.startPage(reqData);
        List<ForkliftPO> bos = forkliftMapper.pageQuery(reqData);
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), ForkliftVO.convert(bos)));
    }

    @Override
    public ResultVO edit(ForkLiftDTO dto){
        UserPO user = tokenService.getLoginUser();
        if (null == dto.getFid()) {
            //叉车编号重复
            ForkliftPO forkliftPO = forkliftMapper.queryNo(dto.getFno());
            if(forkliftPO!=null){
                return new ResultVO(1201);
            }
            ForkliftPO po = ForkLiftDTO.createPO(dto);
            forkliftMapper.insert(po);
            forkliftRecordMapper.insert(new ForkliftRecordPO(po.getForkliftId(),user.getUserId(),"新增"));
            return ResultVO.ok();
        }
        ForkliftPO po = forkliftMapper.selectByPrimaryKey(dto.getFid());
        if (null == po) {
            // 采购单表头不存在
            return new ResultVO(1002);
        }
        ForkliftPO forkliftPO = ForkLiftDTO.buildPO(po,dto);
        forkliftMapper.updateByPrimaryKeySelective(forkliftPO);
        forkliftRecordMapper.insert(new ForkliftRecordPO(po.getForkliftId(),user.getUserId(),"编辑"));
        return ResultVO.ok();
    }

    @Override
    public ResultVO delete(List<Long> fid) {
        forkliftMapper.deleteDr(fid);
        return ResultVO.ok();
    }

    @Override
    public ResultVO record(Long fid) {
        List<ForkliftRecordBO> forkLiftRecordBOS=forkliftRecordMapper.listRecord(fid);
        return new ResultVO(1000, ForkliftRecordVO.convert(forkLiftRecordBOS));
    }

    @Override
    public ResultVO detail(Long fid) {
        ForkliftPO forkliftPO = forkliftMapper.selectByPrimaryKey(fid);
        if(forkliftPO==null){
            return new ResultVO(1002);
        }
        return new ResultVO(1000,ForkliftVO.convert(forkliftPO));
    }
}
