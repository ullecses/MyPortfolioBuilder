package com.example.myportfoliobuilder.services;

import com.example.myportfoliobuilder.models.Work;
import com.example.myportfoliobuilder.repositories.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkService {
    @Autowired
    private WorkRepository workRepository;

    public Work saveWork(Work work) {
        return workRepository.save(work);
    }

    public List<Work> getWorksByUserId(Long userId) {
        return workRepository.findAllByUserId(userId);
    }
}