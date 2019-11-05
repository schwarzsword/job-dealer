package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.dto.ApplicantDto;
import edu.netcracker.jobdealer.repository.ApplicantRepository;
import edu.netcracker.jobdealer.service.ApplicantService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final Mapper mapper;

    @Autowired
    public ApplicantServiceImpl(ApplicantRepository applicantRepository, Mapper mapper) {
        this.applicantRepository = applicantRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ApplicantDto> getAllApplicants() {
        return applicantRepository.findAll().stream()
                .map(applicant -> mapper.map(applicant, ApplicantDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ApplicantDto getApplicantById(UUID id) {
        return mapper.map(applicantRepository.getById(id), ApplicantDto.class);
    }
}
