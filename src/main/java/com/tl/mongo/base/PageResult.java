package com.tl.mongo.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页结果.
 * @author Ryan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    private int pageNumber;

    private int pageSize;

    private long totalRowsCount;

    private int totalPageCount;

    private List<T> dataList;

}