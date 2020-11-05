package com.smartindustry.datasynchronize.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


/**
 * @author hui.feng
 * @date created in 2020/11/4
 * @description
 */
@Configuration
@MapperScan(basePackages = "com.smartindustry.common.sqlserver", sqlSessionFactoryRef = "sqlServerSessionFactory")
public class MybatisSqlServerConfig {

    @Bean(name = "sqlServerDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.test1")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sqlServerTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("sqlServerDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);

    }

    @Bean(name = "sqlServerSessionFactory")
    public SqlSessionFactory basicSqlSessionFactory(@Qualifier("sqlServerDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath:com/smartindustry/common/sqlserver/**/*.xml"));
        return factoryBean.getObject();
    }

    @Bean(name = "sqlServerSessionTemplate")
    public SqlSessionTemplate testSqlSessionTemplate(
            @Qualifier("sqlServerSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
