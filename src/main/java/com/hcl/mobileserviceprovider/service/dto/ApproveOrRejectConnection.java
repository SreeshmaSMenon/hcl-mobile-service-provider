package com.hcl.mobileserviceprovider.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApproveOrRejectConnection {

    private String status;
    private String remark;
}
