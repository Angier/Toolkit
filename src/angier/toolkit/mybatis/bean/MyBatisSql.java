package angier.toolkit.mybatis.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * mybatisSQl������
 * @author liusz
 * @date 2013-1-27 ����4:47:11
 * @version 1.0
 * @since 1.0
 * */
public class MyBatisSql {
	/** 
     * ������ sql 
     */  
    private String sql;  
      
    /** 
     * ���� ���� 
     */  
    private Object[] parameters;

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}
	@Override
	public String toString() {
		if(parameters == null || sql == null)
		{
			return "";
		}
		List<Object> parametersArray = Arrays.asList(parameters);
		List<Object> list = new ArrayList<Object>(parametersArray);
		while(sql.indexOf("?") != -1 && list.size() > 0 && parameters.length > 0)
		{
			sql = sql.replaceFirst("\\?", list.get(0).toString());
			list.remove(0);
		}
		return sql.replaceAll("(\r?\n(\\s*\r?\n)+)", "\r\n");
	}
}
