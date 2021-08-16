package com.fs.swms.mainData.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.mainData.entity.Supplier;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2021-08-14
 */
public interface SupplierMapper extends BaseMapper<Supplier> {
    /**
     * 查询供应商列表
     * @param page
     * @param supplier
     * @return Page<Supplier>
     */
    Page<Supplier> selectSupplierList(Page<Supplier> page, @Param("supplier") Supplier supplier);
    /**
     * 通过供应商代码删除该供应商
     * @param supplierNo
     * @return boolean
     */
    boolean deleteBySupplierNo(@Param("supplierNo") String supplierNo);


    Page<Supplier> selectSupplierAll(Page<Supplier> page);
}
