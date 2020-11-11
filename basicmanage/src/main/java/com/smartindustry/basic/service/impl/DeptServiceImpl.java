package com.smartindustry.basic.service.impl;

import com.github.pagehelper.Page;
import com.smartindustry.basic.service.IDeptService;
import com.smartindustry.basic.vo.DeptVO;
import com.smartindustry.common.bo.am.DeptBO;
import com.smartindustry.common.mapper.am.DeptMapper;
import com.smartindustry.common.pojo.am.DeptPO;
import com.smartindustry.common.security.service.TokenService;
import com.smartindustry.common.util.PageQueryUtil;
import com.smartindustry.common.vo.PageInfoVO;
import com.smartindustry.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author: jiangzhaojie
 * @date: Created in 9:24 2020/7/30
 * @version: 1.0.0
 * @description:
 */
@EnableTransactionManagement
@Service
public class DeptServiceImpl implements IDeptService {
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    TokenService tokenService;
    @Override
    public ResultVO pageQuery(Map<String, Object> reqData){
        Page<DeptBO> page = PageQueryUtil.startPage(reqData);
        List<DeptBO> bos = deptMapper.deptPageQuery(reqData);
        List<DeptVO> vos = DeptVO.convert(bos);
        for (DeptVO vo:vos){
            List<Long> deptIds = getDeptLevel(vo.getDid());
            Collections.reverse(deptIds);
            vo.setDcode(deptIds.subList(0,deptIds.size()-1));
        }
        return ResultVO.ok().setData(new PageInfoVO<>(page.getTotal(), vos));
    }

    /****
     * 部门详情
     * @param did
     * @return
     */
    @Override
    public ResultVO detail(Long did) {
        DeptBO deptBO=deptMapper.getBOByPri(did);
        return new ResultVO(1000,DeptVO.convert(deptBO));
    }

    /**
     * 根据当前的部门id,获得父节点列表
     * @param deptId
     * @return
     */
    private List<Long> getDeptLevel(Long deptId){
        List<Long> deptIds = new ArrayList<>();
        while (deptId !=null){
            DeptPO po = deptMapper.selectByPrimaryKey(deptId);
            deptIds.add(po.getDeptId());
            deptId = po.getParentId();
        }
        return deptIds;
    }


}
