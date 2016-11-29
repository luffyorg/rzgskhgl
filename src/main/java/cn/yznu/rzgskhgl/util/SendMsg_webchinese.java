package cn.yznu.rzgskhgl.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;

import cn.yznu.rzgskhgl.pojo.SendSms;
import cn.yznu.rzgskhgl.service.IUserService;
/**
 * 发送短信 调用接口类
 * @author zhangwei
 * @date 2016-11-15
 */
public class SendMsg_webchinese {
	@Autowired
	private IUserService userService;
	public String SendMsgForUser(SendSms sms)throws Exception{
		String tel = String.valueOf(sms.getSmsMob());
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
		NameValuePair[] data = { new NameValuePair("Uid", "我是路小飞"), new NameValuePair("Key", "51575d5643768729ca14"),
				new NameValuePair("smsMob",tel), new NameValuePair("smsText", sms.getSmsText()) };
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:" + statusCode);
		for (Header h : headers) {
			System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
		sms.setStatus(1);
		userService.saveOrUpdate(sms);
		System.out.println(result); // 打印返回消息状态
		post.releaseConnection();
		return result;
	}
	public String orderStatus(int status){
		if(status==1){
			return "签订合同";
		}else if(status==2){
			return "收齐资料";
		}else if(status==3){
			return "递交渠道处";
		}else if(status==4){
			return "审核阶段";
		}else if(status==5){
			return "下款";
		}else if(status==6){
			return "收费";
		}else if(status==7){
			return "完成";
		}
		return "未更新";
	}

}