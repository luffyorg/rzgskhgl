package cn.yznu.rzgskhgl.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.yznu.rzgskhgl.pojo.CheckModel;
import cn.yznu.rzgskhgl.service.ICoreService;
import cn.yznu.rzgskhgl.service.ITokenService;
 
@Controller
@RequestMapping("/wechat")
public class TokenController {
     
    @Autowired
    private ITokenService tokenService;
    @Autowired
	private ICoreService coreService;     
    /**
     * 开发者模式token校验
     *
     * @param wxAccount 开发者url后缀
     * @param response
     * @param tokenModel
     * @throws ParseException
     * @throws IOException
     */
    @RequestMapping(value = "/check/{wxToken}", method = RequestMethod.GET, produces = "text/plain")
    public @ResponseBody String validate(@PathVariable("wxToken")String wxToken,CheckModel tokenModel) throws ParseException, IOException {
        return tokenService.validate(wxToken,tokenModel);
    }
    
    @RequestMapping(value = "/check/{wxToken}", method = RequestMethod.POST, produces = "text/plain")
	public void post(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");

		// 调用核心业务类接收消息、处理消息
		String respMessage = coreService.weixinPost(request);

		// 响应消息
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(respMessage);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
			out = null;
		}
	}
}