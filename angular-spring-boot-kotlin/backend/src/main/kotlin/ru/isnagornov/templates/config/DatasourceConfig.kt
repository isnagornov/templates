package ru.isnagornov.templates.config

import org.apache.commons.dbcp.BasicDataSource
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager

import javax.sql.DataSource

@Configuration
@MapperScan("ru.isnagornov.templates.mapper")
class DatasourceConfig {

    @Value("\${spring.datasource.driver-class-name}")
    private val driverClassName: String? = null

    @Value("\${spring.datasource.url}")
    private val url: String? = null

    @Value("\${spring.datasource.username}")
    private val username: String? = null

    @Value("\${spring.datasource.password}")
    private val password: String? = null

    val dataSource: DataSource
        @Bean
        get() {
            val dataSource = BasicDataSource()

            dataSource.driverClassName = driverClassName
            dataSource.url = url
            dataSource.username = username
            dataSource.password = password

            return dataSource
        }

    @Bean
    fun transactionManager(): DataSourceTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Bean
    @Throws(Exception::class)
    fun sqlSessionFactory(): SqlSessionFactory? {
        val sessionFactory = SqlSessionFactoryBean()
        sessionFactory.setDataSource(dataSource)
        return sessionFactory.getObject()
    }

}
