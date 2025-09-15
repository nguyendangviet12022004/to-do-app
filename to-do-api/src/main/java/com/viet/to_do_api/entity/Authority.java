package com.viet.to_do_api.entity;

import com.viet.to_do_api.constant.AuthorityName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Authority {
    private Integer id;
    private AuthorityName name;
}
