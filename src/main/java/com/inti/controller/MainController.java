package com.inti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inti.model.Acteur;
import com.inti.services.ActeurRepository;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("acteur")
@Slf4j
public class MainController {
	
	@Autowired
	ActeurRepository acr;
	
	@GetMapping("acteurs")
	public List<Acteur> getActeurs()
	{
		log.info("Voici la liste des acteurs :");
		return acr.findAll();
	}
	
	@GetMapping("acteur/{id}")
	public Acteur getActeur(@PathVariable long id)
	{
		long idMax = acr.findMaxId();
		if (id > 0 && id <= idMax)
		{
		log.info("Voici l'acteur "+id);
		return acr.findById(id).get();
		}
		log.info("L'acteur avec l'id "+id+"n'a pas pu être trouvé");
		return null;
	}
	
	@PostMapping("saveActeur")
	public boolean saveActeur(@RequestBody Acteur acteur)
	{
		if (acteur != null)
		{
			log.info("L'acteur "+acteur+" a pu être sauvegardé.");
			acr.save(acteur);
			return true;
		}
		log.error("L'acteur "+acteur+" n'a pas pu être sauvegardé !");
		return false;
	}
	
	@DeleteMapping("deleteActeur/{id}")
	public boolean deleteActeur(@PathVariable long id)
	{
		long idMax = acr.findMaxId();
		if (id > 0 && id <= idMax)
		{
			log.info("Suppression de l'acteur "+id+" effectuée");
			acr.deleteById(id);
			return true;
		}
		log.error("L'acteur n'a pas été supprimé");
		return false;
	}

	@PutMapping("update/{id}")
	public Acteur updateActeur(@RequestBody Acteur nouvelActeur, @PathVariable long id) {
		return acr.findById(id).map(acteur -> {
			acteur.setNom(nouvelActeur.getNom());
			acteur.setPrenom(nouvelActeur.getPrenom());
			acteur.setEmail(nouvelActeur.getEmail());
			acteur.setVille(nouvelActeur.getVille());
			return acr.save(acteur);
		}).orElseGet(() -> {
			return acr.save(nouvelActeur);
		});
	}
	
	@GetMapping("name/{nom}")
	public Acteur getActeurByNom(@PathVariable String nom)
	{
		return acr.findByNom(nom);
	}
	
	@GetMapping("count")
	public int count()
	{
		log.info("Voici le nombre total d'acteurs dans la BDD :");
		return acr.findAllActeur();
	}
	
	@GetMapping("updateActeurNom")
	public void updateActeurNomById(@RequestParam String nom, @RequestParam long id)
	{
		acr.updateActeurNomById(nom, id);
	}
}
