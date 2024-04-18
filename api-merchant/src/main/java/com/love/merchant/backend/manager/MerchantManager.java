package com.love.merchant.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.love.api.common.util.JwtUtil;
import com.love.common.Constants;
import com.love.common.enums.YesOrNo;
import com.love.common.exception.BizException;
import com.love.common.util.GsonUtil;
import com.love.common.util.RedisUtil;
import com.love.goods.bo.BrandBatchSaveBO;
import com.love.goods.bo.BrandQueryListBO;
import com.love.goods.bo.BrandSaveBO;
import com.love.goods.bo.BrandUpdateStatusBO;
import com.love.goods.client.BrandFeignClient;
import com.love.goods.dto.BrandDTO;
import com.love.merchant.backend.model.param.*;
import com.love.merchant.backend.model.vo.MerUserAdminFullVO;
import com.love.merchant.backend.model.vo.MerUserAdminInvitationVO;
import com.love.merchant.backend.model.vo.MerUserAdminVO;
import com.love.merchant.bo.*;
import com.love.merchant.client.MerUserAdminInvitationFeignClient;
import com.love.merchant.client.MerUserBusinessInfoFeignClient;
import com.love.merchant.client.MerchantUserFeignClient;
import com.love.merchant.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerchantManager {

    private final Logger logger = LoggerFactory.getLogger(MerchantManager.class);

    private final RedisUtil redisUtil;
    private final BrandFeignClient brandFeignClient;
    private final MerchantUserFeignClient merchantUserFeignClient;
    private final MerUserAdminInvitationFeignClient merUserAdminInvitationFeignClient;
    private final MerUserBusinessInfoFeignClient merUserBusinessInfoFeignClient;
    private final MerchantIdManager merchantIdManager;

    public MerUserAdminVO register(MerUserAdminSaveParam merUserAdminSaveParam) {
        Object val = redisUtil.get(Constants.MER_USER_INVITATION + merUserAdminSaveParam.getCode());
        if (Objects.isNull(val)) {
            throw BizException.build("invitation is not exist");
        }

        MerUserAdminSaveBO merUserAdminSaveBO = BeanUtil.copyProperties(merUserAdminSaveParam, MerUserAdminSaveBO.class);
        MerUserDTO merUserDTO;
        if (Objects.nonNull(merUserAdminSaveParam.getId())) {
            merUserDTO = merchantUserFeignClient.editAdmin(merUserAdminSaveBO);
        } else {
            if (Objects.isNull(merUserAdminSaveBO.getPassword())) {
                throw BizException.build("password cannot be null");
            }
            merUserDTO = merchantUserFeignClient.saveAdmin(merUserAdminSaveBO);
        }

        if (Objects.nonNull(merUserDTO) && Objects.nonNull(merUserDTO.getId())) {
            List<MerUserAdminSaveParam.Brand> brands = merUserAdminSaveParam.getBrands();
            if (CollectionUtil.isNotEmpty(brands)) {
                List<BrandSaveBO> brandSaveBOS = BeanUtil.copyToList(brands, BrandSaveBO.class);
                brandSaveBOS.forEach(i -> i.setMerchantId(merUserDTO.getId()));
                boolean result = brandFeignClient.saveBatch(new BrandBatchSaveBO(brandSaveBOS));
                if (!result) {
                    logger.error("===> save brands error ! data = {}", GsonUtil.bean2json(brandSaveBOS));
                }
            }

            // Approve account immediately
            MerUserAdminApproveDTO result = merchantUserFeignClient.approve(BeanUtil.copyProperties(merUserDTO, MerUserAdminApproveBO.class));
            if (Objects.nonNull(result)) {
                redisUtil.del(Constants.MER_USER_INVITATION + result.getCode());

                // Enable any created brands
                BrandUpdateStatusBO brandUpdateStatusBO = new BrandUpdateStatusBO();
                brandUpdateStatusBO.setMerchantId(merUserDTO.getId());
                brandFeignClient.enabled(brandUpdateStatusBO);
            }

            return BeanUtil.copyProperties(merUserDTO, MerUserAdminVO.class);
        }

        throw BizException.build("merchant register request failed");
    }


    public boolean validateToken(TokenParam tokenParam) {
        try {
            JwtUtil.verifyJwt(tokenParam.getToken());
        } catch (Exception exception) {
            throw BizException.build("Token invalid");
        }
        return true;
    }

    public MerUserAdminFullVO detail(MerUserAdminIdParam merUserAdminIdParam) {
        MerUserAdminDTO merUserAdmin = merchantUserFeignClient.queryAdminById(MerUserAdminQueryBO.builder().userId(merUserAdminIdParam.getUserId()).build());
        MerUserAdminFullVO merUserAdminFullVO = BeanUtil.copyProperties(merUserAdmin, MerUserAdminFullVO.class);
        List<BrandDTO> brandDTOS = brandFeignClient.queryByMerchantId(BrandQueryListBO.builder().merchantId(merUserAdminFullVO.getId()).build());
        merUserAdminFullVO.setBrands(BeanUtil.copyToList(brandDTOS, MerUserAdminFullVO.Brand.class));
        return merUserAdminFullVO;
    }

    public MerUserAdminFullVO.MerUserAdminBusinessInfoVO businessInfo(MerUserAdminIdParam merUserAdminIdParam) {
        Long adminId = merchantIdManager.getMerchantId(merUserAdminIdParam.getUserId());
        MerUserAdminBusinessInfoDTO merUserAdminBusinessInfoDTO = merUserBusinessInfoFeignClient.queryByAdminId(MerQueryByAdminIdBO.builder().adminId(adminId).build());

        return BeanUtil.copyProperties(merUserAdminBusinessInfoDTO, MerUserAdminFullVO.MerUserAdminBusinessInfoVO.class);
    }

    public MerUserAdminInvitationVO queryByCode(MerUserAdminInvitationQueryByCodeParam merUserAdminInvitationQueryByCodeParam) {
        Object val = redisUtil.get(Constants.MER_USER_INVITATION + merUserAdminInvitationQueryByCodeParam.getCode());
        if (Objects.isNull(val)) {
            throw BizException.build("invitation is not exist");
        }

        MerUserAdminInvitationQueryByCodeBO queryByCodeBO = BeanUtil.copyProperties(merUserAdminInvitationQueryByCodeParam, MerUserAdminInvitationQueryByCodeBO.class);
        MerUserAdminInvitationDTO merUserAdminInvitationDTO = merUserAdminInvitationFeignClient.queryByCode(queryByCodeBO);
        if (merUserAdminInvitationDTO.getStatus() == YesOrNo.NO.getVal()) {
            throw BizException.build("account is under review now");
        }

        MerUserAdminInvitationVO merUserAdminInvitationVO = BeanUtil.copyProperties(merUserAdminInvitationDTO, MerUserAdminInvitationVO.class);
        if (Objects.nonNull(merUserAdminInvitationDTO.getAdminInfo())) {
            List<BrandDTO> brandDTOS = brandFeignClient.queryByMerchantId(BrandQueryListBO.builder().merchantId(merUserAdminInvitationDTO.getAdminInfo().getId()).build());
            merUserAdminInvitationVO.getAdminInfo().setBrands(BeanUtil.copyToList(brandDTOS, MerUserAdminFullVO.Brand.class));
        }
        return merUserAdminInvitationVO;
    }

    public Boolean changeBizOrderMgmtEmail(MerChangeBizOrderMgmtEmailParam merChangeBizOrderMgmtEmailParam) {
        MerUserAdminBusinessInfoChangeEmailBO changeEmailBO = BeanUtil.copyProperties(merChangeBizOrderMgmtEmailParam, MerUserAdminBusinessInfoChangeEmailBO.class);
        return merUserBusinessInfoFeignClient.changeBizOrderMgmtEmail(changeEmailBO);
    }

    public Boolean changeDefaultCarrier(MerChangeDefaultCarrierParam merChangeDefaultCarrierParam) {
        MerUserAdminBusinessInfoEditBO merUserAdminBusinessInfoEditBO = BeanUtil.copyProperties(merChangeDefaultCarrierParam, MerUserAdminBusinessInfoEditBO.class);
        Long adminId = merchantIdManager.getMerchantId(merChangeDefaultCarrierParam.getUserId());
        merUserAdminBusinessInfoEditBO.setAdminId(adminId);
        return merUserBusinessInfoFeignClient.edit(merUserAdminBusinessInfoEditBO);
    }

}
