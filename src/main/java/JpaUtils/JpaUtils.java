package JpaUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtils {

    private static EntityManagerFactory factory;

    static  {
        //1.加载配置文件，创建entityManagerFactory
        factory = Persistence.createEntityManagerFactory("myJpa");
    }

    /**
     * 获取EntityManager对象
     */
    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }
}