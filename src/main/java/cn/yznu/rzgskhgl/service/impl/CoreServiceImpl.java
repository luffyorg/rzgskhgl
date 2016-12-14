package cn.yznu.rzgskhgl.service.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.yznu.rzgskhgl.pojo.Customer;
import cn.yznu.rzgskhgl.pojo.WeixinUserInfo;
import cn.yznu.rzgskhgl.pojo.weixin.resp.TextMessage;
import cn.yznu.rzgskhgl.service.ICommonService;
import cn.yznu.rzgskhgl.service.ICoreService;
import cn.yznu.rzgskhgl.service.ITokenService;
import cn.yznu.rzgskhgl.util.CommonUtil;
import cn.yznu.rzgskhgl.util.MessageUtil;
import net.sf.json.JSONObject;

@Service("coreService")
public class CoreServiceImpl implements ICoreService {

	public static Logger log = Logger.getLogger(CoreServiceImpl.class);
	@Autowired
	private ICommonService commonService;
	@Autowired
	private ITokenService tokenService;

	// 获取接口访问凭证
	String accessToken = CommonUtil.getToken("wx183636fa6c726c68", "79ada4a6ed3150e83031b20830347a73").getAccessToken();

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
			request.getSession().setAttribute("fromUserName", fromUserName);
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				TextMessage text = new TextMessage();
				// 这里根据关键字执行相应的逻辑，只有你想不到的，没有做不到的
				if (content.equals("1") || content.equals("订单")) {
					WeixinUserInfo user = tokenService.getWxUserInfo(fromUserName);
					Customer customer = tokenService.get(Customer.class, user.getCustomerId());
					if (customer == null) {
						text.setContent("请绑定账户，获取更好的体验：http://luffy.imwork.net/rzgskhgl/weixin/login.do?openid="+fromUserName+"");
						text.setToUserName(fromUserName);
						text.setFromUserName(toUserName);
						text.setCreateTime(new Date().getTime());
						text.setMsgType(msgType);
					}else{
						text.setContent("点击链接获取你的订单详情：http://luffy.imwork.net/rzgskhgl/weixin/myOrder.do");
						text.setToUserName(fromUserName);
						text.setFromUserName(toUserName);
						text.setCreateTime(new Date().getTime());
						text.setMsgType(msgType);
					}
					
				} else if (content.equals("2") || content.equals("产品")) {
					text.setContent("点击链接获取最新的产品信息：http://luffy.imwork.net/rzgskhgl/weixin/newProduct.do");
					text.setToUserName(fromUserName);
					text.setFromUserName(toUserName);
					text.setCreateTime(new Date().getTime());
					text.setMsgType(msgType);
				} else {
					// 自动回复
					text.setContent("对不起，你输入的关键字有误！回复'订单'或者'1' 获取你的订单信息，回复'产品'或者'2' 获取最新的产品资讯");
					text.setToUserName(fromUserName);
					text.setFromUserName(toUserName);
					text.setCreateTime(new Date().getTime());
					text.setMsgType(msgType);
				}

				respMessage = MessageUtil.textMessageToXml(text);

			} /*
				 * else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT))
				 * {// 事件推送 String eventType = requestMap.get("Event");// 事件类型
				 * 
				 * if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {//
				 * 订阅 respContent = "欢迎关注xxx公众号！"; return
				 * MessageResponse.getTextMessage(fromUserName , toUserName ,
				 * respContent); } else if
				 * (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {//
				 * 自定义菜单点击事件 String eventKey = requestMap.get("EventKey");//
				 * 事件KEY值，与创建自定义菜单时指定的KEY值对应 logger.info("eventKey is:"
				 * +eventKey); return xxx; } } //开启微信声音识别测试 2015-3-30 else
				 * if(msgType.equals("voice")) { String recvMessage =
				 * requestMap.get("Recognition"); //respContent =
				 * "收到的语音解析结果："+recvMessage; if(recvMessage!=null){ respContent
				 * = TulingApiProcess.getTulingResult(recvMessage); }else{
				 * respContent = "您说的太模糊了，能不能重新说下呢？"; } return
				 * MessageResponse.getTextMessage(fromUserName , toUserName ,
				 * respContent); } //拍照功能 else
				 * if(msgType.equals("pic_sysphoto")) {
				 * 
				 * } else { return MessageResponse.getTextMessage(fromUserName ,
				 * toUserName , "返回为空"); }
				 */
			// 事件推送
			else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
				String eventType = requestMap.get("Event");// 事件类型
				// 订阅
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {

					TextMessage text = new TextMessage();
					text.setContent(
							"欢迎关注snake团队微信公众号，回复'订单'或者'1' 获取你的订单信息，回复'产品'或者'2' 获取最新的产品资讯");
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
					if (eventKey.equals("customer_telephone")) {
						TextMessage text = new TextMessage();
						text.setContent("0755-86671980");
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

}