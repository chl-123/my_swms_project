package com.fs.swms.common.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Object creator = getFieldValByName("creator", metaObject);
        if (null == creator && null != SecurityUtils.getSubject()) {
            String principal = (String) SecurityUtils.getSubject().getPrincipal();
            setFieldValByName("creator", "用户名", metaObject);
            if (!StringUtils.isEmpty(principal)) {
                JSONObject userObj = JSON.parseObject(principal);
                setFieldValByName("creator", userObj.getString("id"), metaObject);
            }
        }
        Object createTime = getFieldValByName("createTime", metaObject);
        if (null == createTime) {
            setFieldValByName("createTime", new Date(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object operator = getFieldValByName("operator", metaObject);
        if (null == operator && null != SecurityUtils.getSubject()) {
            String principal = (String) SecurityUtils.getSubject().getPrincipal();
            setFieldValByName("operator", "用户名", metaObject);
            if (!StringUtils.isEmpty(principal)) {
                JSONObject userObj = JSON.parseObject(principal);
                setFieldValByName("operator", userObj.getString("id"), metaObject);
            }
        }
        Object updateTime = getFieldValByName("updateTime", metaObject);
        if (null == updateTime) {
            setFieldValByName("updateTime",  new Date(), metaObject);
        }
    }
}
