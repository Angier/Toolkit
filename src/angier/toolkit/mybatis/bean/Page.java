package angier.toolkit.mybatis.bean;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
/**
 * ��ҳ��ѯ���Bean
 * @version 1.0
 * @since 1.0
 * */
public class Page<T> implements Iterable<T>{

	/** ��ѯ��� */
	private List<T> listResult;
	/** ��ҳ��ϢBean */
	private PageBean pageBean;
	
	/**
	 * (��)
	 */
	public Page() {}
	
	/**
	 * ���ݲ�ѯ�������ҳ��Ϣ����
	 * @param lstResult ��ѯ���
	 * @param pageBean ��ҳ��ϢBean
	 */
	public Page(List<T> listResult, PageBean pageBean) {
		this.listResult = listResult;
		this.pageBean = pageBean;
	}
	
	/**
	 * ȡ�ò�ѯ���
	 * @return ��ѯ���
	 */
	public List<T> getListResult() {
		return listResult;
	}
	/**
	 * ���ò�ѯ���
	 * @param lstResult ��ѯ���
	 */
	public void setListResult(List<T> listResult) {
		this.listResult = listResult;
	}
	
	/**
	 * ȡ�÷�ҳ��ϢBean
	 * @return ��ҳ��ϢBean
	 */
	public PageBean getPageBean() {
		return pageBean;
	}
	/**
	 * ���÷�ҳ��ϢBean
	 * @param pageBean ��ҳ��ϢBean
	 */
	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	// -- ���ʲ�ѯ������� --//
	/**
	 * ʵ��Iterable�ӿ�,����for(Object item : page)����ʹ��
	 */
	@SuppressWarnings("unchecked")
	public Iterator<T> iterator() {
		return listResult == null ? IteratorUtils.EMPTY_ITERATOR : listResult.iterator();
	}
}
