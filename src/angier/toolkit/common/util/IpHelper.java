package angier.toolkit.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


public class IpHelper {
	private static final Logger logger = Logger.getLogger(IpHelper.class);
	
	/**
	 * 获取客户的真实IP
	 */
	public static String getClientIP(HttpServletRequest request) {   
	     String ipAddress = "";   
	     
	     ipAddress = request.getHeader("x-forwarded-for");   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	    	 ipAddress = request.getHeader("Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	         ipAddress = request.getHeader("WL-Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	    	 ipAddress = request.getRemoteAddr();   
	    	 if(ipAddress.equals("127.0.0.1")){   
	    		 //根据网卡取本机配置的IP   
	    		 InetAddress inet=null;   
	    		 try {   
	    			 inet = InetAddress.getLocalHost();   
	    		 } catch (UnknownHostException e) {   
	    			 e.printStackTrace();   
	    		 }   
	    		 ipAddress= inet.getHostAddress();   
	    	 }   
	            
	     }   
	  
	     //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割   
	     if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15   
	         if(ipAddress.indexOf(",")>0){   
	             ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));   
	         }   
	     }   
	     return ipAddress;    
	  } 
	
	/**
	 * 获取服务器节点IP
	 */
	public static String getServerIP() {   
		String ipAddress = ""; 
		try {
			ipAddress = InetAddress.getLocalHost().getHostName();
			InetAddress IPAddr[] = InetAddress.getAllByName(ipAddress);
			for (int i = 0; i < IPAddr.length; i++) {
				ipAddress = IPAddr[i].getHostAddress();
				if (ipAddress.startsWith("192.") || ipAddress.startsWith("10.")) {
					break;
				}
			}
		} catch (UnknownHostException e) {
			logger.error("获取节点IP异常：",e);
		}
		return ipAddress;
	}

}
