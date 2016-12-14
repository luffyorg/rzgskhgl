package cn.yznu.rzgskhgl.service;

import javax.servlet.http.HttpServletRequest;

import cn.yznu.rzgskhgl.pojo.WeixinUserInfo;


public interface ICoreService {
	public String weixinPost(HttpServletRequest request);
	
	/*public WeixinUserInfo getUserInfo(String accessToken, String openId);*/
}
