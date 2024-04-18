package com.love.merchant.client;

import com.love.merchant.bo.MerQueryByAdminIdBO;
import com.love.merchant.bo.MerUserAdminBusinessInfoChangeEmailBO;
import com.love.merchant.bo.MerUserAdminBusinessInfoEditBO;
import com.love.merchant.bo.MerUserAdminBusinessInfoQueryByBizName;
import com.love.merchant.dto.MerUserAdminBusinessInfoDTO;
import com.love.merchant.dto.MerUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "merchant-service-api", contextId = "merUserBusinessInfoFeignClient", path = "mer/businessInfo")
public interface MerUserBusinessInfoFeignClient {
    @PostMapping("changeBizOrderMgmtEmail")
    Boolean changeBizOrderMgmtEmail(MerUserAdminBusinessInfoChangeEmailBO changeEmailBO);

    @PostMapping("edit")
    Boolean edit(MerUserAdminBusinessInfoEditBO merUserAdminBusinessInfoEditBO);

    @GetMapping("queryByAdminId")
    MerUserAdminBusinessInfoDTO queryByAdminId(@SpringQueryMap MerQueryByAdminIdBO merQueryByAdminIdBO);

    @GetMapping("all")
    List<MerUserAdminBusinessInfoDTO> all();

    @PostMapping("queryByBizName")
    MerUserAdminBusinessInfoDTO queryByBizName(MerUserAdminBusinessInfoQueryByBizName merUserAdminBusinessInfoQueryByBizName);

}
