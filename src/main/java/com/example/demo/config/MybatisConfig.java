package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Aspect
@Slf4j
@Configuration
@EnableTransactionManagement
public class MybatisConfig {

    @Bean
    public SqlSessionFactory sessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();

        sessionFactory.setDataSource(dataSource);
//        sessionFactory.setMapperLocations(
//                new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml")
//        );
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis-configuration.xml"));
        sessionFactory.setTypeAliasesPackage("com.example.demo.mybatis.vo");
        sessionFactory.setVfs(SpringBootVFS.class);
        return sessionFactory.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
