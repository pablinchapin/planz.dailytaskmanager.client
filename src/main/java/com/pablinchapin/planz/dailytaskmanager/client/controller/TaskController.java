/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.planz.dailytaskmanager.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pablinchapin.planz.dailytaskmanager.client.service.MessageDTO;
import com.pablinchapin.planz.dailytaskmanager.client.service.TaskServiceBean;
import com.pablinchapin.planz.dailytaskmanager.client.service.TaskServiceDTO;
import java.io.IOException;
import javax.validation.Valid;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

/**
 *
 * @author pvargas
 */
@Controller
@RequestMapping("/")
public class TaskController {
    
    @Autowired
    private TaskServiceBean service;
    @Autowired
    private ObjectMapper mapper;
    
    @RequestMapping(method = RequestMethod.GET)
    public String findAll(Model model){
        model.addAttribute("taskes", service.findAll());
        model.addAttribute("newTask", new TaskServiceDTO());
        
        return "tasks";
    }
    
    
    @RequestMapping(method = RequestMethod.PUT)
    public String update(@RequestParam Long id, TaskServiceDTO task){
        service.update(id, task);
        return "redirect:/";
    }
    
    
    @RequestMapping(method = RequestMethod.DELETE)
    public String delete(@RequestParam Long id){
        service.delete(id);
        
        return "redirect:/";
    }
    
    
    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("newTask") TaskServiceDTO task){
        service.create(task);
        return "redirect:/";
    }
    
    
    @ExceptionHandler(HttpClientErrorException.class)
    public String handleClientError(HttpClientErrorException ex, Model model) throws IOException {
        MessageDTO dto = mapper.readValue(ex.getResponseBodyAsByteArray(), MessageDTO.class);
        model.addAttribute("error", dto.getMessage());
        return findAll(model);
    }
    
    
}