package angier.toolkit.mybatis.dialect;
/**
 * ���ݿⷽ�Գ�����
 * @version 1.0
 * @since 1.0
 * */
public interface DBDialect {

	public static enum Type{
		MYSQL,
		ORACLE
	}
	
	/**
	 * �õ���ҳ��ѯ���
	  * @Title: getLimitString
	  * @param @param sql
	  * @param @param skipResults
	  * @param @param maxResults
	  * @param @return
	  * @return String
	  * @throws
	 */
	public abstract String getLimitString(String sql, int skipResults, int maxResults);
	
	/**
	 *  �õ���ѯ������sql
	  * @Title: getCountString
	  * @param @param querySelect
	  * @param @return
	  * @return String
	  * @throws
	 */
	public abstract String getCountString(String querySelect);
}
