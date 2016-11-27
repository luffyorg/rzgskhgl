package cn.yznu.rzgskhgl.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class SendMsg_webchinese {

	public String SendMsgForUser(String tel,int status)throws Exception{
		String order= orderStatus(status);
		
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
		NameValuePair[] data = { new NameValuePair("Uid", "我是路小飞"), new NameValuePair("Key", "51575d5643768729ca14"),
				new NameValuePair("smsMob", tel), new NameValuePair("smsText", "亲，你在snake公司购买的产品，订单状态已更新为："+order+"") };
		post.setRequestBody(data);

		client.executeMethod(post);
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:" + statusCode);
		for (Header h : headers) {
			System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
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
		return "0";
	}

}