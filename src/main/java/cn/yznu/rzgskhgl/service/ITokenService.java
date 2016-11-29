package cn.yznu.rzgskhgl.service;

import cn.yznu.rzgskhgl.pojo.CheckModel;

public interface ITokenService extends ICommonService {
	 public String validate(String wxToken, CheckModel tokenModel);
}
