package cn.yznu.rzgskhgl.service.impl;
import net.sf.json.JSONObject;  
  
import org.apache.log4j.Logger;  
import org.springframework.stereotype.Service;

import cn.yznu.rzgskhgl.util.CommonUtil;
  
@Service("menuService")  
public class MenuServiceImpl  {  
  
    public static Logger log = Logger.getLogger(MenuServiceImpl.class);  
  
    // 菜单创建（POST） 限100（次/天）  
    public static String MENU_CREATE = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";  
 // 获取接口访问凭证
 	String accessToken = CommonUtil.getToken("wx183636fa6c726c68", "79ada4a6ed3150e83031b20830347a73").getAccessToken();
    public String CreateMenu(String jsonMenu) {  
        String resultStr = "";  
        // 调用接口获取token  
        String token = accessToken;  
        if (token != null) {  
            // 调用接口创建菜单  
            int result = createMenu(jsonMenu, token);  
            // 判断菜单创建结果  
            if (0 == result) {  
                resultStr = "菜单创建成功";  
                log.info(resultStr);  
            } else {  
                resultStr = "菜单创建失败，错误码：" + result;  
                log.error(resultStr);  
            }  
        }  
  
        return resultStr;  
    }  
  
  
    /** 
     * 创建菜单 
     *  
     * @param jsonMenu 
     *            菜单的json格式 
     * @param accessToken 
     *            有效的access_token 
     * @return 0表示成功，其他值表示失败 
     */  
    public static int createMenu(String jsonMenu, String accessToken) {  
  
        int result = 0;  
        // 拼装创建菜单的url  
        String url = MENU_CREATE.replace("ACCESS_TOKEN", accessToken);  
        // 调用接口创建菜单  
        JSONObject jsonObject = CommonUtil.httpsRequest(url, "POST", jsonMenu);  
  
        if (null != jsonObject) {  
            if (0 != jsonObject.getInt("errcode")) {  
                result = jsonObject.getInt("errcode");  
                log.error("创建菜单失败 errcode:" + jsonObject.getInt("errcode")  
                        + "，errmsg:" + jsonObject.getString("errmsg"));  
            }  
        }  
  
        return result;  
    }  
  
    public static void main(String[] args) {  
        // 这是一个符合菜单的json格式，“\”是转义符  
        String jsonMenu = "{\"button\":[{\"name\":\"产品\",\"sub_button\":[{\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx183636fa6c726c68&"
        		+ "redirect_uri=http://luffy.imwork.net/weixin/product&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect\",\"name\":\"产品介绍\",\"type\":\"view\"},{\"key\":\"12\",\"name\":\"产品查询\",\"type\":\"click\"}]},"
        				+ "{\"name\":\"我\",\"sub_button\":[{\"key\":\"21\",\"name\":\"我的信息\",\"type\":\"click\"},"
        				+ "{\"name\":\"我的订单\",\"type\":\"view\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx183636fa6c726c68&"
        				+ "redirect_uri=http://luffy.imwork.net/weixin/myOrder&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect\"},"
        				+ "{\"name\":\"绑定账户\",\"type\":\"view\",\"url\":\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx183636fa6c726c68&"
        				+ "redirect_uri=http://luffy.imwork.net/weixin/login&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect\"}]}]}";  
        MenuServiceImpl impl = new MenuServiceImpl();  
        impl.CreateMenu(jsonMenu);  
        //System.out.println(impl.accessToken);
    }  
  
}  