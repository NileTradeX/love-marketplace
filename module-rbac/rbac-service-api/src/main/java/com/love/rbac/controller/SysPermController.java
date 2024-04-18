package com.love.rbac.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.common.user.IUser;
import com.love.common.user.UserThreadLocal;
import com.love.rbac.bo.SysPermEditBO;
import com.love.rbac.bo.SysPermQueryPageBO;
import com.love.rbac.bo.SysPermSaveBO;
import com.love.rbac.dto.SysPermDTO;
import com.love.rbac.service.SysPermService;
import com.love.rbac.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("sys/perm")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SysPermController {

    private final SysPermService sysPermService;

    private final SysUserService sysUserService;


    @PostMapping("save")
    public Result<SysPermDTO> save(@RequestBody SysPermSaveBO sysPermSaveBO) {
        return Result.success(sysPermService.save(sysPermSaveBO));
    }


    @PostMapping("edit")
    public Result<SysPermDTO> edit(@RequestBody SysPermEditBO sysPermEditBO) {
        return Result.success(sysPermService.edit(sysPermEditBO));
    }


    @GetMapping("queryById")
    public Result<SysPermDTO> queryById(IdParam idParam) {
        return Result.success(sysPermService.queryById(idParam));
    }


    @GetMapping("deleteById")
    public Result<Boolean> deleteById(IdParam id) {
        return Result.success(sysPermService.deleteById(id));
    }


    @GetMapping("page")
    public Result<Pageable<SysPermDTO>> page(SysPermQueryPageBO sysPermQueryPageBO) {
        return Result.success(sysPermService.page(sysPermQueryPageBO));
    }


    @GetMapping("tree")
    public Result<List<SysPermDTO>> tree(IdParam idParam) {
        boolean isSuper = false;
        IUser user = UserThreadLocal.get();
        if (Objects.nonNull(user)) {
            isSuper = sysUserService.isSuper(user.getId());
        }
        return Result.success(sysPermService.tree(idParam.getId(), isSuper));
    }
}
