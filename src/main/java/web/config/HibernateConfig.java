package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@PropertySource("classpath:hibernate.properties")
@EnableTransactionManagement
@ComponentScan(value = "web")
public class HibernateConfig {

    private final Environment environment;

    @Autowired
    public HibernateConfig(Environment environment) {
        this.environment = environment;
    }

    //Метод, который вернет объект dataSource для соединения к нашей бд, используя данные из файла db.properties
    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName(Objects.requireNonNull(environment.getRequiredProperty("db.driver")));
        driverManagerDataSource.setUrl(environment.getRequiredProperty("db.url"));
        driverManagerDataSource.setUsername(environment.getRequiredProperty("db.username"));
        driverManagerDataSource.setPassword(environment.getRequiredProperty("db.password"));
        return driverManagerDataSource;
    }

    //метод, который возвращает объект Properties, в который переданы свойства
    public Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql")); // добавил
        return properties;
    }


    // метод, который создаст EntityManagerFactory, который будет управляться контейнером
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(getDataSource());
        factoryBean.setPackagesToScan(new String[]{"web.model"});
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaProperties(getHibernateProperties());
        return factoryBean;
    }

    // метод, для настройки менеджера транзакций
    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(emf);
        return txManager;
    }
}