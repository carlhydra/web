/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springmvc.springmongodbweb.controllers;

 
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.springmvc.springmongodbweb.service.CustomUserDetailsService;

import com.springmvc.springmongodbweb.models.Product;
import com.springmvc.springmongodbweb.repositories.ProductRepository;

/**
 *
 * @author didin
 */
@Controller
public class ProductController {

    @Autowired 
    ProductRepository productRepository;
    @Autowired
    private CustomUserDetailsService userService;


    @RequestMapping("/product")
    public ModelAndView product(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Product user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName", "Welcome " + user.getFullname());
        modelAndView.addObject("products", productRepository.findAll());
        modelAndView.setViewName("product");
        return modelAndView;
    }
    @RequestMapping("/file")
    public ModelAndView file(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Product user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName", "Welcome " + user.getFullname());
        modelAndView.setViewName("file");
        return modelAndView;
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

    @RequestMapping(value = {"/show","/show/{id}"}, method = RequestMethod.GET)
    public ModelAndView show(@PathVariable String id) {
    	ModelAndView modelAndView = new ModelAndView();
    	Optional<Product> product = productRepository.findById(id);
    	 modelAndView.addObject("product", product.get());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Product user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("fullName", "Welcome " + user.getFullname());
        modelAndView.addObject("currentUser", user);
        modelAndView.setViewName("show");
        return modelAndView;
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam String id) {
        Product product = productRepository.findById(id).orElse(null);
        productRepository.delete(product);

        return "redirect:/product";
    }
    
    @RequestMapping("/edit/{id}")
    public ModelAndView edit(@PathVariable String id, Model model) {
    	ModelAndView modelAndView = new ModelAndView();
    	Optional<Product> product = productRepository.findById(id);
    	 modelAndView.addObject("product", product.get());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Product user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("fullName", "Welcome " + user.getFullname());
        modelAndView.addObject("currentUser", user);
        modelAndView.setViewName("edit");
        return modelAndView;
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
       Product product = productRepository.findById(id).orElse(null);
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
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView();
        Product user = new Product();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("signup");
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid Product user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Product userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("signup");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new Product());
            modelAndView.setViewName("login");

        }
        return modelAndView;
    }

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Product user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", user);
        modelAndView.addObject("fullName", "Welcome " + user.getFullname());
        modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
        modelAndView.setViewName("dashboard");
        return modelAndView;
    }
    
    @RequestMapping(value = {"/","/home"}, method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }
    @RequestMapping(value = {"/hello"}, method = RequestMethod.GET)
    public ModelAndView hello() {
    	  ModelAndView modelAndView = new ModelAndView();
          Authentication auth = SecurityContextHolder.getContext().getAuthentication();
          Product user = userService.findUserByEmail(auth.getName());
          modelAndView.addObject("currentUser", user);
          modelAndView.addObject("fullName", "Welcome " + user.getFullname());
        modelAndView.setViewName("hello");
        return modelAndView;
    }
}
