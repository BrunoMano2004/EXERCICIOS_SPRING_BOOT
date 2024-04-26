package br.com.alura.screenMatch;

import br.com.alura.screenMatch.model.DadosSerie;
import br.com.alura.screenMatch.service.ConsumoAPI;
import br.com.alura.screenMatch.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		var consumoAPI = new ConsumoAPI();

		var json = consumoAPI.obterDados("https://omdbapi.com/?t=gilmore+girls&Season=1&apikey=1207e02b");
		System.out.println(json);

		ConverteDados converteDados = new ConverteDados();

		DadosSerie dados = converteDados.obterDados(json, DadosSerie.class);

		System.out.println(dados);
	}
}
