package lynn.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import lynn.util.enums.FailureEnum;
import lynn.util.result.Result;
import lynn.util.result.ResultUtil;

/**
 */
public abstract class BaseController {
    protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * 返回参数错误
     *
     * @param result
     * @return
     */
    protected Result paramError(BindingResult result) {
        StringBuffer sb = new StringBuffer();
        for (ObjectError error : result.getAllErrors()) {
            sb.append(error.getDefaultMessage());
        }
        return ResultUtil.failure(FailureEnum.PARAMETER_FAILURE, sb.toString());
    }

}