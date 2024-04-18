package com.love.merchant.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.love.merchant.entity.MerUser;
import com.love.merchant.entity.MerchantRegInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

public interface MerUserMapper extends BaseMapper<MerUser> {

    @Select("select mg.id group_id, mg.name biz_name, muai.code, muai.status, muai.email,mu.id merchant_id from mer_group mg right join mer_user_admin_invitation muai on mg.name = muai.biz_name right join mer_user mu on mg.id = mu.group_id where mg.name = #{bizName}")
    MerchantRegInfo queryMerchantRegInfo(@Param("bizName") String bizName);

    @Select("select muai.code code, muai.email email, mg.name bizName from love.mer_user_admin_invitation muai left join love.mer_group mg on muai.biz_name = mg.name left join love.mer_user mu on mu.group_id = mg.id where mu.id = #{merchantId}")
    Map<String, Object> queryInviteCodeById(@Param("merchantId") Long merchantId);
}
