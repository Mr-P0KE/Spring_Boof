package com.poke.Comon;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
 * �Զ���Ԫ���ݶ�������
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * ����������Զ����
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("�����ֶ��Զ����[insert]...");
        log.info(metaObject.toString());

        if (metaObject.hasGetter("checkoutTime"))
            metaObject.setValue("checkoutTime", LocalDateTime.now());
        if (metaObject.hasGetter("createTime"))
            metaObject.setValue("createTime", LocalDateTime.now());
        if (metaObject.hasGetter("updateTime"))
            metaObject.setValue("updateTime", LocalDateTime.now());
        if (metaObject.hasGetter("createUser"))
            metaObject.setValue("createUser", BaseContext.getCurrentId());
        if (metaObject.hasGetter("updateUser"))
            metaObject.setValue("updateUser", BaseContext.getCurrentId());
        if (metaObject.hasGetter("orderTime"))
            metaObject.setValue("orderTime", LocalDateTime.now());
    }

    /**
     * ���²������Զ����
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("�����ֶ��Զ����update...");
        log.info(metaObject.toString());

        long id = Thread.currentThread().getId();
        log.info("�߳�idΪ��{}",id);

        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }

}
