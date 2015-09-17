package angier.toolkit.mybatis.dialect;
/**
 * MySql方言实现
 * @version 1.0
 * @since 1.0
 * */
public class MySql5Dialect extends AbstractDialect{

	@Override
	public String getLimitString(String querySelect, int offset, int limit) {
		querySelect	= super.getLineSql(querySelect);
		//String sql =  querySelect.replaceAll("[^\\s,]+\\.", "") +" limit "+ offset +" ,"+ limit;
		String sql =  querySelect +" limit "+ offset +" ,"+ limit;
		return sql;
	}
}
