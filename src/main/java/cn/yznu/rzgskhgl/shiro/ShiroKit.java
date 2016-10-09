package cn.yznu.rzgskhgl.shiro;

import org.apache.log4j.Logger;
import org.apache.shiro.crypto.hash.Md5Hash;

public class ShiroKit {
	private static final Logger logger = Logger.getLogger(ShiroKit.class);

	public static String md5(String password, String salt) {
		logger.info("md5加密");
		String p = null;
		p = new Md5Hash(password, salt).toHex();
		logger.info("加密后：" + p);
		return p;
	}

	public static boolean isEmpty(Object obj) {

		if (obj instanceof String) {
			return "".equals(obj);
		}

		if (obj instanceof Integer) {
			return (Integer) obj == 0;
		}
		if (obj == null)
			return true;
		else
			return false;
	}

}
