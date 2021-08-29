package com.fs.swms.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.business.dto.QueryServiceRegister;
import com.fs.swms.business.dto.QueryServiceRegisterManagement;
import com.fs.swms.business.dto.ServiceRegisterInfo;
import com.fs.swms.business.entity.ServiceRegister;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2021-08-23
 */
public interface ServiceRegisterMapper extends BaseMapper<ServiceRegister> {

    Page<ServiceRegisterInfo> selectServiceRegisterList(Page<ServiceRegisterInfo> page, QueryServiceRegister serviceRegister);
    Page<ServiceRegisterInfo> selectRegisterListForManagement(Page<ServiceRegisterInfo> page, QueryServiceRegisterManagement serviceRegister);
    Page<ServiceRegisterInfo> selectListForDistribution(Page<ServiceRegisterInfo> page, QueryServiceRegisterManagement serviceRegister);
    Page<ServiceRegisterInfo> selectListForDistributionManagement(Page<ServiceRegisterInfo> page, QueryServiceRegisterManagement serviceRegister);


}
