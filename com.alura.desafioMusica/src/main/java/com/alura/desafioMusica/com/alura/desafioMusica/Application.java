package com.alura.desafioMusica.com.alura.desafioMusica;

import com.alura.desafioMusica.com.alura.desafioMusica.principal.Principal;
import com.alura.desafioMusica.com.alura.desafioMusica.repository.ArtistaRepository;
import com.alura.desafioMusica.com.alura.desafioMusica.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	ArtistaRepository artistaRepository;

	@Autowired
	MusicaRepository musicaRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(artistaRepository, musicaRepository);
		principal.exibeMenu();
	}
}
