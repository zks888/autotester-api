package com.zks888.api.core.exception;

import com.zks888.api.core.response.Result;
import com.zks888.api.core.response.ResultCode;
import com.zks888.api.core.response.ResultGenerator;
import com.zks888.api.util.UrlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * 统一异常处理 对于业务异常：返回头 Http 状态码一律使用500，避免浏览器缓存，在响应 Result 中指明异常的状态码 code
 *
 * @author zks888
 * @date 2018/06/09
 */
@Slf4j
@RestControllerAdvice
public class ExceptionResolver {
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(ConstraintViolationException.class)
  public Result validatorException(final ConstraintViolationException e) {
    final String msg =
        e.getConstraintViolations().stream()
            .map(ConstraintViolation::getMessage)
            .collect(Collectors.joining(","));
    // e.toString 多了不需要用户知道的属性路径
    log.error("==> 验证实体异常: {}", e.toString());
    e.printStackTrace();
    return ResultGenerator.genFailedResult(ResultCode.VIOLATION_EXCEPTION, msg);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({ServiceException.class})
  public Result serviceException(final ServiceException e) {
    log.error("==> 服务异常: {}", e.getMessage());
    e.printStackTrace();
    return ResultGenerator.genFailedResult(e.getResultCode(), e.getMessage());
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({ResourcesNotFoundException.class})
  public Result resourcesException(final Throwable e) {
    log.error("==> 资源异常: {}", e.getMessage());
    e.printStackTrace();
    return ResultGenerator.genFailedResult(ResultCode.FIND_FAILED);
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({SQLException.class, DataAccessException.class})
  public Result databaseException(final Throwable e) {
    log.error("==> 数据库异常: {}", e.getMessage());
    e.printStackTrace();
    return ResultGenerator.genFailedResult(ResultCode.DATABASE_EXCEPTION);
  }


  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoHandlerFoundException.class)
  public Result apiNotFoundException(final Throwable e, final HttpServletRequest request) {
    log.error("==> API不存在: {}", e.getMessage());
    e.printStackTrace();
    return ResultGenerator.genFailedResult(
        "API [" + UrlUtils.getMappingUrl(request) + "] not existed");
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(Exception.class)
  public Result globalException(final HttpServletRequest request, final Throwable e) {
    log.error("==> 全局异常: {}", e.getMessage());
    e.printStackTrace();
    return ResultGenerator.genFailedResult(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        String.format("%s => %s", UrlUtils.getMappingUrl(request), e.getMessage()));
  }
}
