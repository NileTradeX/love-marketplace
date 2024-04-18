package com.love.backend.manager;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.love.api.common.util.JwtUtil;
import com.love.backend.model.param.*;
import com.love.backend.model.vo.*;
import com.love.common.Constants;
import com.love.common.bo.MerchantInvitationEmailSendBO;
import com.love.common.bo.MerchantReviewApproveEmailSendBO;
import com.love.common.bo.MerchantReviewRejectEmailSendBO;
import com.love.common.client.EmailSendFeignClient;
import com.love.common.dto.EmailSendDTO;
import com.love.common.enums.ExpireTime;
import com.love.common.enums.YesOrNo;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageableUtil;
import com.love.common.util.RedisUtil;
import com.love.goods.bo.BrandQueryListBO;
import com.love.goods.bo.BrandUpdateStatusBO;
import com.love.goods.client.BrandFeignClient;
import com.love.goods.dto.BrandDTO;
import com.love.merchant.bo.*;
import com.love.merchant.client.CommissionRateFeignClient;
import com.love.merchant.client.MerUserAdminInvitationFeignClient;
import com.love.merchant.client.MerchantUserFeignClient;
import com.love.merchant.dto.*;
import com.love.merchant.enums.InvitationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerchantManager {

    private final RedisUtil redisUtil;
    private final BrandFeignClient brandFeignClient;
    private final MerchantUserFeignClient merchantUserFeignClient;
    private final EmailSendFeignClient emailSendFeignClient;
    private final CommissionRateFeignClient commissionRateFeignClient;
    private final MerUserAdminInvitationFeignClient merUserAdminInvitationFeignClient;

    @Value("${app.merchant.register-url}")
    private String merchantRegisterUrl;

    @Value("${app.merchant.login-url}")
    private String merchantLoginUrl;

    private EmailSendDTO sendInviteEmail(String email, String bizName, String code) {
        MerchantInvitationEmailSendBO invitationEmailSendBO = MerchantInvitationEmailSendBO.builder()
                .toEmail(email)
                .bizName(bizName)
                .inviteUrl(merchantRegisterUrl + code + "&token=" + JwtUtil.createJwt(Constants.FAKE_MAP))
                .build();
        return emailSendFeignClient.sendMerchantInvitationEmail(invitationEmailSendBO);
    }

    @Async
    public void sendMerchantApproveEmail(String email, String bizName) {
        MerchantReviewApproveEmailSendBO reviewApproveEmailSendBO = MerchantReviewApproveEmailSendBO.builder()
                .toEmail(email)
                .bizName(bizName)
                .loginUrl(merchantLoginUrl)
                .build();
        emailSendFeignClient.sendMerchantReviewApproveEmail(reviewApproveEmailSendBO);
    }

    @Async
    public void sendMerchantRejectEmail(String email, String bizName, String code, String reason) {
        MerchantReviewRejectEmailSendBO reviewApproveEmailSendBO = MerchantReviewRejectEmailSendBO.builder()
                .toEmail(email)
                .bizName(bizName)
                .reason(reason)
                .registerUrl(merchantRegisterUrl + code + "&token=" + JwtUtil.createJwt(Constants.FAKE_MAP))
                .build();
        emailSendFeignClient.sendMerchantReviewRejectEmail(reviewApproveEmailSendBO);
    }

    public MerUserAdminInvitationVO send(MerUserAdminInvitationSaveParam merUserAdminInvitationSaveParam) {
        MerUserAdminInvitationSaveBO merUserAdminInvitationSaveBO = BeanUtil.copyProperties(merUserAdminInvitationSaveParam, MerUserAdminInvitationSaveBO.class);
        MerUserAdminInvitationDTO merUserAdminInvitationDTO = merUserAdminInvitationFeignClient.save(merUserAdminInvitationSaveBO);
        if (Objects.nonNull(merUserAdminInvitationDTO)) {
            String email = merUserAdminInvitationSaveParam.getEmail();
            EmailSendDTO emailSendDTO = this.sendInviteEmail(email, merUserAdminInvitationDTO.getBizName(), merUserAdminInvitationDTO.getCode());
            if (Objects.nonNull(emailSendDTO)) {
                redisUtil.set(Constants.MER_USER_INVITATION + merUserAdminInvitationDTO.getCode(), YesOrNo.YES.getVal(), ExpireTime.ONE_WEEK.getTime());

                MerUserAdminInvitationEditBO merUserInvitationEditBO = new MerUserAdminInvitationEditBO();
                merUserInvitationEditBO.setId(merUserAdminInvitationDTO.getId());
                merUserInvitationEditBO.setStatus(InvitationStatus.WAIT_SUBMIT.getStatus());
                merUserAdminInvitationFeignClient.edit(merUserInvitationEditBO);
            } else {
                throw BizException.build("send merchant invite email fail, code=%s", merUserAdminInvitationDTO.getCode());
            }
        }
        return BeanUtil.copyProperties(merUserAdminInvitationDTO, MerUserAdminInvitationVO.class);
    }

    public Boolean resend(MerUserAdminInvitationEditParam merUserAdminInvitationEditParam) {
        MerUserAdminInvitationDTO merUserAdminInvitationDTO = merUserAdminInvitationFeignClient.detail(IdParam.builder().id(merUserAdminInvitationEditParam.getId()).build());
        if (Objects.isNull(merUserAdminInvitationDTO)) {
            throw BizException.build("invitation is not exist , id=%s", merUserAdminInvitationEditParam.getId());
        }

        String email = merUserAdminInvitationEditParam.getEmail();
        EmailSendDTO emailSendDTO = this.sendInviteEmail(email, merUserAdminInvitationDTO.getBizName(), merUserAdminInvitationDTO.getCode());
        if (Objects.nonNull(emailSendDTO)) {
            redisUtil.set(Constants.MER_USER_INVITATION + merUserAdminInvitationDTO.getCode(), YesOrNo.YES.getVal(), ExpireTime.ONE_WEEK.getTime());

            MerUserAdminInvitationEditBO merUserInvitationEditBO = new MerUserAdminInvitationEditBO();
            merUserInvitationEditBO.setId(merUserAdminInvitationDTO.getId());
            merUserInvitationEditBO.setEmail(email.equals(merUserAdminInvitationDTO.getEmail()) ? null : email);
            merUserInvitationEditBO.setStatus(InvitationStatus.WAIT_SUBMIT.getStatus());
            merUserAdminInvitationFeignClient.edit(merUserInvitationEditBO);
        } else {
            throw BizException.build("send merchant invite email fail, code= %s", merUserAdminInvitationDTO.getCode());
        }
        return false;
    }

    public Pageable<MerUserAdminInvitationVO> page(MerUserAdminInvitationQueryPageParam merUserInvitationQueryPageParam) {
        MerUserAdminInvitationQueryPageBO merUserInvitationQueryPageBO = BeanUtil.copyProperties(merUserInvitationQueryPageParam, MerUserAdminInvitationQueryPageBO.class);
        Pageable<MerUserAdminInvitationDTO> pageable = merUserAdminInvitationFeignClient.page(merUserInvitationQueryPageBO);
        return PageableUtil.toPage(pageable, MerUserAdminInvitationVO.class);
    }

    public Pageable<MerUserAdminSimpleVO> simpleAdminPage(MerUserAdminSimpleQueryPageParam merUserAdminQueryPageParam) {
        MerUserQueryAdminPageBO merUserQueryPageBO = BeanUtil.copyProperties(merUserAdminQueryPageParam, MerUserQueryAdminPageBO.class);
        merUserQueryPageBO.setStatus(InvitationStatus.APPROVE.getStatus());
        Pageable<MerUserDTO> pageable = merchantUserFeignClient.simpleAdminPage(merUserQueryPageBO);
        return PageableUtil.toPage(pageable, MerUserAdminSimpleVO.class);
    }

    public Pageable<MerUserAdminFullVO> fullAdminPage(MerUserAdminFullQueryPageParam merUserAdminFullQueryPageParam) {
        MerUserQueryAdminPageBO merUserQueryPageBO = BeanUtil.copyProperties(merUserAdminFullQueryPageParam, MerUserQueryAdminPageBO.class);
        Pageable<MerUserAdminDTO> pageable = merchantUserFeignClient.fullAdminPage(merUserQueryPageBO);
        return PageableUtil.toPage(pageable, MerUserAdminFullVO.class, (src, dst) -> {
            List<BrandDTO> brands = brandFeignClient.queryByMerchantId(BrandQueryListBO.builder().merchantId(src.getId()).build());
            if (CollectionUtil.isNotEmpty(brands)) {
                dst.setBrands(BeanUtil.copyToList(brands, BrandVO.class));
            }
        });
    }

    public MerUserAdminFullVO detail(IdParam idParam) {
        MerUserAdminFullVO merUserAdminFullVO = BeanUtil.copyProperties(merchantUserFeignClient.queryAdminById(MerUserAdminQueryBO.builder().userId(idParam.getId()).build()), MerUserAdminFullVO.class);
        if (Objects.isNull(merUserAdminFullVO.getBrands())) {
            List<BrandDTO> brands = brandFeignClient.queryByMerchantId(BrandQueryListBO.builder().merchantId(idParam.getId()).build());
            if (CollectionUtil.isNotEmpty(brands)) {
                merUserAdminFullVO.setBrands(BeanUtil.copyToList(brands, BrandVO.class));
            }
        }
        return merUserAdminFullVO;
    }

    public MerUserAdminReviewStatVO reviewStat(MerUserAdminReviewStatParam merUserAdminReviewStatParam) {
        return BeanUtil.copyProperties(merchantUserFeignClient.reviewStat(BeanUtil.copyProperties(merUserAdminReviewStatParam, MerUserAdminReviewStatBO.class)), MerUserAdminReviewStatVO.class);
    }

    public MerUserAdminApproveDTO approve(MerUserAdminApproveParam merUserAdminApproveParam) {
        MerUserAdminApproveDTO result = merchantUserFeignClient.approve(BeanUtil.copyProperties(merUserAdminApproveParam, MerUserAdminApproveBO.class));
        if (Objects.nonNull(result)) {
            redisUtil.del(Constants.MER_USER_INVITATION + result.getCode());
            this.sendMerchantApproveEmail(result.getEmail(), result.getBizName());

            // enabled brand
            BrandUpdateStatusBO brandUpdateStatusBO = new BrandUpdateStatusBO();
            brandUpdateStatusBO.setMerchantId(merUserAdminApproveParam.getId());
            brandFeignClient.enabled(brandUpdateStatusBO);
        }
        return result;
    }

    public MerUserAdminRejectDTO reject(MerUserAdminRejectParam merUserAdminRejectParam) {
        MerUserAdminRejectDTO result = merchantUserFeignClient.reject(BeanUtil.copyProperties(merUserAdminRejectParam, MerUserAdminRejectBO.class));
        if (Objects.nonNull(result)) {
            this.sendMerchantRejectEmail(result.getEmail(), result.getBizName(), result.getCode(), merUserAdminRejectParam.getReason());
        }
        return result;
    }

    public Pageable<CommissionRateVO> feeRatePage(CommissionRateQueryPageParam commissionRateQueryPageParam) {
        CommissionRateQueryPageBO commissionRateQueryPageBO = BeanUtil.copyProperties(commissionRateQueryPageParam, CommissionRateQueryPageBO.class);
        Pageable<CommissionRateDTO> pageable = commissionRateFeignClient.page(commissionRateQueryPageBO);
        return PageableUtil.toPage(pageable, CommissionRateVO.class, (src, dst) -> {
            List<BrandDTO> brands = brandFeignClient.queryByMerchantId(BrandQueryListBO.builder().merchantId(src.getMerchantId()).build());
            if (CollectionUtil.isNotEmpty(brands)) {
                dst.setBrands(BeanUtil.copyToList(brands, CommissionRateVO.Brand.class));
            }
        });
    }

    public Boolean feeRateEdit(CommissionRateSaveParam commissionRateSaveParam) {
        CommissionRateSaveBO commissionRateSaveBO = BeanUtil.copyProperties(commissionRateSaveParam, CommissionRateSaveBO.class);
        commissionRateSaveBO.setAdminId(commissionRateSaveParam.getMerchantId());
        return commissionRateFeignClient.save(commissionRateSaveBO);
    }
}
