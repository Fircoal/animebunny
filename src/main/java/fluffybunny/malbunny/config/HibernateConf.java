package fluffybunny.malbunny.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import fluffybunny.malbunny.entity.Entry;
import fluffybunny.malbunny.entity.MalBunnyUser;
import fluffybunny.malbunny.entity.Profile;
import fluffybunny.malbunny.entity.UserRole;

@Configuration
@ComponentScan({"fluffybunny.malbunny.config"})
@EnableTransactionManagement
//@ComponentScan(basePackages="fluffybunny.malbunny")
//@ComponentScans(value = {@ComponentScan("Fluffybunny.bunnytets3.dao"),@ComponentScan("Fluffybunny.bunnytets3.entity"),@ComponentScan("Fluffybunny.bunnytets3.service")})
public class HibernateConf {

 
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
    	System.out.println("Session Factory");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("fluffybunny.malbunny.entity");
        sessionFactory.setHibernateProperties(hibernateProperties());
        sessionFactory.setAnnotatedClasses(Entry.class, MalBunnyUser.class,Profile.class,UserRole.class);
        return sessionFactory;
    }
 
    
    @Bean
    public DataSource dataSource() {
    	System.out.println("Data Source");
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/animu");
        //dataSource.setUrl("jdbc:postgresql://ec2-54-225-96-191.compute-1.amazonaws.com:5432/dbi15v8a2cgoj8?user=ntjmgwwwxddnfp&password=f3d1e73355ea42065fde087aa9027f0ae044fff7bb7ead530dbbfbe470f4d95e&sslmode=require");
        //dataSource.setUrl("#{ 'jdbc:postgresql://' + @dbUrl.getHost() + ':' + @dbUrl.getPort() + @dbUrl.getPath() }\");
        dataSource.setUsername("postgres");
        dataSource.setPassword("root");

        
   /*     dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/anime");
        dataSource.setUsername("root");
        dataSource.setPassword("root");*/
        
        
        return dataSource;
    }
/*
    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
    	System.out.println("Hibernate Transaction Managery");
        HibernateTransactionManager transactionManager
          = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
 */
    
    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(s);
       return txManager;
    }
    
    private final Properties hibernateProperties() {
    	System.out.println("Hibernate Properties");
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(
          "hibernate.hbm2ddl.auto", "update");
        hibernateProperties.setProperty(
          "hibernate.dialect", "org.hibernate.dialect.PostgreSQL95Dialect");
        //hibernateProperties.setProperty("hibernate.show_sql", "true");
        //hibernateProperties.setProperty("hibernate.format_sql", "true");
        return hibernateProperties;
    }
}