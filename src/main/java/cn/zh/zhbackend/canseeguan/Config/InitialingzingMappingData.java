package cn.zh.zhbackend.canseeguan.Config;

import cn.zh.zhbackend.canseeguan.service.DataModule;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class InitialingzingMappingData implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        DataModule.InitCellMapping();
        System.out.println("InitializingBean..");
    }
}