package com.currychoco.product.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.ModelAndView;

import com.currychoco.product.domain.Product;
import com.currychoco.product.service.ProductService;

@Controller
public class ProductController {
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping(value = "/products/new")
	public String createForm() {
		return "products/createProductForm";
	}
	
	@PostMapping(value = "/products/new")
	public String create(ProductForm form) {
		Product product = new Product();
		product.setName(form.getName());
		
		productService.addProduct(product);
		
		return "redirect:/products/new";
	}
	
	@GetMapping(value = "/products")
	public String list(Model model) {
		List<Product> products = productService.findProdcuts();
		model.addAttribute("products222", products);
		
		return "products/productList";
	}
	
	/*
	@GetMapping(value = "/products")
	public ModelAndView listWithModelAndView() {
		List<Product> products = productService.findProdcuts();
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("products/productList");
		mav.addObject("products222", products);
		
		return mav;
	}
	*/
	
	@GetMapping(value = "/products/{id}/delete")
	public String delete(@PathVariable(name = "id") long id) {
		productService.deleteById(id);
		return "redirect:/products";
	}
}
