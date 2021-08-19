package com.fs.swms.mainData.mapper;

import com.fs.swms.mainData.entity.Windfarm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2021-08-16
 */
public interface WindfarmMapper extends BaseMapper<Windfarm> {
    /**
     * 通过顾客ID删除顾客风场信息
     * @param customerId
     * @return boolean
     */
    boolean deleteByCustomerId(@Param("customerId") String customerId);

}
