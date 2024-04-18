package com.love.merchant.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.common.Constants;
import com.love.common.enums.ExpireTime;
import com.love.common.enums.YesOrNo;
import com.love.common.exception.BizException;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.util.PageUtil;
import com.love.common.util.PostProcessor;
import com.love.common.util.RedisUtil;
import com.love.common.util.SortUtil;
import com.love.merchant.bo.*;
import com.love.merchant.dto.*;
import com.love.merchant.entity.*;
import com.love.merchant.enums.InvitationStatus;
import com.love.merchant.enums.MerUserType;
import com.love.merchant.mapper.MerUserMapper;
import com.love.merchant.service.*;
import com.love.merchant.util.BCryptUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MerUserServiceImpl extends ServiceImpl<MerUserMapper, MerUser> implements MerUserService, PostProcessor<MerUser, MerUserDTO> {
    private final Logger logger = LoggerFactory.getLogger(MerUserServiceImpl.class);

    private final MerPermService merPermService;
    private final MerRoleService merRoleService;
    private final MerGroupService merGroupService;
    private final MerUserRoleService merUserRoleService;
    private final MerShippingTemplateService merShippingTemplateService;
    private final MerUserAdminPersonalInfoService merUserAdminPersonalInfoService;
    private final MerUserAdminBusinessInfoService merUserAdminBusinessInfoService;

    @Autowired
    private CommissionRateService commissionRateService;

    @Autowired
    private MerUserAdminInvitationService merUserAdminInvitationService;

    @Override
    public void process(MerUser src, MerUserDTO dst) {
        MerUserRole merUserRole = merUserRoleService.queryByUserId(src.getId());
        if (Objects.nonNull(merUserRole)) {
            long roleId = merUserRole.getRoleId();
            dst.setRoleId(roleId);
            MerRoleDTO merRoleDTO = merRoleService.queryById(IdParam.builder().id(roleId).build());
            dst.setRoleName(merRoleDTO.getName());
        }
        dst.setAdmin(src.getType() == MerUserType.ADMIN.getType());

        MerGroup merGroup = merGroupService.queryById(src.getGroupId());
        if (Objects.nonNull(merGroup)) {
            dst.setBizName(merGroup.getName());
        }
    }

    private MerUser checkExists(String account) {
        return this.lambdaQuery().eq(MerUser::getAccount, account).one();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MerUserDTO save(MerUserSaveBO merUserSaveBO) {
        MerUser temp = checkExists(merUserSaveBO.getAccount());
        if (Objects.nonNull(temp)) {
            throw BizException.build("Account already exists");
        }

        MerUser merUser = BeanUtil.copyProperties(merUserSaveBO, MerUser.class);
        merUser.setPassword(BCryptUtils.encode(merUser.getPassword()));
        merUser.setType(MerUserType.NORMAL.getType());
        merUser.setStatus(YesOrNo.YES.getVal());
        merUser.setUid("M" + IdWorker.getIdStr());

        boolean result = this.save(merUser);
        if (result) {
            long roleId = merUserSaveBO.getRoleId();
            MerRoleDTO merRole = merRoleService.queryById(IdParam.builder().id(roleId).build());
            if (Objects.isNull(merRole)) {
                throw BizException.build("role = %s not exist", roleId);
            }

            MerUserRole merUserRole = new MerUserRole();
            merUserRole.setUserId(merUser.getId());
            merUserRole.setRoleId(roleId);
            merUserRoleService.save(merUserRole);
        }
        return BeanUtil.copyProperties(merUser, MerUserDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MerUserDTO saveAdmin(MerUserAdminSaveBO merUserAdminSaveBO) {
        String bizName = merUserAdminSaveBO.getBizName();
        if (merGroupService.checkExists(bizName)) {
            throw BizException.build("group already exist");
        }

        MerUser temp = checkExists(merUserAdminSaveBO.getAccount());
        if (Objects.nonNull(temp)) {
            throw BizException.build("Account already exists");
        }

        if (Objects.isNull(merUserAdminSaveBO.getPersonalInfo())) {
            throw BizException.build("Admin personal info cannot be null");
        }

        if (Objects.isNull(merUserAdminSaveBO.getBusinessInfo())) {
            throw BizException.build("Admin business info cannot be null");
        }

        MerGroup merGroup = new MerGroup();
        merGroup.setName(bizName);
        boolean result = merGroupService.save(merGroup);
        if (result) {
            MerUser merUser = new MerUser();
            merUser.setAccount(merUserAdminSaveBO.getAccount());
            merUser.setPassword(BCryptUtils.encode(merUserAdminSaveBO.getPassword()));
            merUser.setType(MerUserType.ADMIN.getType());
            merUser.setGroupId(merGroup.getId());
            merUser.setStatus(YesOrNo.NO.getVal());
            merUser.setUsername(merUser.getAccount());
            merUser.setUid("M" + IdWorker.getIdStr());

            result = this.save(merUser);
            if (result) {
                MerRoleSaveBO roleSaveBO = new MerRoleSaveBO();
                roleSaveBO.setGroupId(merGroup.getId());
                roleSaveBO.setName("admin");
                MerRoleDTO merRoleDTO = merRoleService.save(roleSaveBO);

                MerUserRole merUserRole = new MerUserRole();
                merUserRole.setUserId(merUser.getId());
                merUserRole.setRoleId(merRoleDTO.getId());
                merUserRoleService.save(merUserRole);

                MerUserAdminPersonalInfoBO personalInfoBO = merUserAdminSaveBO.getPersonalInfo();
                personalInfoBO.setAdminId(merUser.getId());
                merUserAdminPersonalInfoService.save(personalInfoBO);

                MerUserAdminBusinessInfoEditBO businessInfoBO = merUserAdminSaveBO.getBusinessInfo();
                businessInfoBO.setAdminId(merUser.getId());
                merUserAdminBusinessInfoService.save(businessInfoBO);

                MerUserAdminInvitation merUserAdminInvitation = new MerUserAdminInvitation();
                merUserAdminInvitation.setBizName(merUserAdminSaveBO.getBizName());
                merUserAdminInvitation.setStatus(InvitationStatus.WAIT_REVIEW.getStatus());
                merUserAdminInvitationService.updateByBizName(merUserAdminInvitation);

                MerUserAdminInvitation tempInvitation = merUserAdminInvitationService.queryByGroupById(merUser.getGroupId());
                CommissionRate commissionRate = new CommissionRate();
                commissionRate.setBizName(merUserAdminSaveBO.getBizName());
                commissionRate.setAdminId(merUser.getId());
                commissionRate.setEffectiveTime(LocalDateTime.now());
                commissionRate.setRate(tempInvitation.getCommissionFeeRate());
                commissionRateService.saveInner(commissionRate);

                MerUserDTO merUserDTO = BeanUtil.copyProperties(merUser, MerUserDTO.class);
                merUserDTO.setRoleId(merRoleDTO.getId());
                merUserDTO.setRoleName("admin");
                merUserDTO.setAdmin(true);
                return merUserDTO;
            }
        }
        throw BizException.build("register request failed");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MerUserDTO edit(MerUserEditBO merUserEditBO) {
        MerUser merUser = BeanUtil.copyProperties(merUserEditBO, MerUser.class);
        this.updateById(merUser);
        long userId = merUserEditBO.getId();
        Long roleId = merUserEditBO.getRoleId();
        if (Objects.nonNull(roleId)) {
            merUser = this.getById(userId);
            MerUserRole merUserRole = merUserRoleService.queryByUserId(userId);
            if (Objects.nonNull(merUserRole) && merUserRole.getRoleId().longValue() != roleId) {
                merUserRoleService.deleteByUserId(userId);
                MerUserRole temp = new MerUserRole();
                temp.setRoleId(roleId);
                temp.setUserId(userId);
                merUserRoleService.save(temp);
            }
        }
        return BeanUtil.copyProperties(merUser, MerUserDTO.class);
    }

    @Override
    public MerUserDTO editAdmin(MerUserAdminSaveBO merUserAdminSaveBO) {
        if (Objects.isNull(merUserAdminSaveBO.getId())) {
            throw BizException.build("admin invitation id cannot be null");
        }

        MerUserAdminPersonalInfoBO personalInfo = merUserAdminSaveBO.getPersonalInfo();
        if (Objects.nonNull(personalInfo) && Objects.nonNull(personalInfo.getAdminId())) {
            boolean result = merUserAdminPersonalInfoService.updateById(personalInfo);
            if (!result) {
                logger.warn("admin = {} personal info update failure", merUserAdminSaveBO.getId());
            }
        }

        MerUserAdminBusinessInfoEditBO businessInfo = merUserAdminSaveBO.getBusinessInfo();
        if (Objects.nonNull(businessInfo) && Objects.nonNull(businessInfo.getAdminId())) {
            boolean result = merUserAdminBusinessInfoService.updateById(businessInfo);
            if (!result) {
                logger.warn("admin = {} business info update failure", merUserAdminSaveBO.getId());
            }
        }

        Long adminId = merUserAdminSaveBO.getPersonalInfo().getAdminId();
        this.lambdaUpdate().eq(MerUser::getId, adminId)
                .set(MerUser::getUsername, merUserAdminSaveBO.getAccount())
                .set(MerUser::getStatus, YesOrNo.NO.getVal()).update();

        MerUserDTO merUserDTO = new MerUserDTO();
        merUserDTO.setId(adminId);
        merUserDTO.setAccount(merUserAdminSaveBO.getAccount());
        return merUserDTO;
    }

    @Override
    public MerUserDTO queryById(IdParam idParam) {
        MerUser merUser = this.getById(idParam.getId());
        if (Objects.isNull(merUser)) {
            throw BizException.build("user does not exist.");
        }

        MerUserDTO merUserDTO = BeanUtil.copyProperties(merUser, MerUserDTO.class);
        MerUserRole merUserRole = merUserRoleService.queryByUserId(merUser.getId());
        if (Objects.nonNull(merUserRole)) {
            process(merUser, merUserDTO);
        }
        return merUserDTO;
    }

    @Override
    public MerUserAdminDTO queryAdminById(MerUserAdminQueryBO merUserAdminQueryBO) {
        long adminId = merUserAdminQueryBO.getUserId();
        MerUser merUser = this.getById(adminId);
        if (Objects.isNull(merUser)) {
            throw BizException.build("user does not exist.");
        }

        // 替换成admin账号
        if (merUser.getType() != MerUserType.ADMIN.getType()) {
            merUser = this.lambdaQuery().eq(MerUser::getGroupId, merUser.getGroupId()).eq(MerUser::getType, MerUserType.ADMIN.getType()).one();
            adminId = merUser.getId();
        }

        MerUserAdminDTO merUserAdminDTO = BeanUtil.copyProperties(merUser, MerUserAdminDTO.class);
        if (merUserAdminQueryBO.isSimple()) {
            return merUserAdminDTO;
        }

        MerUserAdminPersonalInfo personalInfo = merUserAdminPersonalInfoService.queryById(adminId);
        if (Objects.nonNull(personalInfo)) {
            merUserAdminDTO.setPersonalInfo(BeanUtil.copyProperties(personalInfo, MerUserAdminPersonalInfoDTO.class));
        }

        MerUserAdminBusinessInfoDTO businessInfoDTO = merUserAdminBusinessInfoService.queryById(adminId);
        if (Objects.nonNull(businessInfoDTO)) {
            merUserAdminDTO.setBusinessInfo(businessInfoDTO);
        }

        MerUserAdminInvitation merUserAdminInvitation = merUserAdminInvitationService.queryByGroupById(merUser.getGroupId());
        if (Objects.nonNull(merUserAdminInvitation)) {
            merUserAdminDTO.setMpa(merUserAdminInvitation.getMpa());
        }

        BigDecimal commissionRate = commissionRateService.queryCurrent(adminId);
        if (Objects.nonNull(commissionRate)) {
            merUserAdminDTO.setCommissionFeeRate(commissionRate);
        }

        return merUserAdminDTO;
    }

    @Override
    public Boolean deleteById(IdParam idParam) {
        boolean result = this.removeById(idParam.getId());
        return result && merUserRoleService.deleteByUserId(idParam.getId());
    }

    @Override
    public Pageable<MerUserDTO> page(MerUserQueryPageBO merUserQueryPageBO) {
        if (Objects.isNull(merUserQueryPageBO.getGroupId())) {
            throw BizException.build("group id cannot be null");
        }

        Page<MerUser> page = this.lambdaQuery()
                .eq(MerUser::getGroupId, merUserQueryPageBO.getGroupId())
                .like(StringUtils.isNotBlank(merUserQueryPageBO.getAccount()), MerUser::getAccount, merUserQueryPageBO.getAccount())
                .like(StringUtils.isNotBlank(merUserQueryPageBO.getUsername()), MerUser::getUsername, merUserQueryPageBO.getUsername())
                .page(new Page<>(merUserQueryPageBO.getPageNum(), merUserQueryPageBO.getPageSize()));
        return PageUtil.toPage(page, MerUserDTO.class, this);
    }

    @Override
    public Pageable<MerUserDTO> simpleAdminPage(MerUserQueryAdminPageBO merUserQueryAdminPageBO) {
        Page<MerUser> page = this.lambdaQuery()
                .select(MerUser::getId, MerUser::getAccount, MerUser::getUsername, MerUser::getStatus, MerUser::getType, MerUser::getGroupId)
                .eq(MerUser::getType, MerUserType.ADMIN.getType())
                .eq(Objects.nonNull(merUserQueryAdminPageBO.getStatus()), MerUser::getStatus, merUserQueryAdminPageBO.getStatus())
                .like(StringUtils.isNotBlank(merUserQueryAdminPageBO.getAccount()), MerUser::getAccount, merUserQueryAdminPageBO.getAccount())
                .page(new Page<>(merUserQueryAdminPageBO.getPageNum(), merUserQueryAdminPageBO.getPageSize()));
        return PageUtil.toPage(page, MerUserDTO.class, this);
    }

    @Override
    public Pageable<MerUserAdminDTO> fullAdminPage(MerUserQueryAdminPageBO merUserQueryAdminPageBO) {
        QueryChainWrapper<MerUser> queryChainWrapper = this.query()
                .eq("type", MerUserType.ADMIN.getType())
                .eq(Objects.nonNull(merUserQueryAdminPageBO.getId()), "id", merUserQueryAdminPageBO.getId())
                .eq(Objects.nonNull(merUserQueryAdminPageBO.getStatus()), "status", merUserQueryAdminPageBO.getStatus())
                .like(StringUtils.isNotBlank(merUserQueryAdminPageBO.getAccount()), "account", merUserQueryAdminPageBO.getAccount());

        if (Objects.nonNull(merUserQueryAdminPageBO.getBeginTime()) && Objects.nonNull(merUserQueryAdminPageBO.getEndTime())) {
            queryChainWrapper.between("create_time", merUserQueryAdminPageBO.getBeginTime(), merUserQueryAdminPageBO.getEndTime());
        }

        if (Objects.nonNull(merUserQueryAdminPageBO.getSort())) {
            SortUtil sortUtil = new SortUtil(merUserQueryAdminPageBO.getSort());
            queryChainWrapper.orderBy(true, sortUtil.isAsc(), sortUtil.getSortField());
        } else {
            queryChainWrapper.orderBy(true, false, "create_time");
        }

        Page<MerUser> page = queryChainWrapper.page(new Page<>(merUserQueryAdminPageBO.getPageNum(), merUserQueryAdminPageBO.getPageSize()));

        return PageUtil.toPage(page, MerUserAdminDTO.class, (src, dst) -> {
            MerUserAdminPersonalInfo personalInfo = merUserAdminPersonalInfoService.queryById(src.getId());
            if (Objects.nonNull(personalInfo)) {
                dst.setPersonalInfo(BeanUtil.copyProperties(personalInfo, MerUserAdminPersonalInfoDTO.class));
            }

            MerUserAdminBusinessInfoDTO merUserAdminBusinessInfoDTO = merUserAdminBusinessInfoService.queryById(src.getId());
            if (Objects.nonNull(merUserAdminBusinessInfoDTO)) {
                dst.setBusinessInfo(merUserAdminBusinessInfoDTO);
            }
        });
    }

    public List<Long> queryApprovedList(MerUserQueryBO merUserQueryBO) {
        return this.lambdaQuery().select(MerUser::getId).eq(Objects.nonNull(merUserQueryBO.getStatus()), MerUser::getStatus, merUserQueryBO.getStatus()).list()
                .stream().map(MerUser::getId).collect(Collectors.toList());
    }

    @Override
    public boolean changePassword(MerUserChangePasswordBO merUserChangePasswordBO) {
        String encodePassword = BCryptUtils.encode(merUserChangePasswordBO.getPassword());
        return this.lambdaUpdate().set(MerUser::getPassword, encodePassword).eq(MerUser::getId, merUserChangePasswordBO.getId()).update();
    }

    @Override
    public boolean resetPassword(MerUserResetPasswordBO merUserResetPasswordBO) {
        return this.lambdaUpdate().set(MerUser::getPassword, BCryptUtils.encode(merUserResetPasswordBO.getPassword())).eq(MerUser::getId, merUserResetPasswordBO.getId()).update();
    }

    /**
     * 1. Read user information from the user account
     * 2. Verify password
     * 3. Update login time
     * 4. Generate permission data
     *
     * @param merUserLoginBO system user login business object
     * @return login result
     */
    @Override
    public LoginUserDTO login(MerUserLoginBO merUserLoginBO) {
        MerUser tmp = this.lambdaQuery().eq(MerUser::getAccount, merUserLoginBO.getAccount()).one();
        if (Objects.isNull(tmp) || Objects.isNull(tmp.getId())) {
            throw BizException.build("Account : %s does not exist.", merUserLoginBO.getAccount());
        }

        if (!BCryptUtils.matches(merUserLoginBO.getPassword(), tmp.getPassword())) {
            throw BizException.build("Authentication failed. Please check your username and password and try again.");
        }

        if (MerUserType.ADMIN.getType() == tmp.getType() && tmp.getStatus() != InvitationStatus.APPROVE.getStatus()) {
            throw BizException.build("Authentication failed. Please login after account be actived");
        }

        List<MerPermDTO> merPermDTOS;
        if (MerUserType.ADMIN.getType() == tmp.getType()) {
            merPermDTOS = merPermService.tree(null, true);
        } else {
            MerUserRole merUserRole = merUserRoleService.queryByUserId(tmp.getId());
            if (Objects.nonNull(merUserRole)) {
                merPermDTOS = merPermService.tree(merUserRole.getRoleId(), false);
            } else {
                merPermDTOS = new ArrayList<>();
            }
        }
        tmp.setLastLoginTime(LocalDateTime.now());
        this.updateById(tmp);

        MerUserDTO merUserDTO = BeanUtil.copyProperties(tmp, MerUserDTO.class);
        this.process(tmp, merUserDTO);
        return new LoginUserDTO(merUserDTO, merPermDTOS);
    }

    @Override
    public boolean isAdmin(Long userId) {
        if (Objects.isNull(userId)) {
            return false;
        }
        MerUser merUser = this.getById(userId);
        return Objects.nonNull(merUser) && merUser.getType() == MerUserType.ADMIN.getType();
    }

    @Override
    public long queryGroupId(Long adminId) {
        MerUser admin = this.getById(adminId);
        if (Objects.nonNull(admin)) {
            return admin.getGroupId();
        }
        throw BizException.build("MerUser = %s is not exist", adminId);
    }

    @Override
    public MerUser queryAdminByGroupId(Long groupId) {
        return this.lambdaQuery().eq(MerUser::getGroupId, groupId).eq(MerUser::getType, MerUserType.ADMIN.getType()).one();
    }

    @Override
    public MerchantRegInfo queryMerchantRegInfo(String bizName) {
        return this.baseMapper.queryMerchantRegInfo(bizName);
    }

    @Override
    public MerUserDTO queryByAccount(MerUserQueryByAccountBO merUserQueryByAccountBO) {
        MerUser merUser = this.lambdaQuery().eq(MerUser::getAccount, merUserQueryByAccountBO.getAccount()).one();
        if (Objects.isNull(merUser)) {
            throw BizException.build("MerUser =%s is not exist", merUserQueryByAccountBO.getAccount());
        }
        return BeanUtil.copyProperties(merUser, MerUserDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MerUserAdminApproveDTO approve(MerUserAdminApproveBO merUserAdminApproveBO) {
        boolean result = this.lambdaUpdate().eq(MerUser::getId, merUserAdminApproveBO.getId()).eq(MerUser::getStatus, YesOrNo.NO.getVal()).set(MerUser::getStatus, InvitationStatus.APPROVE.getStatus()).update();
        if (result) {
            MerShippingTemplateBO merShippingTemplate = new MerShippingTemplateBO();
            merShippingTemplate.setMerchantId(merUserAdminApproveBO.getId());
            merShippingTemplate.setShippingModels(2);
            merShippingTemplateService.save(merShippingTemplate);

            Map<String, Object> map = this.baseMapper.queryInviteCodeById(merUserAdminApproveBO.getId());
            return BeanUtil.copyProperties(map, MerUserAdminApproveDTO.class);
        }
        throw BizException.build("approve failed");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MerUserAdminRejectDTO reject(MerUserAdminRejectBO merUserAdminRejectBO) {
        boolean result = this.lambdaUpdate().eq(MerUser::getId, merUserAdminRejectBO.getId()).eq(MerUser::getStatus, YesOrNo.NO.getVal()).set(MerUser::getStatus, InvitationStatus.REJECT.getStatus()).set(MerUser::getReason, merUserAdminRejectBO.getReason()).update();
        if (result) {
            Map<String, Object> map = this.baseMapper.queryInviteCodeById(merUserAdminRejectBO.getId());
            return BeanUtil.copyProperties(map, MerUserAdminRejectDTO.class);
        }
        throw BizException.build("reject failed");
    }

    @Override
    public MerUserAdminReviewStatDTO reviewStat(MerUserAdminReviewStatBO merUserAdminReviewStatBO) {
        MerUserAdminReviewStatDTO merUserAdminReviewStatDTO = new MerUserAdminReviewStatDTO();
        QueryWrapper<MerUser> queryWrapper = Wrappers.query();
        queryWrapper.select("status,count(status) as count").groupBy("status");
        List<Map<String, Object>> list = this.listMaps(queryWrapper);
        if (list.size() > 0) {
            list.forEach(m -> {
                Integer status = ( Integer ) m.get("status");
                Long count = ( Long ) m.get("count");
                if (status == YesOrNo.NO.getVal()) {
                    merUserAdminReviewStatDTO.setAwaiting(count);
                } else if (status == InvitationStatus.APPROVE.getStatus()) {
                    merUserAdminReviewStatDTO.setApproved(count);
                } else if (status == InvitationStatus.REJECT.getStatus()) {
                    merUserAdminReviewStatDTO.setRejected(count);
                }
            });
        }
        return merUserAdminReviewStatDTO;
    }
}
