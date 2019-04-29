package org.sid.web;

import java.util.Optional;

import javax.validation.Valid;

import org.sid.dao.PersRepository;
import org.sid.entities.Pers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersController {
	@Autowired
	private PersRepository persRepository;
	@GetMapping("/user/index")
	public String index(Model model, 
			@RequestParam(name = "page", defaultValue = "0") int page, 
			@RequestParam(name = "size", defaultValue = "5") int s,
			@RequestParam(name = "motCle", defaultValue = "") String mc) {
			
		Page<Pers> pagePers = 
				persRepository.findByNomContains(mc, PageRequest.of(page, s));
		model.addAttribute("listPers", pagePers.getContent());
		model.addAttribute("pages", new int[pagePers.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("motCle", mc);
		model.addAttribute("size", s);
		
		return "ListePers";	
	}
	
	@GetMapping("/admin/delete")
	public String delete(Long id, int page, String motCle) {	//pr supp il faut 3 param(le num, la page et le motCle en cours 
		persRepository.deleteById(id);
		return "redirect:/user/index?page="+page+"&motCle="+motCle;
		//on supp et on fait une redirection vers la page index
	}
	
	@RequestMapping(value="/admin/form", method=RequestMethod.GET)	
	public String formPers(Model model) {
		model.addAttribute("pers", new Pers());
		return "SaisiePers";
	}

	@RequestMapping(value="/admin/edit", method=RequestMethod.GET)	
	public String formEditPers(Model model, Long id) {
		Optional<Pers> p = persRepository.findById(id);
		  if(p.isPresent()) {					//si p n'est pas null on crée un objet Pers k j'initialise avec p et 
			  									//que j'envois par la suite dans le formulaire via model.
		  Pers pers = p.get();
		  model.addAttribute("pers", pers);
		  }
		return "EditPers";
	}
	
	@RequestMapping(value="/admin/save", method=RequestMethod.POST)	
	public String formSavePers(Model model, @Valid Pers pers, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "SaisiePers";
		persRepository.save(pers);
		return "Confirmation";
	}
	
	@RequestMapping("/")
	public String home() {
		return "redirect:/user/index";
	}
	
	@RequestMapping("/403")
	public String accesDenied() {
		return "403";
	}
	
	@RequestMapping("/404")
	public String resourceNotFound() {
		return "404";
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