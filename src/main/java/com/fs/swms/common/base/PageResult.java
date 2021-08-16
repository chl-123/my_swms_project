package com.fs.swms.common.base;

import lombok.Data;

import java.util.List;

/**
 * @ClassName: PageResult
 * @Description: TODO
 * @param <T>
 */
@Data
public class PageResult<T> {

    /**
     * 状态码, 200表示成功
     */
    private int code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 总数量
     */
    private long count;

    /**
     * 当前数据
     */
    private List<T> data;

    /**
     * 默认构造方法
     */
    public PageResult() {
    }

    public PageResult(long total, List<T> rows) {
        this.count = total;
        this.data = rows;
        this.code = 200;
        this.msg = "";
    }
}