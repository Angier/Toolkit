package angier.toolkit.common.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class NetworkInterfaceUtils {

	private final static Log log = LogFactory.getLog(NetworkInterfaceUtils.class);
	private final static String LOCATION_ADDRESS = "127.0.0.1";
	
	private NetworkInterfaceUtils() {
		
	}
	
	/**
	 * 获取多网卡IP
	 * @return
	 */
	public static Set<String> getNetworkAddress() {
		Set<String> networkAddress = new HashSet<String>(1);
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (null != interfaces && interfaces.hasMoreElements()) {
				Enumeration<InetAddress> addressList = interfaces.nextElement().getInetAddresses();  
				while (null != addressList && addressList.hasMoreElements()) {
					InetAddress address = addressList.nextElement();
					if (address instanceof Inet4Address) {
						if (!LOCATION_ADDRESS.equals(address.getHostAddress())) {
							networkAddress.add(address.getHostAddress());
						}
					}
				}
			}
		} catch (SocketException e) {
			log.error(e.getMessage(),e);
		}
		if (networkAddress.isEmpty()) {
			networkAddress.add(LOCATION_ADDRESS);
		}
		return networkAddress;
	}
}