package cn.yznu.rzgskhgl.util;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.yznu.rzgskhgl.pojo.AccessToken;

/**
 * @ClassName: ServletContextUtil
 * @author zhangw
 * @Description: 全局缓存servletcontext
 */
public final class ServletContextUtil {
	private static final Logger log = Logger.getLogger(ServletContextUtil.class);
	private static ServletContext serveltContext = null;

	private ServletContextUtil() {
	};

	public synchronized static ServletContext get() {

		if (null == serveltContext) {
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			serveltContext = webApplicationContext.getServletContext();
			log.info("获取到serveltContext ：" + serveltContext);
		}
		return serveltContext;
	}
	public static AccessToken  getAccessToken() {  
        return (AccessToken) ServletContextUtil.get().getAttribute(MessageUtil.ACCESS_TOKEN);  
    }  
}