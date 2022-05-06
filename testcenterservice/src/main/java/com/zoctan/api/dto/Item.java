package com.zoctan.api.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author hacker li
 * @since 08/02/2022 11:06
 */
@Setter
@Getter
public class Item {
    private String name;
    private List<ApiInfo> api;
}
