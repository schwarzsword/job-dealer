package edu.netcracker.jobdealer.service.impl;


import edu.netcracker.jobdealer.repository.ResumeRepository;
import edu.netcracker.jobdealer.service.ResumeService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private Mapper mapper;

    @Autowired
    public ResumeServiceImpl(ResumeRepository resumeRepository, Mapper mapper) {
        this.resumeRepository = resumeRepository;
        this.mapper = mapper;
    }
  //TODO написать методы
  
}
