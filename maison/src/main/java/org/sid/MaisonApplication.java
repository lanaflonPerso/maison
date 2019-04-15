package org.sid;

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
		persRepository.save(new Pers(null, "kamdem theo", "sylvain", 25));
		persRepository.save(new Pers(null, "woupo tchangang", "cyrille", 29));
		persRepository.save(new Pers(null, "totue sefo", "rodrige", 32));
		
		persRepository.findAll().forEach(p->{
			System.out.println(p.getNom() + " a " + p.getAge());
		});
		
	}

}
