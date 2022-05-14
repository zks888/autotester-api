package com.zks888.api.dto;

import com.zks888.api.entity.Apicases;

/**
 * @author zks888
 * @date 2018/10/16
 */
public class ApicasewithStatu extends Apicases {
  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  private Boolean status;

}
