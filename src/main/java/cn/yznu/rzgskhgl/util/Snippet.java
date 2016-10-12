package cn.yznu.rzgskhgl.util;

/**
 * 生成随机字符串
 * 
 * @author 张伟
 *
 */
public class Snippet {
	String string = "abcdefghijklmnopqrstuvwxyz123456789";

	private int getRandom(int count) {
		return (int) Math.round(Math.random() * (count));
	}
	/***
	 * 获取16位的随机数
	 * @return
	 */
	public String getRandomString() {
		StringBuffer sb = new StringBuffer();
		int len = string.length();
		for (int i = 0; i < 16; i++) {
			sb.append(string.charAt(getRandom(len - 1)));
		}
		return sb.toString();
	}
}
