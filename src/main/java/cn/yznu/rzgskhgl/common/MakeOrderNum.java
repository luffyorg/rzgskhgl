package cn.yznu.rzgskhgl.common;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author 张伟
 * @Description:生成订单号，1毫秒可以生成999个订单。可以扩展
 */
public class MakeOrderNum {
	/**
	 * 锁对象，可以为任意对象
	 */
	private static Object lockObj = "lockerOrder";
	/**
	 * 订单号生成计数器
	 */
	private static long orderNumCount = 0L;
	/**
	 * 每毫秒生成订单号数量最大值
	 */
	private int maxPerMSECSize=99;
	/**
	 * 生成非重复订单号，理论上限1毫秒1000个，可扩展
	 * @param tname 测试用
	 */
	public String makeOrderNum() {
		String finOrderNum = "";
		try {
			// 最终生成的订单号
			
			synchronized (lockObj) {
				// 取系统当前时间作为订单号变量前半部分，精确到毫秒
				long nowLong = Long.parseLong(new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date()));
				// 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万
				if (orderNumCount > maxPerMSECSize) {
					orderNumCount = 0L;
				}
				//组装订单号
				if (orderNumCount < 10) {
					finOrderNum = nowLong + "0" + orderNumCount;
				}
				if (orderNumCount > 9) {
					finOrderNum = nowLong + "" + orderNumCount;
				}
				orderNumCount++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finOrderNum;
	}

	

}