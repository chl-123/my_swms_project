package com.fs.swms.mainData.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
@Data
public class QueryCustomer implements Serializable {
    private static final long serialVersionUID = 1L;
    private String customerName;
    private List<String> windfarms;
}
