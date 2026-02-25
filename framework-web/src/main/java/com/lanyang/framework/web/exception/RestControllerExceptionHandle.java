package com.lanyang.framework.web.exception;

import com.lanyang.framework.web.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

/**
 * @author lanyang
 * @date 2025/3/27
 * @des 只做基础的兜底拦截，各个系统自定义统一异常处理
 * 系统不自定义异常处理时，会走这里的逻辑
 */
@RestControllerAdvice
@Slf4j
public class RestControllerExceptionHandle {

    /**
     * 数据验证异常
     * */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handle(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        if (!CollectionUtils.isEmpty(fieldErrorList)){
            Optional<FieldError> optional = fieldErrorList.stream().findFirst();
            if(optional.isPresent()){
                return R.fail(optional.get().getDefaultMessage());
            }
        }
        return R.fail(e.getMessage());
    }

    @ExceptionHandler(value = ServiceException.class)
    public R handle(ServiceException e){
        log.info("业务异常统一处理:" + e.getMessage());
        log.error(e.getMessage(), e);
        return R.fail(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = BizException.class)
    public R handle(BizException e){
        log.info("业务异常统一处理:" + e.getMessage());
        log.error(e.getMessage(), e);
        return R.fail(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public R handleRuntimeException(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return R.fail(e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public R handle(Exception e){
        log.info("全局异常统一处理:" + e.getMessage());
        log.error(e.getMessage(), e);
        return R.fail( e.getMessage());
    }

//    /**
//     * 处理数据持久化层异常
//     * */
//    @ExceptionHandler(value = DataAccessException.class)
//    public RestResponse handle(DataAccessException e){
//        log.error(e.getMessage(), e);
//        return RestResponse.error("数据处理错误");
//    }
//
//    /**
//     * 处理数据持久化层异常
//     * */
//    @ExceptionHandler(value = DuplicateKeyException.class)
//    public RestResponse handle(DuplicateKeyException e){
//        log.error(e.getMessage(), e);
//        return RestResponse.error("数据处理错误");
//    }
}
