package com.zks888.api.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DbUserPassword {
    private String userName;
    private String password;
    private String url;
}
