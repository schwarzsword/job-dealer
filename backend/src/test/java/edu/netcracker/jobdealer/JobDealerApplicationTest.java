package edu.netcracker.jobdealer;

import edu.netcracker.jobdealer.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class JobDealerApplicationTest {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ApplicantRepository applicantRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    ResumeRepository resumeRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    SkillsRepository skillsRepository;
    @Autowired
    SkillToOwnerRepository skillToOwnerRepository;
    @Autowired
    SubmissionRepository submissionRepository;
    @Autowired
    TestTaskRepository testTaskRepository;
    @Autowired
    VacancyRepository vacancyRepository;

    @Test
    void contextLoads() {
        log.info("test");
    }


}
