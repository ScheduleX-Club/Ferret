package org.ferret;


import lombok.Getter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@Getter
public class GuardMonitorLoader implements ApplicationContextAware , InitializingBean {

    private Map<String,Object> guards = new HashMap<String, Object>();

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        String[] names = applicationContext.getBeanDefinitionNames();

        for(String beanName : names){

            if(beanName.endsWith("Guard")){
                guards.put(beanName,applicationContext.getBean(beanName));
            }
        }
    }


    public void afterPropertiesSet() throws Exception {}

}
