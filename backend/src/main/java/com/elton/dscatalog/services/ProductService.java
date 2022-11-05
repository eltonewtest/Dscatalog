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
import com.elton.dscatalog.dto.ProductDTO;
import com.elton.dscatalog.entities.Category;
import com.elton.dscatalog.entities.Product;
import com.elton.dscatalog.repositories.CategoryRepository;
import com.elton.dscatalog.repositories.ProductRepository;
import com.elton.dscatalog.services.exceptions.DatabaseException;
import com.elton.dscatalog.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository proRepository;
	
	@Autowired
	private CategoryRepository catRepository;
	
	
	@Transactional(readOnly = true)
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
		Page<Product> list = proRepository.findAll(pageRequest);
		
		//Tranformar lista de Product em CategpryDto
		return list.map( x -> new ProductDTO(x) );
	}
	
	
	@Transactional(readOnly = true)
	public ProductDTO findById(Long id) {
		Optional<Product> obj =  proRepository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));
		return new ProductDTO(entity, entity.getCategories());
	}

	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		copyDtoEntity(dto, entity );
		entity = proRepository.save(entity);
		return new ProductDTO(entity);
	}


	@Transactional
	public ProductDTO update(Long id, ProductDTO dto) {
		try {
			
			//método atualizado -> getReferenceById
			Product entity = proRepository.getReferenceById(id);
			copyDtoEntity(dto, entity );
			entity = proRepository.save(entity);
			return new ProductDTO(entity);
			
		} catch (EntityNotFoundException e) {
			
			throw new ResourceNotFoundException("Id not found " + id);
			
		}
		
	}


	public void delete(Long id) {
		try {
			proRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceClosedException("Id not Found");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity Violation");
		}
		
	}
	
	private void copyDtoEntity(ProductDTO dto, Product entity) {
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setDate(dto.getDate());
		entity.setImgUrl(dto.getImgUrl());
		entity.setPrice(dto.getPrice());
		

		for (CategoryDTO catDto : dto.getCategories()) {
			Category category = catRepository.getReferenceById(catDto.getId());
			entity.getCategories().add(category);
		}

	}

}
