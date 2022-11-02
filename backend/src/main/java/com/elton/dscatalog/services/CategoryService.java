package com.elton.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.hibernate.ResourceClosedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elton.dscatalog.dto.CategoryDto;
import com.elton.dscatalog.entities.Category;
import com.elton.dscatalog.repositories.CategoryRepository;
import com.elton.dscatalog.services.exceptions.DatabaseException;
import com.elton.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository catRepository;
	
	@Transactional(readOnly = true)
	public List<CategoryDto> findAll() {
		List<Category> list = catRepository.findAll();
		
		//Tranformar lista de Category em CategpryDto
		return list.stream().map(x -> new CategoryDto(x) ).collect(Collectors.toList());
	}
	
	
	@Transactional(readOnly = true)
	public CategoryDto findById(Long id) {
		Optional<Category> obj =  catRepository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
		return new CategoryDto(entity);
	}

	@Transactional
	public CategoryDto insert(CategoryDto dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = catRepository.save(entity);
		return new CategoryDto(entity);
	}

	@Transactional
	public CategoryDto update(Long id, CategoryDto dto) {
		try {
			
			//método atualizado -> getReferenceById
			Category entity = catRepository.getReferenceById(id);
			entity.setName(dto.getName());
			entity = catRepository.save(entity);
			return new CategoryDto(entity);
			
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
