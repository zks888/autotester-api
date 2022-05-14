package com.zks888.api.util;

import com.zks888.api.core.exception.ServiceException;
import com.zks888.api.core.response.ResultCode;

/**
 * 断言工具
 *
 * @author zks888
 * @date 2018/11/29
 */
public class AssertUtils {
  public static void throwIf(
      final boolean statement, final ResultCode resultCode, final String message) {
    if (statement) {
      throw toThrow(resultCode, message);
    }
  }

  public static void throwIf(
      final boolean statement, final ResultCode resultCode, final Object... messages) {
    throwIf(statement, resultCode, resultCode.format(messages));
  }

  public static RuntimeException toThrow(final ResultCode resultCode, final Object... messages) {
    return new ServiceException(resultCode, resultCode.format(messages));
  }

  public static void asserts(
      final boolean statement, final ResultCode resultCode, final Object... messages) {
    throwIf(!statement, resultCode, messages);
  }
}
