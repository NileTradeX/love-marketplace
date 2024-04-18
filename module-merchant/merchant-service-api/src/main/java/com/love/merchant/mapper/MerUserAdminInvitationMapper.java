package com.love.merchant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.merchant.entity.MerUserAdminInvitation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MerUserAdminInvitationMapper extends BaseMapper<MerUserAdminInvitation> {
    @Select("select muai.id, muai.biz_name,muai.commission_fee_rate,muai.mpa from mer_user_admin_invitation muai left join mer_group mg on mg.name = muai.biz_name where mg.id = #{groupId}")
    MerUserAdminInvitation queryByGroupId(@Param("groupId") Long groupId);
}
