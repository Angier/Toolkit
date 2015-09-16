package angier.toolkit.mybatis.plugin;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

import angier.toolkit.common.util.ReflectionUtils;
import angier.toolkit.mybatis.bean.MyBatisSql;
import angier.toolkit.mybatis.bean.PageBean;
import angier.toolkit.mybatis.dialect.DBDialect;
import angier.toolkit.mybatis.dialect.MySql5Dialect;
import angier.toolkit.mybatis.dialect.OracleDialect;
/**
 * ���ݿ��ҳ����ʵ��
 * @author liusz
 * @date 2013-1-27 ����3:31:13
 * @version 1.0
 * @since 1.0
 * */
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class PaginationInterceptor implements Interceptor{

	private final static Log log = LogFactory.getLog(PaginationInterceptor.class);
	/** mapper.xml����Ҫ���ص�ID(����ƥ��) **/
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	private static String PAGESQL_ID = "pageSqlId";// ��Ҫ���ص�ID(����ƥ��)
	private static String EPAGESQL_ID = "exportSqlId";
	private String pageSqlId;
	private String ePageSqlId;
	
	@Override
	@SuppressWarnings({"unchecked" })
	public Object intercept(Invocation invocation) throws Throwable {
		RoutingStatementHandler routingStatementHandler = (RoutingStatementHandler) invocation.getTarget();
		BaseStatementHandler delegate = (BaseStatementHandler) ReflectionUtils.getFieldValue(routingStatementHandler, "delegate");
		MappedStatement mappedStatement = (MappedStatement) ReflectionUtils.getFieldValue(delegate, "mappedStatement");
		if (mappedStatement.getId().matches(pageSqlId)) {
			StatementHandler statementHandler = (StatementHandler)invocation.getTarget();
			MetaObject metaStatementHandler = MetaObject.forObject(
					statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
			
			// ������������(����Ŀ������ܱ�������������أ��Ӷ��γɶ�δ���ͨ�����������ѭ��
			// ���Է������ԭʼ�ĵ�Ŀ����)
			while (metaStatementHandler.hasGetter("h")) {
				Object object = metaStatementHandler.getValue("h");
				metaStatementHandler = MetaObject.forObject(object,
						DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
			}
			// �������һ����������Ŀ����
			while (metaStatementHandler.hasGetter("target")) {
				Object object = metaStatementHandler.getValue("target");
				metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
			}
			
			Configuration configuration = (Configuration)metaStatementHandler.getValue("delegate.configuration");
			BoundSql boundSql = delegate.getBoundSql();
			Object parameterObject = boundSql.getParameterObject();
			if(parameterObject == null){
				log.error("����������δʵ������");
				throw new NullPointerException("����������δʵ������");
			}
			Map<String, Object> parameterMap = (Map<String, Object>) parameterObject;
			PageBean pageBean = (PageBean) parameterMap.get("pageBean");
			if(pageBean == null){
				log.error("��ҳ���󲻴��ڣ�");
				throw new NullPointerException("��ҳ���󲻴��ڣ�");
			}
			String querySql = boundSql.getSql();
			if(mappedStatement.getId().matches(ePageSqlId)){
				MyBatisSql myBatisSql = getIbatisSql(boundSql, mappedStatement);
				pageBean.setMyBatisSql(myBatisSql);
			}
			// ȡ������ 
			Connection connection = (Connection) invocation.getArgs()[0];
			DBDialect.Type databaseType  = null;
			try{
				databaseType = DBDialect.Type.valueOf(configuration.getVariables().getProperty("dialect").toUpperCase());
			} catch(Exception e){
				e.printStackTrace();
			}
			if(databaseType == null){
				throw new RuntimeException("the value of the dialect property in configuration.xml is not defined : " + configuration.getVariables().getProperty("dialect"));
			}
			DBDialect dialect = null;
			switch(databaseType){
				case MYSQL:
					dialect = new MySql5Dialect();break;
				case ORACLE:
					dialect = new OracleDialect();break;
			}
			String countSql = dialect.getCountString(querySql);
			log.debug("��ѯ�ܼ�¼SQL:" + countSql);
			BoundSql newBoundSql = new BoundSql(mappedStatement
					.getConfiguration(), countSql, boundSql
					.getParameterMappings(), parameterObject);
			
			copyAdditionalParametersByBoundSql(newBoundSql,boundSql);
			
			/*-------֧��foreah�еĲ�����ȡ--------*/
			Field metaParamsField = ReflectionUtils.getAccessibleField(boundSql, "metaParameters");
			if (metaParamsField != null) {
                MetaObject mo = (MetaObject) ReflectionUtils.getFieldValue(boundSql, "metaParameters");
                ReflectionUtils.setFieldValue(newBoundSql, "metaParameters", mo);
            }
			/*------------------------*/
			DefaultParameterHandler parameterHandler = new DefaultParameterHandler(
					mappedStatement, parameterObject, newBoundSql);
			PreparedStatement ps = null;
			int count = 0;
			try{
				ps = connection.prepareStatement(countSql);
				parameterHandler.setParameters(ps);
				ResultSet rs = ps.executeQuery();
				count = (rs.next()) ? rs.getInt("count") : 0;
				rs.close();
			}catch(SQLException e){
				throw new Exception("ִ�м�¼����SQLʱ�����쳣",e);
			}finally{
				try {
					if (ps != null) ps.close();
				} catch (SQLException e) {
					throw new Exception("�ر�״̬ʱ�����쳣", e);
				}
			}
			// �Ѽ�¼�����������pageBean��
			pageBean.setTotalItems(count);
			String originalSql = (String)metaStatementHandler.getValue("delegate.boundSql.sql");
			metaStatementHandler.setValue("delegate.boundSql.sql", dialect.getLimitString(originalSql, pageBean.getOffset(), pageBean.getPageSize()) );
			metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET );
			metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT );
		}
		return invocation.proceed();
	}
	
	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		pageSqlId = properties.getProperty(PAGESQL_ID);
		if (pageSqlId == null || pageSqlId.length() < 1) {
			log.error("pageSqlId property is not found!");
		}
		ePageSqlId = properties.getProperty(EPAGESQL_ID);
		if (ePageSqlId == null || ePageSqlId.length() < 1) {
			log.error("ePageSqlId property is not found!");
		}
	}
	
	/**
	 * 
	 * @param id xml ��sql��id ���� <select id="XXX">�е�"XXX"
	 * @param parameterObject ��������sql�Ĳ���
	 * @return
	 */
	public MyBatisSql getIbatisSql(BoundSql boundSql,MappedStatement ms) {
		MyBatisSql myBatisSql = new MyBatisSql();
		String querySql = boundSql.getSql().replace("\r"," ").replace("\n"," ").replace("\t"," ").replaceAll(" {2,}"," "); 
		myBatisSql.setSql(querySql);
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
	    if (parameterMappings != null) {
			Object[] parameterArray = new Object[parameterMappings.size()];
	        MetaObject metaObject = parameterObject == null ? null : MetaObject.forObject(parameterObject, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
	        for (int i = 0; i < parameterMappings.size(); i++) {
	        	ParameterMapping parameterMapping = parameterMappings.get(i);
	        	if (parameterMapping.getMode() != ParameterMode.OUT) {
	        		Object value;
	        		String propertyName = parameterMapping.getProperty();
	        		PropertyTokenizer prop = new PropertyTokenizer(propertyName);
	        		if (parameterObject == null) {
	        			value = null;
	        		} else if (ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(parameterObject.getClass())) {
	        			value = parameterObject;
	        		} else if (boundSql.hasAdditionalParameter(propertyName)) {
	        			value = boundSql.getAdditionalParameter(propertyName);
	        		} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {
	        			value = boundSql.getAdditionalParameter(prop.getName());
	        			if (value != null) {
	        				value = MetaObject.forObject(value, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY).getValue(propertyName.substring(prop.getName().length()));
	        			}
	        		} else {
	        			value = metaObject == null ? null : metaObject.getValue(propertyName);
	        		}
	        		parameterArray[i] = value;
	        	}
	        }
	        myBatisSql.setParameters(parameterArray);
	    }
		return myBatisSql;
	}

	/**
	 * 
	 * Ϊ�˽��mybatis��ҳ����ڽ���
	 * select from in (xx,xx,xx)ʱ������������bug����������
	 * ��Ҫ��Ϊ�˽�ԭʼBoundSql������additionalParameters���Ƶ��¶��������ȥ
	 * mybatisԭʼ��û���ṩadditionalParameters�ĸ�ֵ�ӿ�
	 */
	private void copyAdditionalParametersByBoundSql(BoundSql target,BoundSql source){
		List<ParameterMapping> parameterMappings = source.getParameterMappings();
		if(parameterMappings !=null ){
			for(ParameterMapping parameter : parameterMappings){
				if(source.hasAdditionalParameter(parameter.getProperty())){
					
					target.setAdditionalParameter(parameter.getProperty(), source.getAdditionalParameter(parameter.getProperty()));
				}
			}
		}
		 
	}

}
