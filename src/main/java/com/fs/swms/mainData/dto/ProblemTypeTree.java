package com.fs.swms.mainData.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProblemTypeTree {
    private String id;
    private String typeName;
    private String parentId;
    private List<ProblemTypeTree> children = new ArrayList<>();
}
