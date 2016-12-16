package cn.yznu.rzgskhgl.service.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.yznu.rzgskhgl.pojo.WeixinUserInfo;
import cn.yznu.rzgskhgl.pojo.weixin.req.OAuthInfo;
import cn.yznu.rzgskhgl.pojo.weixin.resp.Template;
import cn.yznu.rzgskhgl.pojo.weixin.resp.TextMessage;
import cn.yznu.rzgskhgl.service.ICommonService;
import cn.yznu.rzgskhgl.service.ICoreService;
import cn.yznu.rzgskhgl.service.ITokenService;
import cn.yznu.rzgskhgl.util.CommonUtil;
import cn.yznu.rzgskhgl.util.MessageUtil;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@Service("coreService")
public class CoreServiceImpl implements ICoreService {

	public static Logger log = Logger.getLogger(CoreServiceImpl.class);
	@Autowired
	private ICommonService commonService;
	@Autowired
	private ITokenService tokenService;

	// 获取接口访问凭证
	String accessToken = CommonUtil.getToken(MessageUtil.APPID, MessageUtil.APPSECRET).getAccessToken();

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public String weixinPost(HttpServletRequest request) {
		String respMessage = null;
		try {

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.xmlToMap(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 消息内容
			String content = requestMap.get("Content");

			log.info("FromUserName is:" + fromUserName + ", ToUserName is:" + toUserName + ", MsgType is:" + msgType);
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				TextMessage text = new TextMessage();
				// 这里根据关键字执行相应的逻辑，只有你想不到的，没有做不到的

				// 自动回复
				text.setContent("亲，不好意思，暂时没有客服陪你聊天额！" + "你输入了：" + content);
				text.setToUserName(fromUserName);
				text.setFromUserName(toUserName);
				text.setCreateTime(new Date().getTime());
				text.setMsgType(msgType);

				respMessage = MessageUtil.textMessageToXml(text);

			}
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				String eventType = requestMap.get("Event");// 事件类型
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {

					TextMessage text = new TextMessage();
					text.setContent(
							"欢迎关注snake团队毕业设计小分队微信公众号。请绑定账户，以便获取更好的体验哟!");
					text.setToUserName(fromUserName);
					text.setFromUserName(toUserName);
					text.setCreateTime(new Date().getTime());
					text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
					respMessage = MessageUtil.textMessageToXml(text);
					// 获取用户信息
					WeixinUserInfo user = tokenService.getWxUserInfo(fromUserName);
					if (user == null) {
						user = getUserInfo(accessToken, fromUserName);
					} else {
						user.setSubscribe(1);
					}
					commonService.save(user);

				}
				// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {// 取消订阅
					// 获取用户信息将关注状态设置为0
					WeixinUserInfo user = tokenService.getWxUserInfo(fromUserName);
					user.setSubscribe(0);
					commonService.saveOrUpdate(user);

				}
				// 自定义菜单点击事件
				else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
					String eventKey = requestMap.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					if (eventKey.equals("12")) {
						TextMessage text = new TextMessage();
						text.setContent("你点击了产品查询");
						text.setToUserName(fromUserName);
						text.setFromUserName(toUserName);
						text.setCreateTime(new Date().getTime());
						text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

						respMessage = MessageUtil.textMessageToXml(text);
					} else if (eventKey.equals("21")) {
						TextMessage text = new TextMessage();
						text.setContent("你点击了我的信息");
						text.setToUserName(fromUserName);
						text.setFromUserName(toUserName);
						text.setCreateTime(new Date().getTime());
						text.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
						respMessage = MessageUtil.textMessageToXml(text);
					}
				}
			}
		} catch (Exception e) {
			log.error("error......");
		}
		return respMessage;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param accessToken
	 *            接口访问凭证
	 * @param openId
	 *            用户标识
	 * @return WeixinUserInfo
	 */
	public static WeixinUserInfo getUserInfo(String accessToken, String openId) {
		WeixinUserInfo weixinUserInfo = null;
		// 拼接请求地址
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
		// 获取用户信息
		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

		if (null != jsonObject) {
			try {
				weixinUserInfo = new WeixinUserInfo();
				// 用户的标识
				weixinUserInfo.setOpenId(jsonObject.getString("openid"));
				// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
				weixinUserInfo.setSubscribe(jsonObject.getInt("subscribe"));
				// 用户关注时间
				weixinUserInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
				// 昵称
				weixinUserInfo.setNickname(jsonObject.getString("nickname"));
				// 用户的性别（1是男性，2是女性，0是未知）
				weixinUserInfo.setSex(jsonObject.getInt("sex"));
				// 用户所在国家
				weixinUserInfo.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				weixinUserInfo.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				weixinUserInfo.setCity(jsonObject.getString("city"));
				// 用户的语言，简体中文为zh_CN
				weixinUserInfo.setLanguage(jsonObject.getString("language"));
				// 用户头像
				weixinUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
			} catch (Exception e) {
				if (0 == weixinUserInfo.getSubscribe()) {
					log.error("用户{}已取消关注:" + weixinUserInfo.getOpenId() + " ");
				} else {
					int errorCode = jsonObject.getInt("errcode");
					String errorMsg = jsonObject.getString("errmsg");
					log.error("获取用户信息失败 errcode:{" + errorCode + "} errmsg:{" + errorMsg + "}");
				}
			}
		}
		return weixinUserInfo;
	}

	public OAuthInfo getOAuthOpenId(String appid, String secret, String code) {
		OAuthInfo oAuthInfo = null;
		String o_auth_openid_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code;";
		String requestUrl = o_auth_openid_url.replace("APPID", appid).replace("SECRET", secret).replace("CODE", code);

		JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

		// oAuthInfo是作者自己把那几个属性参数写在一个类里面了。
		// 如果请求成功
		if (null != jsonObject) {
			try {
				oAuthInfo = new OAuthInfo();
				oAuthInfo.setAccessToken(jsonObject.getString("access_token"));
				oAuthInfo.setExpiresIn(jsonObject.getInt("expires_in"));
				oAuthInfo.setRefreshToken(jsonObject.getString("refresh_token"));
				oAuthInfo.setOpenId(jsonObject.getString("openid"));
				oAuthInfo.setScope(jsonObject.getString("scope"));
			} catch (JSONException e) {
				oAuthInfo = null;
				// 获取token失败
				log.info("网页授权获取openId失败 errcode:{" + jsonObject.getInt("errcode") + "} errmsg:{"
						+ jsonObject.getString("errmsg") + "}");
			}
		}
		return oAuthInfo;
	}

	public boolean sendTemplateMsg(String token, Template template) {

		boolean flag = false;

		String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
		requestUrl = requestUrl.replace("ACCESS_TOKEN", token);

		JSONObject jsonResult = CommonUtil.httpsRequest(requestUrl, "POST", template.toJSON());
		if (jsonResult != null) {
			int errorCode = jsonResult.getInt("errcode");
			String errorMessage = jsonResult.getString("errmsg");
			if (errorCode == 0) {
				flag = true;
			} else {
				System.out.println("模板消息发送失败:" + errorCode + "," + errorMessage);
				flag = false;
			}
		}
		return flag;

	}
}