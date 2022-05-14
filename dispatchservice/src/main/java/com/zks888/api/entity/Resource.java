package com.zks888.api.entity;

import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

/**
 * @author zks888
 * @date 2018/10/16
 */
@Data
public class Resource {
  @Transient private String resource;

  @Transient private List<Handle> handleList;
}
