package api_consumo_arquivos.api_consumo_arquivos;

import api_consumo_arquivos.api_consumo_arquivos.property.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)
public class ApiConsumoArquivosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiConsumoArquivosApplication.class, args);
	}

}
