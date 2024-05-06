package treinamento1.treinamento1.configuration;

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
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        //URL do BD
        dataSource.setUrl("jdbc:mysql://bckqenbq3usaz4qkwslx-mysql.services.clever-cloud.com/bckqenbq3usaz4qkwslx?useTimezone=true&serverTimezone=UTC");
        //Usuario
        dataSource.setUsername("uljqevhsfzgv4kmo");
        //Senha
        dataSource.setPassword("0tOWu1uzrmVlG2G8CUj7");
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        //Informa o tipo de BD que vai se ligar
        adapter.setDatabase(Database.MYSQL);
        //Solicita para mostrar o SQL que está sendo executado
        adapter.setShowSql(true);
        //Caso as tabelas não existam crie
        adapter.setGenerateDdl(true);
        //Qual o tipo de SQL que vai ser usado
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
        //Inicie a conexão
        adapter.setPrepareConnection(true);

        return adapter;
    }
}
