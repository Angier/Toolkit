package angier.toolkit.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


public class IpHelper {
	private static final Logger logger = Logger.getLogger(IpHelper.class);
	
	/**
	 * ��ȡ�ͻ�����ʵIP
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
	    		 //��������ȡ�������õ�IP   
	    		 InetAddress inet=null;   
	    		 try {   
	    			 inet = InetAddress.getLocalHost();   
	    		 } catch (UnknownHostException e) {   
	    			 e.printStackTrace();   
	    		 }   
	    		 ipAddress= inet.getHostAddress();   
	    	 }   
	            
	     }   
	  
	     //����ͨ�����������������һ��IPΪ�ͻ�����ʵIP,���IP����','�ָ�   
	     if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15   
	         if(ipAddress.indexOf(",")>0){   
	             ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));   
	         }   
	     }   
	     return ipAddress;    
	  } 
	
	/**
	 * ��ȡ�������ڵ�IP
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
			logger.error("��ȡ�ڵ�IP�쳣��",e);
		}
		return ipAddress;
	}

}
