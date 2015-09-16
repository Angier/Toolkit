package angier.toolkit.common.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpServletResponse帮助类
 */
public final class ResponseUtils {
	
	public static Logger log = LoggerFactory.getLogger(ResponseUtils.class);

	/**
	 * 发送文本。使用GBK编码。
	 * @param response	HttpServletResponse
	 * @param text		发送的字符串
	 */
	public static void responseText(HttpServletResponse response, String text) {
		response(response, "text/plain;charset=GBK", text);
	}

	/**
	 * 发送json。使用GBK编码。
	 * @param response	HttpServletResponse
	 * @param text		 发送的字符串
	 */
	public static void responseJson(HttpServletResponse response, String text) {
		response(response, "application/json;charset=GBK", text);
	}
	
	/**
	 * 发送html。使用GBK编码。
	 * @param response	HttpServletResponse
	 * @param text		 发送的字符串
	 */
	public static void responseHtml(HttpServletResponse response, String text) {
		response(response, "text/html;charset=GBK", text);
	}

	/**
	 * 发送xml。使用GBK编码。
	 * @param response	HttpServletResponse
	 * @param text		 发送的字符串
	 */
	public static void responseXml(HttpServletResponse response, String text) {
		response(response, "text/xml;charset=GBK", text);
	}

	/**
	 * 发送内容。使用GBK编码。
	 * @param response
	 * @param contentType
	 * @param text
	 */
	public static void response(HttpServletResponse response, String contentType, String text) {
		response.setContentType(contentType);
		/**
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		*/
		try {
			response.getWriter().write(text);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 
	 * 返回已格式化的Json字符串 UTF8
	 * 
	 */
	public static void respnseWriteJsonUTF8(String jsonstr, HttpServletResponse response) {

		try {
			response.setContentType("application/json;charset=UTF-8"); 
			response.setCharacterEncoding("UTF-8");
			System.out.println(jsonstr);
			response.getWriter().write(jsonstr);

		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);

		}
	}

	/**
	 * 
	 * 返回已格式化的Json字符串 GBK
	 * 
	 */
	public static void respnseWriteJsonGBK(String jsonstr, HttpServletResponse response) {

		try {
			response.setContentType("application/json;charset=GBK"); 
			response.setCharacterEncoding("GBK");
			System.out.println(jsonstr);
			response.getWriter().write(jsonstr);

		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);

		}
	}
}
