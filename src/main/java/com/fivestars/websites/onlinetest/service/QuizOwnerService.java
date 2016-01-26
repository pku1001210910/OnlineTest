package com.fivestars.websites.onlinetest.service;

import java.io.Serializable;
import java.util.List;

import com.fivestars.websites.onlinetest.model.QuizOwnership;

public interface QuizOwnerService {
	
    public Serializable save(QuizOwnership ownership); 
    
    public void saveOrUpdate(QuizOwnership ownership); 
    
    public List<QuizOwnership> loadByUser(String userName); 
    
    public void saveOrUpdateBatch(List<QuizOwnership> ownerships);
    
    public void delete(Integer ownershipId);
    
}