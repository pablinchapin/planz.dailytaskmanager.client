/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pablinchapin.planz.dailytaskmanager.client.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author pvargas
 */
@Service
public class TaskServiceBean {
        
    @Value("${resource.tasks}")
    private String resource;
    @Value("${resource.tasks}/{id}")
    private String idResource;
    
    @Autowired
    private RestTemplate restTemplate;
    
    
    
    public List<TaskServiceDTO> findAll(){
        return Arrays.stream(restTemplate.getForObject(resource, TaskServiceDTO[].class)).collect(Collectors.toList());
    }
    
    public TaskServiceDTO create(TaskServiceDTO task){
        return restTemplate.postForObject(resource, task, TaskServiceDTO.class);
    }
    
    public TaskServiceDTO update(Long id, TaskServiceDTO task){
        return restTemplate.exchange(idResource, HttpMethod.PUT, new HttpEntity<>(task), TaskServiceDTO.class, id).getBody();
    }
    
    public void delete(Long id){
        restTemplate.delete(idResource, id);
    }
    
}
