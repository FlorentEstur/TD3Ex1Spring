package com.inti.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.inti.model.Acteur;

@Repository
@Transactional
public interface ActeurRepository extends JpaRepository<Acteur, Long> {

	@Query(value = "select max(id) from acteur", nativeQuery = true)
	long findMaxId();
	
	Acteur findByNom(String nom);
	
	@Query(value ="select count(*) from acteur", nativeQuery = true)
	int findAllActeur();
	
	@Modifying
	@Query(value ="update acteur set nom=:nom where id=:id", nativeQuery = true)
	void updateActeurNomById(@Param("nom") String nom, @Param("id") long id);
}
