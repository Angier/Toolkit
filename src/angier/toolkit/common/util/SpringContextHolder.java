package angier.toolkit.common.util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * �Ծ�̬��������Spring ApplicationContext, �����κδ����κεط��κ�ʱ����ȡ��ApplicaitonContext.
 * @version 1.0
 * @since 1.0
 * */
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext applicationContext = null;

    private static Logger logger = LoggerFactory.getLogger(SpringContextHolder.class);

    /**
     * ȡ�ô洢�ھ�̬�����е�ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * �Ӿ�̬����applicationContext��ȡ��Bean, �Զ�ת��Ϊ����ֵ���������.
     */
    @SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * �Ӿ�̬����applicationContext��ȡ��Bean, �Զ�ת��Ϊ����ֵ���������.
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    /**
     * ���SpringContextHolder�е�ApplicationContextΪNull.
     */
    public static void cleanHolder() {
        logger.debug("���SpringContextHolder�е�ApplicationContext:" + applicationContext);
        applicationContext = null;
    }

    /**
     * ʵ��ApplicationContextAware�ӿ�, ע��Context����̬������.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        logger.debug("ע��ApplicationContext��SpringContextHolder:" + applicationContext);

        if (SpringContextHolder.applicationContext != null) {
                logger.warn("SpringContextHolder�е�ApplicationContext������, ԭ��ApplicationContextΪ:"
                                + SpringContextHolder.applicationContext);
        }

        SpringContextHolder.applicationContext = applicationContext; //NOSONAR
    }

    /**
     * ʵ��DisposableBean�ӿ�, ��Context�ر�ʱ������̬����.
     */
    @Override
    public void destroy() throws Exception {
        SpringContextHolder.cleanHolder();
    }
}