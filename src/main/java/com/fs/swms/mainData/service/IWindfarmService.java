package com.fs.swms.mainData.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fs.swms.common.entity.MyFile;
import com.fs.swms.mainData.dto.CreateWindfarm;
import com.fs.swms.mainData.dto.QueryWindfarm;
import com.fs.swms.mainData.dto.UpdateCustomer;
import com.fs.swms.mainData.entity.Windfarm;
import com.fs.swms.security.entity.User;

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
     * 创建客户风场
     * @param windfarm
     * @return boolean
     */
    boolean createWindfarm(CreateWindfarm windfarm);

    /**
     * 更新客户风场
     * @param windfarm
     * @return boolean
     */
    boolean updateWindfarm(UpdateCustomer windfarm);

    /**
     * 删除客户风场
     * @param customerId
     * @param windfarmId
     * @return boolean
     */
    boolean deleteWindfarm(String customerId);

    /**
     * 批量添加客户风场
     * @param file
     * @return boolean
     */
    boolean batchCreateWindfarm(MyFile file, User user) throws Exception;

    /**
     * 通过客户id查询客户风场
     * @param customerId
     * @return QueryWindfarm
     */
    QueryWindfarm selectWindfarmByCustomerId(String customerId);


}
