package cn.yznu.rzgskhgl.util;

import cn.yznu.rzgskhgl.pojo.AccessToken;

/**
 * AccessToken 单例类
 * 
 * @author zhangwei
 *
 */
public class SingleAccessToken {

	private AccessToken accessToken;
	private static SingleAccessToken singleAccessToken;

	/**
	 * 私有构造函数
	 */
	private SingleAccessToken() {
		accessToken = CommonUtil.getToken(MessageUtil.APPID, MessageUtil.APPSECRET);
		initThread();
	}

	/**
	 * 获取SingleAccessToken对象
	 * 
	 * @return
	 */
	public static SingleAccessToken getInstance() {
		if (singleAccessToken == null) {
			singleAccessToken = new SingleAccessToken();
		}
		return singleAccessToken;
	}

	public AccessToken getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(AccessToken accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * 开启线程，设置SingleAccessToken为空
	 */
	private void initThread() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					// 睡眠7000秒
					Thread.sleep(7000 * 1000);
					//Thread.sleep(3000);
					singleAccessToken = null;

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}
}