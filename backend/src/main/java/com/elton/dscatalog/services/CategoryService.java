package com.elton.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elton.dscatalog.dto.CategoryDto;
import com.elton.dscatalog.entities.Category;
import com.elton.dscatalog.repositories.CategoryRepository;
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
	
	
	
	
	
	
	
	
	
}
