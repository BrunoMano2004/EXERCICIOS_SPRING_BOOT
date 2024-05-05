package com.alura.desafioMusica.com.alura.desafioMusica.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
public class DataConfiguration {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        //Drive do BD
        dataSource.setDriverClassName("org.postgresql.Driver");
        //URL do BD
        dataSource.setUrl("jdbc:postgresql://" + System.getenv("DB_HOST") + "/" + System.getenv("DB_NAME"));
        //Usuario
        dataSource.setUsername(System.getenv("DB_USER"));
        //Senha
        dataSource.setPassword(System.getenv("DB_PASSWORD"));
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        //Informa o tipo de BD que vai se ligar
        adapter.setDatabase(Database.POSTGRESQL);
        //Solicita para mostrar o SQL que está sendo executado
        adapter.setShowSql(true);
        //Caso as tabelas não existam crie
        adapter.setGenerateDdl(true);
        //Qual o tipo de SQL que vai ser usado
        adapter.setDatabasePlatform("org.hibernate.dialect.HSQLDialect");
        //Inicie a conexão
        adapter.setPrepareConnection(true);

        return adapter;
    }
}

