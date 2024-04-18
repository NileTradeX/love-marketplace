package com.love.merchant.service;

import com.love.merchant.bo.MerUserAdminBusinessInfoEditBO;
import com.love.merchant.bo.MerUserAdminBusinessInfoChangeEmailBO;
import com.love.merchant.bo.MerUserAdminBusinessInfoQueryByBizName;
import com.love.merchant.dto.MerUserAdminBusinessInfoDTO;

import java.util.List;

public interface MerUserAdminBusinessInfoService {
    boolean save(MerUserAdminBusinessInfoEditBO businessInfoBO);

    MerUserAdminBusinessInfoDTO queryById(Long id);

    boolean updateById(MerUserAdminBusinessInfoEditBO businessInfo);

    boolean changeBizOrderMgmtEmail(MerUserAdminBusinessInfoChangeEmailBO changeEmailBO);

    List<MerUserAdminBusinessInfoDTO> all();

    MerUserAdminBusinessInfoDTO queryByBizName(MerUserAdminBusinessInfoQueryByBizName queryByBizName);
}
