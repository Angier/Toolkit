package angier.test;

import java.util.Enumeration;

public class GetProperties {

	//系统属性  
	public static void main(String[] args) {
		java.util.Properties pp = System.getProperties();
		Enumeration<?> names = pp.propertyNames();
		while (names.hasMoreElements()) {
			String key = (String) names.nextElement();
			System.out.println(key +" = "+System.getProperty(key));
		}
	}

}
