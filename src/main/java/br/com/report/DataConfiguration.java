package br.com.report;

import javax.sql.DataSource;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.Properties;

@Configuration
public class DataConfiguration {
    @Bean
    public DataSource dataSource(){

        Dotenv dotenv = Dotenv.load();
        Properties props = new Properties();
        props.setProperty("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation","true");
        props.setProperty("spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults","false");

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dotenv.get("DB_DRIVE", ""));
        dataSource.setUrl(dotenv.get("DB_URL", ""));
        dataSource.setUsername(dotenv.get("DB_USER_NAME", ""));
        dataSource.setPassword(dotenv.get("DB_PASSWORD", ""));
        dataSource.setConnectionProperties(props);
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setDatabase(Database.POSTGRESQL);
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        adapter.setDatabasePlatform("org.hibernate.dialect.PostgreSQLDialect");
        adapter.setPrepareConnection(true);
        return adapter;
    }
}
