package angier.toolkit.mybatis.bean;

import java.io.Serializable;

/**
 * 匹配解析配置文件 - sysprofile.properties中 key:value的项
 * @version 1.0
 * @since 1.0
 * */
public class ProfileBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String pkey;
	private String pvalue;
	
	public String getPkey() {
		return pkey;
	}
	public void setPkey(String pkey) {
		this.pkey = pkey;
	}
	public String getPvalue() {
		return pvalue;
	}
	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}
	
	

}
