package angier.toolkit.mybatis.bean;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import angier.toolkit.common.util.constants.AppConstants;
/**
 * �����ORMʵ���޹صķ�ҳ��������ѯ�����װ
 * ����ֻ��װ�����������, ����ķ�ҳ�߼�ȫ����װ��Paginator��.
 * @version 1.0
 * @since 1.0
 * */
public class PageBean{
	// -- �������� --//
	public static final String ASC = "asc";
	public static final String DESC = "desc";
	private int DEF_PAGE_VIEW_SIZE = 15;

	// -- ��ҳ��ѯ���� --//
	protected int pageNo = 1;
	protected int pageSize = DEF_PAGE_VIEW_SIZE;
	protected boolean autoCount = true;
	protected String orderBy = null;
	protected String order = null;
	protected int totalItems = -1;
	private List<Long> slider = null;
	private MyBatisSql myBatisSql = null;
	private long totalPage = 1;
	
	private long minPage;//�ӵڼ�ҳ��ʼ
	private long maxPage;//���ڼ�ҳ����

	// -- ���캯�� --//
	public PageBean() {
	}

	public PageBean(int pageSize) {
		setPageSize(pageSize);
	}

	public PageBean(int pageNo, int pageSize) {
		setPageNo(pageNo);
		setPageSize(pageSize);
	}

	// -- ��ҳ�������ʺ��� --//
	/**
	 * ��õ�ǰҳ��ҳ��,��Ŵ�1��ʼ,Ĭ��Ϊ1.
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * ���õ�ǰҳ��ҳ��,��Ŵ�1��ʼ,����1ʱ�Զ�����Ϊ1.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}

	/**
	 * ���ÿҳ�ļ�¼����, Ĭ��Ϊ-1.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * ����ÿҳ�ļ�¼����.
	 */
	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * ��������ֶ�,��Ĭ��ֵ. ��������ֶ�ʱ��','�ָ�.
	 */
	public String getOrderBy() {
		return orderBy;
	}

	/**
	 * ���������ֶ�,��������ֶ�ʱ��','�ָ�.
	 */
	public void setOrderBy(final String orderBy) {
		this.orderBy = orderBy;
	}

	/**
	 * ���������, ��Ĭ��ֵ.
	 */
	public String getOrder() {
		return order;
	}

	/**
	 * ��������ʽ��.
	 * 
	 * @param order
	 *            ��ѡֵΪdesc��asc,��������ֶ�ʱ��','�ָ�.
	 */
	public void setOrder(final String order) {
		String lowcaseOrder = StringUtils.lowerCase(order);

		// ���order�ַ����ĺϷ�ֵ
		String[] orders = StringUtils.split(lowcaseOrder, ',');
		for (String orderStr : orders) {
			if (!StringUtils.equals(DESC, orderStr)
					&& !StringUtils.equals(ASC, orderStr)) {
				throw new IllegalArgumentException("������" + orderStr + "���ǺϷ�ֵ");
			}
		}

		this.order = lowcaseOrder;
	}

	/**
	 * �Ƿ������������ֶ�,��Ĭ��ֵ.
	 */
	public boolean isOrderBySetted() {
		return (StringUtils.isNotBlank(orderBy) && StringUtils
				.isNotBlank(order));
	}

	/**
	 * ����pageNo��pageSize���㵱ǰҳ��һ����¼���ܽ�����е�λ��,��Ŵ�0��ʼ. ����Mysql,Hibernate.
	 */
	public int getOffset() {
		return ((pageNo - 1) * pageSize);
	}

	/**
	 * ����pageNo��pageSize���㵱ǰҳ��һ����¼���ܽ�����е�λ��,��Ŵ�1��ʼ. ����Oracle.
	 */
	public int getStartRow() {
		return getOffset() + 1;
	}

	/**
	 * ����pageNo��pageSize���㵱ǰҳ���һ����¼���ܽ�����е�λ��, ��Ŵ�1��ʼ. ����Oracle.
	 */
	public int getEndRow() {
		return pageSize * pageNo;
	}

	/**
	 * ����ܼ�¼��, Ĭ��ֵΪ-1.
	 */
	public int getTotalItems() {
		return totalItems;
	}

	/**
	 * �����ܼ�¼��.
	 */
	public void setTotalItems(final int totalItems) {
		this.totalItems = totalItems;
		if (totalItems < 0) {
			this.totalPage = -1;
		}

		long count = totalItems / pageSize;
		if (totalItems % pageSize > 0) {
			count++;
		}
		this.totalPage = count;
	}

	/**
	 * �Ƿ����һҳ.
	 */
	public boolean isLastPage() {
		return pageNo == getTotalPages();
	}

	/**
	 * �Ƿ�����һҳ.
	 */
	public boolean isHasNextPage() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * ȡ����ҳ��ҳ��, ��Ŵ�1��ʼ. ��ǰҳΪβҳʱ�Է���βҳ���.
	 */
	public int getNextPage() {
		if (isHasNextPage()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * �Ƿ��һҳ.
	 */
	public boolean isFirstPage() {
		return pageNo == 1;
	}

	/**
	 * �Ƿ�����һҳ.
	 */
	public boolean isHasPrePage() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * ȡ����ҳ��ҳ��, ��Ŵ�1��ʼ. ��ǰҳΪ��ҳʱ������ҳ���.
	 */
	public int getPrePage() {
		if (isHasPrePage()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * ����pageSize��totalItems������ҳ��, Ĭ��ֵΪ-1.
	 */
	public long getTotalPages() {
		if (totalItems < 0) {
			return -1;
		}

		long count = totalItems / pageSize;
		if (totalItems % pageSize > 0) {
			count++;
		}
		return count;
	}

	public List<Long> getSlider() {
		slider = showSlider(AppConstants.DEFAULT_PAGESUM);
		return slider;
	}

	public MyBatisSql getMyBatisSql() {
		return myBatisSql;
	}

	public void setMyBatisSql(MyBatisSql myBatisSql) {
		this.myBatisSql = myBatisSql;
	}

	/**
	 * �����Ե�ǰҳΪ���ĵ�ҳ���б�,��"��ҳ,23,24,25,26,27,ĩҳ"
	 * @param count ��Ҫ������б��С
	 * @return pageNo�б�
	 */
	private List<Long> showSlider(int count){
		int halfSize = count / 2;
		long totalPage = getTotalPages();

		long startPageNumber = Math.max(pageNo - halfSize, 1);
		long endPageNumber = Math.min(startPageNumber + count - 1, totalPage);

		if (endPageNumber - startPageNumber < count) {
			startPageNumber = Math.max(endPageNumber - count, 1);
		}

		List<Long> result = new ArrayList<Long>();
		for (long i = startPageNumber; i <= endPageNumber; i++) {
			result.add(new Long(i));
		}
		return result;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}



	public void setMinPage(long minPage) {
		this.minPage = minPage;
	}

	public void setMaxPage(long maxPage) {
		this.maxPage = maxPage;
	}
	
	/**
	 * ҳ����չʾ����Сҳ��
	 */
	public long getMinPage() {
		if(totalPage>0 && totalPage-this.pageNo<4){
			if(totalPage<10){
				return 1;
			}else{
				return totalPage-9;
			}
		}else{
			return this.pageNo - 5 > 0 ? this.pageNo - 5 : 1;
		}
	}
	
	/**
	 * ҳ����չʾ�����ҳ��
	 */

	public long getMaxPage() {
		if(getTotalPages() <= 10) {
			return getTotalPages() == 0 ? 1 : getTotalPages();
		}
		
		long p = this.pageNo + 4;
		if(p <= 10 && getTotalPages() >= 10) {
			return 10;
		}
		
		return  this.pageNo + 4 > getTotalPages() ? getTotalPages() : this.pageNo + 4; 
		
	}
}