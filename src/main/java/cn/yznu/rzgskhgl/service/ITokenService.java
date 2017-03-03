package cn.yznu.rzgskhgl.service;

import cn.yznu.rzgskhgl.pojo.CheckModel;
import cn.yznu.rzgskhgl.pojo.WeixinUserInfo;

public interface ITokenService extends ICommonService {
	 public String validate(String wxToken, CheckModel tokenModel);
	 /**
	  * 根据openid 获取用户
	  * @param openid
	  * @return
	  */
	 public WeixinUserInfo getWxUserInfo(String openid);
}
