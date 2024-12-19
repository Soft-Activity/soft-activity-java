package homework.soft.activity.controller;

import homework.soft.activity.util.beans.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "AI", description = "ai助手")
@RestController
@RequestMapping("/ai")
public class AIController {
    @Operation(summary = "ai助手交流")
    @PostMapping("/query")
    public CommonResult<String> ai(@RequestParam("userInput") String userInput) {
        if (userInput == null || userInput.trim().isEmpty()) {
            return CommonResult.error(HttpStatus.BAD_REQUEST, "问题不能为空");
        }
        try {
            String result = homework.web.util.AIHelperUtils.address(userInput);
            if (result == null || result.isEmpty()) {
                return CommonResult.error(HttpStatus.INTERNAL_SERVER_ERROR, "AI助手暂时无法回答");
            }
            return CommonResult.success(result);
        } catch (Exception e) {
            return CommonResult.error(HttpStatus.INTERNAL_SERVER_ERROR, "AI服务异常: " + e.getMessage());
        }
    }
}
