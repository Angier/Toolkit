package angier.toolkit.mybatis.bean;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.IteratorUtils;
/**
 * 分页查询结果Bean
 * @version 1.0
 * @since 1.0
 * */
public class Page<T> implements Iterable<T>{

	/** 查询结果 */
	private List<T> listResult;
	/** 分页信息Bean */
	private PageBean pageBean;
	
	/**
	 * (空)
	 */
	public Page() {}
	
	/**
	 * 根据查询结果、分页信息构造
	 * @param lstResult 查询结果
	 * @param pageBean 分页信息Bean
	 */
	public Page(List<T> listResult, PageBean pageBean) {
		this.listResult = listResult;
		this.pageBean = pageBean;
	}
	
	/**
	 * 取得查询结果
	 * @return 查询结果
	 */
	public List<T> getListResult() {
		return listResult;
	}
	/**
	 * 设置查询结果
	 * @param lstResult 查询结果
	 */
	public void setListResult(List<T> listResult) {
		this.listResult = listResult;
	}
	
	/**
	 * 取得分页信息Bean
	 * @return 分页信息Bean
	 */
	public PageBean getPageBean() {
		return pageBean;
	}
	/**
	 * 设置分页信息Bean
	 * @param pageBean 分页信息Bean
	 */
	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	// -- 访问查询结果函数 --//
	/**
	 * 实现Iterable接口,可以for(Object item : page)遍历使用
	 */
	@SuppressWarnings("unchecked")
	public Iterator<T> iterator() {
		return listResult == null ? IteratorUtils.EMPTY_ITERATOR : listResult.iterator();
	}
}
