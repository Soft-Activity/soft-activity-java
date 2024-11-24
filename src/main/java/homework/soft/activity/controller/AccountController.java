package homework.soft.activity.controller;

import homework.soft.activity.entity.dto.AccountQuery;
import homework.soft.activity.entity.po.Account;
import homework.soft.activity.entity.vo.AccountVO;
import homework.soft.activity.service.AccountService;
import homework.soft.activity.util.beans.CommonResult;
import homework.soft.activity.util.beans.ListResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 账号表(Account)表控制层
 *
 * @author jscomet
 * @since 2024-11-24 09:35:29
 */
@Tag(name = "Account", description = "账号表")
@RestController
@RequestMapping("/account")
public class AccountController {
    @Resource
    private AccountService accountService;

    @Operation(summary = "获取指定账号表信息")
    @GetMapping("/info/{id}")
    public CommonResult<AccountVO> getAccount(@PathVariable String id) {
        AccountVO vo = accountService.queryById(id);
        return vo != null ? CommonResult.success(vo) : CommonResult.error(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "获取账号表列表")
    @GetMapping("/list")
    public CommonResult<ListResult<AccountVO>> getAccounts(@RequestParam(defaultValue = "1") Integer current, @RequestParam(defaultValue = "10") Integer pageSize,
            AccountQuery param) {
        List<AccountVO> list = accountService.queryAll(current, pageSize, param);
        int total = accountService.count(param);
        return CommonResult.success(new ListResult<>(list, total));
    }

    @Operation(summary = "添加账号表")
    @PostMapping("/add")
    public CommonResult<Boolean> addAccount(@RequestBody Account param) {
        return accountService.save(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "修改指定账号表信息")
    @PutMapping("/update/{id}")
    public CommonResult<Boolean> updateAccount(@PathVariable String id,
            @RequestBody Account param) {
            param.setUserId(id);
        return accountService.updateById(param) ? CommonResult.success(true) : CommonResult.error(HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "删除指定账号表")
    @DeleteMapping("/delete/{id}")
    public CommonResult<Boolean> deleteAccount(@PathVariable String id) {
        return accountService.removeById(id) ? CommonResult.success(true) : CommonResult.error(HttpStatus.NOT_FOUND);
    }
}
