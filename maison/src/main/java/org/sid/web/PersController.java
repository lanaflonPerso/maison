package org.sid.web;

import java.util.Optional;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.sid.dao.PersRepository;
import org.sid.entities.Pers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.SystemProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PersController {
	
	@Autowired															//Autowired permet l'injection d'un objet
	private PersRepository persRepository;
	
	@Value("${dir.images}")
	private String imageDir;
	
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
	
	@GetMapping(value="/admin/form")										//new enregistrement
	public String formPers(Model model) {
		model.addAttribute("pers", new Pers());
		return "SaisiePers";
	}
	
	@GetMapping(value="/admin/edit")										//new modification
	public String formEditPers(Model model, Long id) {
		Optional<Pers> p = persRepository.findById(id);
		  if(p.isPresent()) {					//si p n'est pas null on crée un objet Pers k j'initialise avec p et 
			  									//que j'envois par la suite dans le formulaire via model. ou faire simplement
		  Pers pers = p.get();					// Pers pers = persRepository.findById(id).get();
		  model.addAttribute("pers", pers);
		  }
		return "EditPers";
	}
	
	@PostMapping(value="/admin/save")										//Sauvegarder un enregistrement	
	public String formSavePers(Model model, @Valid Pers pers, BindingResult bindingResult, @RequestParam(name="picture")MultipartFile file) throws Exception{
		
		if (bindingResult.hasErrors()) {
			return "SaisiePers";
		}
		try {
			if(!(file.isEmpty())) { pers.setPhoto(file.getOriginalFilename()); }			//sauvegarder nom_photo sur notre server de BD
		} catch (Exception e) {
			
		}
				
		
		persRepository.save(pers);
		
	
		if(!(file.isEmpty())) {
			pers.setPhoto(file.getOriginalFilename());
			file.transferTo(new File(imageDir + pers.getId()));						//sauvegarder photo sur server distant avec id
		}
		
		return "Confirmation";
	}
	
	@RequestMapping(value = {"/user/getPhoto", "/admin/getPhoto"}, produces=MediaType.IMAGE_JPEG_VALUE)		//la photo sera envoyée en format JPEG
	@ResponseBody
	public byte[] getPhoto(Long id) throws Exception {
		File f = new File(imageDir+id);
		return IOUtils.toByteArray(new FileInputStream(f));					//le resultat de la methode est envoyé ds le corps de la reponse. 
													//cad est retourné ds la partie body de la page html ki a fait la demande de cette photo 
	}
	
	@GetMapping("/admin/delete")											//delete enregistrement
	public String delete(Long id, int page, String motCle) {	//pr supp il faut 3 param(le num, la page et le motCle en cours 
		persRepository.deleteById(id);
		return "redirect:/user/index?page="+page+"&motCle="+motCle;
		//on supp et on fait une redirection vers la page index
	}
	
	@GetMapping("/")						//home par defaut
	public String home() {
		return "redirect:/user/index";
	}
	
	@GetMapping("/403")						//on est authentifié mais on n'a pas le droit
	public String accesDenied() {
		return "403";
	}
	
	@GetMapping("/404")						//la ressource n'est pa disponible
	public String resourceNotFound() {
		return "404";
	}
	
	@GetMapping(value="/login")				//page de connexion
	public String login() {
		return "login";
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