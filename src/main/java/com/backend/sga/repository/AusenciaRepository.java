package com.backend.sga.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.backend.sga.model.Ausencia;

@Repository
public interface AusenciaRepository extends PagingAndSortingRepository<Ausencia, Long>{

	public List<Ausencia> findByProfessorId(Long id);
}
