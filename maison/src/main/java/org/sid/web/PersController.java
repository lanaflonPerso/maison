package org.sid.web;

import java.lang.ProcessBuilder.Redirect;

import org.sid.dao.PersRepository;
import org.sid.entities.Pers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersController {
	@Autowired
	private PersRepository persRepository;

	@GetMapping("/index")
	public String index(Model model, 
			@RequestParam(name = "page", defaultValue = "0") int page, 
			@RequestParam(name = "size", defaultValue = "6") int s,
			@RequestParam(name = "motCle", defaultValue = "") String mc) {
			
		Page<Pers> pagePers = 
				persRepository.findByNom(mc, PageRequest.of(page, s));
		model.addAttribute("listPers", pagePers.getContent());
		model.addAttribute("pages", new int[pagePers.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		model.addAttribute("size", s);
		
		return "habitants";	
	}
	
	@GetMapping("/delete")
	public String delete(Long id, int page, String motCle) {	//pr supp il faut 3 param(le num, la page et le motCle en cours 
		persRepository.deleteById(id);
		return "redirect:/index?page="+page+"&motCle="+motCle;
		//on supp et on fait une redirection vers la page index
	}
	
}




/* cette methode retourne la liste entiere des personnes
 *
 @GetMapping("/index")
	public String index(Model model, 
			@RequestParam(name = "page", defaultValue = "0") int page) {
		
		//List<Pers> pers = persRepository.findAll();							//on recupere la liste des personne
		
		Page<Pers> pagePers = persRepository.findAll(PageRequest.of(page, 5));	//ici on affiche une page personne
		model.addAttribute("listPers", pagePers.getContent());
		model.addAttribute("pages", new int[pagePers.getTotalPages()]);			//pr afficher les liens vers les autre pages
		model.addAttribute("currentPage", page);								//pour garder la page courant en couleur
		
		return "habitants";	
	}
 */