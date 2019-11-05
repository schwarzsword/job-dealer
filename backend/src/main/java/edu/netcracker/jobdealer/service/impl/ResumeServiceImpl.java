package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.dto.ResumeDto;
import edu.netcracker.jobdealer.repository.ResumeRepository;
import edu.netcracker.jobdealer.service.ResumeService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private Mapper mapper;

    @Autowired
    public ResumeServiceImpl(ResumeRepository resumeRepository, Mapper mapper) {
        this.resumeRepository = resumeRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ResumeDto> getAllResumes() {
        return resumeRepository.findAll().stream()
                .map(resume -> mapper.map(resume, ResumeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ResumeDto getResumeById(UUID id) {
        return mapper.map(resumeRepository.getOne(id), ResumeDto.class);
    }
}
