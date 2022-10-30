package com.elton.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elton.dscatalog.entities.Category;
import com.elton.dscatalog.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository catRepository;
	
	public List<Category> findAll() {
		
		return catRepository.findAll();
	}
}
