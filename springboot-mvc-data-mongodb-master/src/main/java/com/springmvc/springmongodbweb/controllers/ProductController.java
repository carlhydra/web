/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springmvc.springmongodbweb.controllers;

import com.springmvc.springmongodbweb.models.Product;
import com.springmvc.springmongodbweb.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author didin
 */
@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @RequestMapping("/product")
    public String product(Model model) {
        model.addAttribute("products", productRepository.findAll());
        return "product";
    }

    @RequestMapping("/create")
    public String create(Model model) {
        return "create";
    }

    @RequestMapping("/save")
    public String save(@RequestParam String userName,@RequestParam String password,
    		@RequestParam String email,
    		@RequestParam String firstName, @RequestParam String lastName,
    		@RequestParam String middleName, 
    		@RequestParam String address1,
    		@RequestParam String address2,
    		@RequestParam String city,
    		@RequestParam String state,
    		@RequestParam String zip,
    		@RequestParam String prodDesc, @RequestParam String prodImage) {
        Product product = new Product();
        product.setUserName(userName);
        product.setProdDesc(prodDesc);
        product.setProdImage(prodImage);
        product.setFirstName(firstName);
        product.setLastName(lastName);
        product.setMiddleName(middleName);
        product.setAddress1(address1);
        product.setAddress2(address2);
        product.setCity(city);
        product.setState(state);
        product.setZip(zip);
        product.setEmail(email);
        product.setPassword(password);
        
        productRepository.save(product);

        return "redirect:/show/" + product.getId();
    }

    @RequestMapping("/show/{id}")
    public String show(@PathVariable String id, Model model) {
        model.addAttribute("product", productRepository.findOne(id));
        return "show";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam String id) {
        Product product = productRepository.findOne(id);
        productRepository.delete(product);

        return "redirect:/product";
    }
    
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("product", productRepository.findOne(id));
        return "edit";
    }
    
    @RequestMapping("/update")
    public String update(@RequestParam String id,String userName,@RequestParam String password,
    		@RequestParam String email,
    		@RequestParam String firstName, @RequestParam String lastName,
    		@RequestParam String middleName, 
    		@RequestParam String address1,
    		@RequestParam String address2,
    		@RequestParam String city,
    		@RequestParam String state,
    		@RequestParam String zip,
    		@RequestParam String prodDesc, @RequestParam String prodImage) {
        Product product = productRepository.findOne(id);
      //  product.setProdName(prodName);
      
        product.setUserName(userName);
        product.setProdDesc(prodDesc);
        product.setProdImage(prodImage);
        product.setFirstName(firstName);
        product.setLastName(lastName);
        product.setMiddleName(middleName);
        product.setAddress1(address1);
        product.setAddress2(address2);
        product.setCity(city);
        product.setState(state);
        product.setZip(zip);
        product.setEmail(email);
        product.setPassword(password);
        productRepository.save(product);

        return "redirect:/show/" + product.getId();
    }
}
