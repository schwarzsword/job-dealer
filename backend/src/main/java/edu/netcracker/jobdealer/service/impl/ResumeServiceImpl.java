package edu.netcracker.jobdealer.service.impl;


import edu.netcracker.jobdealer.entity.Resume;
import edu.netcracker.jobdealer.exceptions.NotImplementedMethodException;
import edu.netcracker.jobdealer.service.ResumeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ResumeServiceImpl implements ResumeService {

    @Override
    public Resume add(Resume resume) {
        throw new NotImplementedMethodException("Method is not implemented");
    }

    @Override
    public Resume update(String resumeName, Resume resume, String email) {
        throw new NotImplementedMethodException("Method is not implemented");
    }

    @Override
    public void remove(String resumeName, String email) {
        throw new NotImplementedMethodException("Method is not implemented");
    }

    @Override
    public List<Resume> getAllResumeOfUser(String login) {
        throw new NotImplementedMethodException("Method is not implemented");
    }
}
