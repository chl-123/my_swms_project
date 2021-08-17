package com.fs.swms.mainData.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.mainData.dto.CreateSupplier;
import com.fs.swms.mainData.dto.UpdateSupplier;
import com.fs.swms.mainData.entity.Supplier;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2021-08-14
 */
public interface ISupplierService extends IService<Supplier> {
    /**
     * 分页查询供应商列表(带条件)
     * @param page
     * @param supplier
     * @return Page<Role>
     */
    Page<Supplier> selectSupplierList(Page<Supplier> page, Supplier supplier);
    /**
     * 分页查询供应商列表(全部)
     * @param page
     * @return Page<Role>
     */
    Page<Supplier> selectSupplierAll(Page<Supplier> page);
    /**
     * 创建供应商
     * @param supplier
     * @return boolean
     */
    boolean createSupplier(CreateSupplier supplier);

    /**
     * 更新供应商
     * @param supplier
     * @return boolean
     */
    boolean updateSupplier(UpdateSupplier supplier);

    /**
     * 删除供应商
     * @param supplierId
     * @return boolean
     */
    boolean deleteSupplier(String supplierId);

    /**
     * 批量添加供应商
     * @param file
     * @return boolean
     */
    boolean batchCreateSupplier(MyFile file) throws Exception;


}
