package com.fs.swms.security.dto;


import lombok.Data;

import java.util.List;

@Data
public class UpdateDataPermission {

    private String userId;

    private List<String> addDataPermissions;

    private List<String> removeDataPermissions;
}
