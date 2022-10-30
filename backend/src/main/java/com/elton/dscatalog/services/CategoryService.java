package com.elton.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.elton.dscatalog.entities.Category;
import com.elton.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository catRepository;
	
	@Transactional(readOnly = true)
	public List<Category> findAll() {
		
		return catRepository.findAll();
	}
}
