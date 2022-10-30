package com.elton.dscatalog.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elton.dscatalog.entities.Category;
import com.elton.dscatalog.services.CategoryService;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {
	
	@Autowired
	private CategoryService catService;
	@GetMapping
	public ResponseEntity<List<Category>> finAll(){
		List<Category> list = catService.findAll();
	
		return ResponseEntity.ok().body(list);
	}
}
