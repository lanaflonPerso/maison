package org.sid.dao;

import org.sid.entities.Pers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersRepository extends JpaRepository<Pers, Long>{
	
	public Page<Pers> findByNomContains(String mc, Pageable pageable);

}
