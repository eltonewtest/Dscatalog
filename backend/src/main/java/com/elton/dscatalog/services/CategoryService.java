package com.elton.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.hibernate.ResourceClosedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elton.dscatalog.dto.CategoryDTO;
import com.elton.dscatalog.entities.Category;
import com.elton.dscatalog.repositories.CategoryRepository;
import com.elton.dscatalog.services.exceptions.DatabaseException;
import com.elton.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository catRepository;
	
	@Transactional(readOnly = true)
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
		Page<Category> list = catRepository.findAll(pageRequest);
		
		//Tranformar lista de Category em CategpryDto
		return list.map( x -> new CategoryDTO(x) );
	}
	
	
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> obj =  catRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = catRepository.save(entity);
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			
			//método atualizado -> getReferenceById
			Category entity = catRepository.getReferenceById(id);
			entity.setName(dto.getName());
			entity = catRepository.save(entity);
			return new CategoryDTO(entity);
			
		} catch (EntityNotFoundException e) {
			
			throw new ResourceNotFoundException("Id not found " + id);
			
		}
		
	}


	public void delete(Long id) {
		try {
			catRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceClosedException("Id not Found");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity Violation");
		}
		
	}
	
	
	
	
	
	
	
	
	
}
