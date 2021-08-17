package com.fs.swms.mainData.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.mainData.dto.CreateWindfarm;
import com.fs.swms.mainData.dto.QueryWindfarm;
import com.fs.swms.mainData.dto.UpdateCustomer;
import com.fs.swms.mainData.entity.Windfarm;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2021-08-16
 */
public interface IWindfarmService extends IService<Windfarm> {

    /**
     * 分页查询供应商列表(带条件)
     * @param page
     * @param windfarm
     * @return Page<Role>
     */
    Page<Windfarm> selectWindfarmList(Page<Windfarm> page, Windfarm windfarm);
    /**
     * 分页查询供应商列表(全部)
     * @param page
     * @return Page<Role>
     */
    Page<Windfarm> selectWindfarmAll(Page<Windfarm> page);
    /**
     * 创建供应商
     * @param windfarm
     * @return boolean
     */
    boolean createWindfarm(CreateWindfarm windfarm);

    /**
     * 更新供应商
     * @param windfarm
     * @return boolean
     */
    boolean updateWindfarm(UpdateCustomer windfarm);

    /**
     * 删除供应商
     * @param customerId
     * @param windfarmId
     * @return boolean
     */
    boolean deleteWindfarm(String customerId,String windfarmId);

    /**
     * 批量添加供应商
     * @param file
     * @return boolean
     */
    boolean batchCreateWindfarm(MyFile file) throws Exception;

    QueryWindfarm selectWindfarmByCustomerId(String customerId);
}
