package angier.toolkit.mybatis.dialect;


/**
 * Oracle·½ÑÔ
 * @version 1.0
 * @since 1.0
 * */
public class OracleDialect extends AbstractDialect{

	public String getLimitString(String sql, int offset, int limit) {
		sql	= getLineSql(sql);
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		pagingSelect.append(sql);
		pagingSelect.append(" ) row_ where rownum <= " + (offset + limit) + ") where rownum_ > " + offset);
		if (isForUpdate) {
			pagingSelect.append(" for update");
		}
		return pagingSelect.toString();
	}
}