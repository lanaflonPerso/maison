package org.sid;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.sid.dao.PersRepository;
import org.sid.entities.Pers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MaisonApplication implements CommandLineRunner{
	@Autowired
	private PersRepository persRepository;

	public static void main(String[] args) {
		SpringApplication.run(MaisonApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		DateFormat df = new SimpleDateFormat("YYYY-MM-DD");
		persRepository.save(new Pers(null, "annick", "carmel", 20, df.parse("2000-11-29"), "annic@gmail.com", "moi.jpg"));
		//persRepository.save(new Pers(null, "mbappe", "sanchao", 18, df.parse("2002-07-17"), "gueken@gmail.com", "toi.jpg"));
		//persRepository.save(new Pers(null, "mendy", "ferland", 18, df.parse("1999-06-11"), "bappe@gmail.com", "mbappe.jpg"));
		//persRepository.save(new Pers(null, "antony", "lopes", 28, df.parse("1997-02-28"), "antonhy@gmail.com", "lopez.jpg"));
		//persRepository.save(new Pers(null, "fekir", "nabil", 25, df.parse("1996-10-31"), "fekir@gmail.com", "nabil.jpg"));

		/*
		 * persRepository.findAll().forEach(p->{ System.out.println(p.getNom() + " a " +
		 * p.getAge()); });
		 */
		
	}

}
