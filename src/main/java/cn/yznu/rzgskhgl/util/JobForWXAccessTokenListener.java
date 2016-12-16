package cn.yznu.rzgskhgl.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import cn.yznu.rzgskhgl.service.ICoreService;

public class JobForWXAccessTokenListener implements ApplicationListener<ContextRefreshedEvent> {
	Logger log = Logger.getLogger(JobForWXAccessTokenListener.class);

	public void onApplicationEvent(ContextRefreshedEvent event) {  
        if(event.getApplicationContext().getParent() == null){  
              
            Runnable runnable = new Runnable() {  
                public void run() {  
                    /** 
                     * 定时设置accessToken 
                     */  
                    //AccessTokenUtil.initAndSetAccessToken();  
                	String accessToken = CommonUtil.getToken(MessageUtil.APPID, MessageUtil.APPSECRET).getAccessToken();
                	log.info("定时设置-获取到的accessToken为：" + accessToken);
                }  
            };  
              
            ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
            service.scheduleAtFixedRate(runnable, 1, 7000, TimeUnit.SECONDS);  
        }  
    }
}