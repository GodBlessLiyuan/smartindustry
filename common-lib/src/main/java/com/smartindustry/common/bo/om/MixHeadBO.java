package com.smartindustry.common.bo.om;

import com.smartindustry.common.pojo.om.MixHeadPO;
import lombok.Data;

import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 11:26 2020/10/28
 * @version: 1.0.0
 * @description:
 */
@Data
public class MixHeadBO extends MixHeadPO {
    List<MixBodyBO> bos;
}
