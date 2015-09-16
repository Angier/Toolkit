package angier.toolkit.mybatis.dialect;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ��ҳ���������
 * @version 1.0
 * @since 1.0
 * */
public abstract class AbstractDialect implements DBDialect{

	/**
	 * �õ���ҳ��SQL
	 * @param offset 	ƫ����
	 * @param limit		λ��
	 * @return	��ҳSQL
	 */
	public abstract String getLimitString(String querySelect,int offset, int limit);
	
	/**
	 * �õ���ѯ������sql
	 */
	public  String getCountString(String querySelect) {
		querySelect		= getLineSql(querySelect);
		int orderIndex  = getLastOrderInsertPoint(querySelect);
		int formIndex   = getAfterFormInsertPoint(querySelect);
		String select   = querySelect.substring(0, formIndex);
		//���SELECT �а��� DISTINCT ֻ����������COUNT
		if (select.toLowerCase().indexOf("select distinct") != -1 || querySelect.toLowerCase().indexOf("group by")!=-1) {
			return new StringBuffer(querySelect.length()).append(
					"select count(1) count from (").append(
					querySelect.substring(0, orderIndex)).append(" ) t")
					.toString();
		} else {
			return new StringBuffer(querySelect.length()).append(
					"select count(1) count ").append(
					querySelect.substring(formIndex, orderIndex)).toString();
		}
	}
	
	/**
	 * �õ����һ��Order By�Ĳ����λ��
	 * @return �������һ��Order By������λ��
	 */
	protected  int getLastOrderInsertPoint(String querySelect){
		int orderIndex = querySelect.toLowerCase().lastIndexOf("order by");
		if (orderIndex == -1 || !isBracketCanPartnership(querySelect.substring(orderIndex,querySelect.length()))) {
			throw new RuntimeException("My SQL ��ҳ����Ҫ��Order by ���!");
		}
		return orderIndex;
	}
	/**
	 * ��SQL�����һ����䣬����ÿ�����ʵļ������1���ո�
	 * 
	 * @param sql SQL���
	 * @return ���sql��NULL���ؿգ����򷵻�ת�����SQL
	 */
	protected String getLineSql(String sql) {
		return sql.replaceAll("[\r\n]", " ").replaceAll("\\s{2,}", " ");
	}

	/**
	 * �õ�SQL��һ����ȷ��FROM�ĵĲ����
	 */
	protected int getAfterFormInsertPoint(String querySelect) {
		String regex = "\\s+FROM\\s+";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(querySelect);
		while (matcher.find()) {
			int fromStartIndex = matcher.start(0);
			String text = querySelect.substring(0, fromStartIndex);
			if (isBracketCanPartnership(text)) {
				return fromStartIndex;
			}
		}
		return 0;
	}

	/**
	 * �ж�����"()"�Ƿ�ƥ��,�������ж�����˳���Ƿ���ȷ
	 * 
	 * @param text Ҫ�жϵ��ı�
	 * @return ���ƥ�䷵��TRUE,���򷵻�FALSE
	 */
	private boolean isBracketCanPartnership(String text) {
		if (text == null || (getIndexOfCount(text, '(') != getIndexOfCount(text, ')'))) {
			return false;
		}
		return true;
	}

	/**
	 * �õ�һ���ַ�����һ���ַ����г��ֵĴ���
	 * @param text	�ı�
	 * @param ch    �ַ�
	 */
	private int getIndexOfCount(String text, char ch) {
		int count = 0;
		for (int i = 0; i < text.length(); i++) {
			count = (text.charAt(i) == ch) ? count + 1 : count;
		}
		return count;
	}
}
