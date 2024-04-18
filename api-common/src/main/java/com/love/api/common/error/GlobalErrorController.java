package com.love.api.common.error;

import com.love.common.result.Result;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GlobalErrorController extends AbstractErrorController {

    public GlobalErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping
    public Result<String> error(HttpServletRequest request) {
        Map<String, Object> map = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        return Result.fail(String.valueOf(getStatus(request).value()), map.get("error").toString());
    }
}
