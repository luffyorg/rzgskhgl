package cn.yznu.rzgskhgl.common;


/**
 * @deprecated 生成二维码异常
 * @author zhangwei
 *
 */
public class QRParamsException extends Exception {  
    private static final long serialVersionUID = 8837582301762730656L;  
    public QRParamsException()  {}                //用来创建无参数对象  
    public QRParamsException(String message) {        //用来创建指定参数对象  
        super(message);                             //调用超类构造器  
    }  
}  