package com.smartindustry.storage.service.impl;

import com.smartindustry.common.mapper.PrintLabelMapper;
import com.smartindustry.common.pojo.PrintLabelPO;
import com.smartindustry.common.vo.ResultVO;
import com.smartindustry.storage.dto.LabelDTO;
import com.smartindustry.storage.service.ILabelManageService;
import com.smartindustry.storage.util.ReceiptNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/6/28 15:06
 * @description: 标签管理
 * @version: 1.0
 */
@Service
public class LabelManageServiceImpl implements ILabelManageService {
    @Autowired
    private PrintLabelMapper printLabelMapper;

    @Override
    public ResultVO insert(LabelDTO dto) {
        if (StringUtils.isEmpty(dto.getScode())) {
            // 手动录入
            List<PrintLabelPO> pos = new ArrayList<>();
            int num = ReceiptNoUtil.getLabelNum(printLabelMapper, null, new Date());
            Date curDate = new Date();
            for (int i = 0; i < dto.getPnum(); i++) {
                pos.add(LabelDTO.createPO(dto, ++num, curDate));
            }
            printLabelMapper.batchInsert(pos);

            return ResultVO.ok();
        }

        // 扫描录入
        return null;
    }
}
