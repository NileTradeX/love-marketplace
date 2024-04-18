package com.love.marketplace.controller;

import com.love.common.result.Result;
import com.love.influencer.bo.InfStoreQueryByDisplayNameBO;
import com.love.influencer.dto.InfStoreDTO;
import com.love.marketplace.manager.InfStoreManager;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("influencer/store")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "InfluencerStoreApi", description = "all Influencer Store manage operation")
public class InfStoreController {

    private final InfStoreManager infStoreManager;

    @GetMapping("displayName")
    public Result<InfStoreDTO> queryByDisplayName(InfStoreQueryByDisplayNameBO infStoreQueryByDisplayNameBO, HttpServletRequest request) {
        return Result.success(infStoreManager.queryByDisplayName(infStoreQueryByDisplayNameBO, request));
    }
}
