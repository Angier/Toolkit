package angier.toolkit.common.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpServletResponse������
 */
public final class ResponseUtils {
	
	public static Logger log = LoggerFactory.getLogger(ResponseUtils.class);

	/**
	 * �����ı���ʹ��GBK���롣
	 * @param response	HttpServletResponse
	 * @param text		���͵��ַ���
	 */
	public static void responseText(HttpServletResponse response, String text) {
		response(response, "text/plain;charset=GBK", text);
	}

	/**
	 * ����json��ʹ��GBK���롣
	 * @param response	HttpServletResponse
	 * @param text		 ���͵��ַ���
	 */
	public static void responseJson(HttpServletResponse response, String text) {
		response(response, "application/json;charset=GBK", text);
	}
	
	/**
	 * ����html��ʹ��GBK���롣
	 * @param response	HttpServletResponse
	 * @param text		 ���͵��ַ���
	 */
	public static void responseHtml(HttpServletResponse response, String text) {
		response(response, "text/html;charset=GBK", text);
	}

	/**
	 * ����xml��ʹ��GBK���롣
	 * @param response	HttpServletResponse
	 * @param text		 ���͵��ַ���
	 */
	public static void responseXml(HttpServletResponse response, String text) {
		response(response, "text/xml;charset=GBK", text);
	}

	/**
	 * �������ݡ�ʹ��GBK���롣
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
	 * �����Ѹ�ʽ����Json�ַ��� UTF8
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
	 * �����Ѹ�ʽ����Json�ַ��� GBK
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
