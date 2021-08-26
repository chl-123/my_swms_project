package com.fs.swms.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fs.swms.business.entity.Rectification;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2021-08-19
 */
public interface RectificationMapper extends BaseMapper<Rectification> {

    Page<Rectification> selectRectificationList(Page<Rectification> page, @Param("rectification") Rectification rectification);

    Page<Rectification> selectRectificationAll(Page<Rectification> page);
}
