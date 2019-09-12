package ru.isnagornov.templates.config

import org.apache.commons.dbcp.BasicDataSource
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.core.env.Environment
import org.springframework.jdbc.datasource.DataSourceTransactionManager

import javax.sql.DataSource

@Configuration
@MapperScan("ru.isnagornov.templates.mapper")
@PropertySource("classpath:db.properties")
class DatasourceConfig {

    @Autowired
    Environment environment

    @Bean
    static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer()
    }

    @Bean
    DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource()

        dataSource.setDriverClassName(environment.getProperty('db.driverClassName'))
        dataSource.setUrl(environment.getProperty('db.url'))
        dataSource.setUsername(environment.getProperty('db.username'))
        dataSource.setPassword(environment.getProperty('db.password'))

        return dataSource
    }

    @Bean
    DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(getDataSource())
    }

    @Bean
    SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean()
        sessionFactory.setDataSource(getDataSource())
        return sessionFactory.getObject()
    }

}
