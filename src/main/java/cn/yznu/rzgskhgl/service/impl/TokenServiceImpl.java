package cn.yznu.rzgskhgl.service.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.yznu.rzgskhgl.pojo.CheckModel;
import cn.yznu.rzgskhgl.pojo.WeixinUserInfo;
import cn.yznu.rzgskhgl.service.ICommonService;
import cn.yznu.rzgskhgl.service.ITokenService;
import cn.yznu.rzgskhgl.util.EncoderHandler;

@Service("tokenService")
public class TokenServiceImpl extends CommonServiceimpl implements ITokenService {
	Logger log = LoggerFactory.getLogger(ITokenService.class);

	@Autowired
	@Qualifier("commonService")
	private ICommonService common;

	/**
	 * 微信开发者验证
	 * 
	 * @param wxAccount
	 *
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @return
	 */
	@Override
	public String validate(String wxToken, CheckModel tokenModel) {
		String signature = tokenModel.getSignature();
		Long timestamp = tokenModel.getTimestamp();
		Long nonce = tokenModel.getNonce();
		String echostr = tokenModel.getEchostr();
		log.info("1signature:" + signature + ",timestamp:" + timestamp + ",nonce:" + nonce + ",echostr:" + echostr);
		if (signature != null && timestamp != null & nonce != null) {
			String[] str = { wxToken, timestamp + "", nonce + "" };
			Arrays.sort(str); // 字典序排序
			String bigStr = str[0] + str[1] + str[2];
			// SHA1加密
			String digest = EncoderHandler.encode("SHA1", bigStr).toLowerCase();
			log.info("2signature:" + signature + ",digest:" + digest + ",timestamp:" + timestamp);
			// 确认请求来至微信
			if (digest.equals(signature)) {
				// 最好此处将echostr存起来，以后每次校验消息来源都需要用到
				return echostr;
			}
		}
		return "error";
	}

	@Override
	public WeixinUserInfo getWxUserInfo(String openid) {
		String hql = "from WeixinUserInfo where openId=? ";
		Object[] param = { openid };
		WeixinUserInfo user = common.getSingleByHQL(hql, param);
		return user;
	}
}