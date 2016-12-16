package cn.yznu.rzgskhgl.service;

import javax.servlet.http.HttpServletRequest;

import cn.yznu.rzgskhgl.pojo.WeixinUserInfo;
import cn.yznu.rzgskhgl.pojo.weixin.req.OAuthInfo;
import cn.yznu.rzgskhgl.pojo.weixin.resp.Template;


public interface ICoreService {
	public String weixinPost(HttpServletRequest request);
	
	/*public WeixinUserInfo getUserInfo(String accessToken, String openId);*/
	public OAuthInfo getOAuthOpenId(String appid, String secret, String code ) ;
	
	public boolean sendTemplateMsg(String token, Template template);
}
