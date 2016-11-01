package cn.yznu.rzgskhgl.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * @deprecated 文件工具类 打包下载文件 
 * @author zhagnwei
 * 
 */
public class FileUtils {
	private Logger log = Logger.getLogger(FileUtils.class);
	private static final String FilePath = "D:\\";
	
	
	/**
	 * 文件打包
	 * @param name 要打包成的名称
	 * @param files 要打包的文件
	 * @param request
	 * @param response
	 * @return
	 */
	public String execute(String name,File[] files, HttpServletRequest request, HttpServletResponse response) {
		//File[] files = {new File(FilePath + "1.txt"),new File(FilePath + "2.txt")};
		String tmpFileName = name + ".zip";
		byte[] buffer = new byte[1024];
		String strZipPath = FilePath + tmpFileName;
		try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(strZipPath));
			for (int i = 0; i < files.length; i++) {
				FileInputStream fis = new FileInputStream(files[i]);
				out.putNextEntry(new ZipEntry(files[i].getName()));
				// 设置压缩文件内的字符编码，不然会变成乱码
				out.setEncoding("GBK");
				int len;
				// 读入需要下载的文件的内容，打包到zip文件
				while ((len = fis.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				fis.close();
			}
			out.close();
			this.downFile(response, tmpFileName);
		} catch (Exception e) {
			log.error("文件下载出错", e);
		}
		return "success";
	}

	/**
	 * 文件下载
	 * 
	 * @param response
	 * @param str
	 */

	private void downFile(HttpServletResponse response, String str) {
		try {
			String path = FilePath + str;
			File file = new File(path);
			if (file.exists()) {
				InputStream ins = new FileInputStream(path);
				BufferedInputStream bins = new BufferedInputStream(ins);// 放到缓冲流里面
				OutputStream outs = response.getOutputStream();// 获取文件输出IO流
				BufferedOutputStream bouts = new BufferedOutputStream(outs);
				response.setContentType("application/x-download");// 设置response内容的类型
				response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(str, "UTF-8"));// 设置头部信息
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				// 开始向网络传输文件流
				while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
					bouts.write(buffer, 0, bytesRead);
				}
				bouts.flush();// 这里一定要调用flush()方法
				ins.close();
				bins.close();
				outs.close();
				bouts.close();
			} else {
				System.out.println("文件下载出错");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
