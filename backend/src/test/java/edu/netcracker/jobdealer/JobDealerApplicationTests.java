package edu.netcracker.jobdealer;

import edu.netcracker.jobdealer.controller.MessageController;
import edu.netcracker.jobdealer.dto.MessageDto;
import edu.netcracker.jobdealer.entity.*;
import edu.netcracker.jobdealer.repository.*;
import edu.netcracker.jobdealer.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class JobDealerApplicationTests {

    @Autowired
    MessageController messageController;

    @Autowired
    MessageService messageService;

    @Autowired
    Mapper mapper;

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

    @Before
    public void prepare() {
        Account userAccount = new Account("4", "4", "ROLE_USER");
        accountRepository.save(userAccount);
        Account companyAccount = new Account("5", "5", "ROLE_COMPANY");
        accountRepository.save(companyAccount);
        Applicant applicant = new Applicant(userAccount);
        applicantRepository.save(applicant);
        Company company = new Company(companyAccount);
        companyRepository.save(company);
        Message message = new Message("hello world", userAccount, companyAccount);
        messageRepository.save(message);
        Resume resume = new Resume(applicant);
        resume.setFirstName("schwarz");
        resume.setResumeName("qwe");
        resumeRepository.save(resume);
        Review review1 = new Review("good", userAccount, companyAccount);
        review1.setRating(10);
        reviewRepository.save(review1);
        Skills skill1 = new Skills("java");
        Skills skill2 = new Skills("javascript");
        skillsRepository.save(skill1);
        skillsRepository.save(skill2);
        SkillToOwner skillToOwner = new SkillToOwner(resume, skill1, 6);
        skillToOwnerRepository.save(skillToOwner);
        Vacancy vacancy = new Vacancy("java dev", "java dev", 100, Arrays.asList(skill1, skill2), company);
        vacancyRepository.save(vacancy);
        Task task = new Task("task", "write smth", vacancy);
        testTaskRepository.save(task);
        Submission submission = new Submission("txt.txt", task, applicant);
        submissionRepository.save(submission);
    }

    @Test
    public void registrationTest() {
        Optional<Account> byEmail = accountRepository.findByEmail("4");
        log.info(byEmail.get().getUsername());
        log.info(byEmail.get().toString());
        assertEquals("4", byEmail.get().getUsername());
    }

    @Test
    public void applicantTest() {
        Optional<Applicant> byAccount = applicantRepository.findByAccount_Email("4");
        log.info("applicant found: " + byAccount.get().getAccount().getEmail());
        assertEquals("4", byAccount.get().getAccount().getEmail());
    }

    @Test
    public void companyTest() {
        Optional<Company> byAccount = companyRepository.findByAccount_Email("5");
        log.info("company found: " + byAccount.get().getAccount().getEmail());
        assertEquals("5", byAccount.get().getAccount().getEmail());
    }

    @Test
    public void messageTest() {
        List<Message> allByMessageDest = messageRepository.findAllByMessageDest_Email("5");
        allByMessageDest.forEach(e -> log.info("message: " + e.getText()));
        log.info("total messages: " + allByMessageDest.size());
        assertEquals(1, allByMessageDest.size());
    }

    @Test
    public void resumeTest() {
        List<Resume> resumes = resumeRepository.findAllByOwner_Account_Email("4");
        resumes.forEach(e -> log.info("resume: " + e.getResumeName()));
        log.info("total resumes: " + resumes.size());
        assertEquals(1, resumes.size());
    }

    @Test
    public void reviewTest() {
        List<Review> reviews = reviewRepository.findAllByReviewDest_Email("5");
        reviews.forEach(e -> log.info("review: " + e.getText() + "; rating: " + e.getRating()));
        log.info("total reviews: " + reviews.size());
        assertEquals(1, reviews.size());
    }

    @Test
    public void skillsTest() {
        List<Skills> skills = skillsRepository.findAllByNameContaining("java");
        skills.forEach(e -> log.info("skill: " + e.getName()));
        log.info("totals skills: " + skills.size());
        assertEquals(2, skills.size());
    }

    @Test
    public void skillToOwnerTestByResume() {
        List<SkillToOwner> qwe = skillToOwnerRepository.findAllByOwner(resumeRepository.findByResumeNameAndOwner_Account_Email("qwe", "4"));
        qwe.forEach(e -> log.info("skill " + e.getSkill().getName() + "; level: " + e.getLevel()));
        log.info("total STO found by resume: " + qwe.size());
        assertEquals(1, qwe.size());
    }

    @Test
    public void skillToOwnerTestBySkillAndLevel() {
        List<SkillToOwner> java = skillToOwnerRepository.findAllBySkillAndLevelGreaterThanEqual(skillsRepository.findByName("java").get(), 4);
        java.forEach(e -> log.info("skill " + e.getSkill().getName() + "; level: " + e.getLevel()));
        log.info("total STO found by skill and level: " + java.size());
        assertEquals(1, java.size());
    }

    @Test
    public void vacancyTestByNameAndCompany() {
        List<Vacancy> java = vacancyRepository.findAllByNameContainingAndOwner_Account_Email("java", "5");
        java.forEach(e -> log.info("vacancy: " + e.getName()));
        log.info("total vacancies found by name and company: " + java.size());
        assertEquals(1, java.size());
    }

    @Test
    public void vacancyTestByMoney() {
        List<Vacancy> money = vacancyRepository.findAllByMoneyIsGreaterThanEqual(100);
        money.forEach(e -> log.info("vacancy: " + e.getName()));
        log.info("total vacancies found by money: " + money.size());
        assertEquals(1, money.size());
    }

    @Test
    public void testTaskTest() {
        Task java_dev = testTaskRepository.findByVacancy(vacancyRepository.findByNameAndOwner_Account_Email("java dev", "5")).get();
        log.info("test task: " + java_dev.getName());
        assertEquals("task", java_dev.getName());
    }

    @Test
    public void submissionTest() {
        Submission java_dev = submissionRepository.findByTaskAndSubmiter_Account_Email(testTaskRepository.findByVacancy(vacancyRepository.findByNameAndOwner_Account_Email("java dev", "5")).get(), "4");
        log.info("submission: " + java_dev.getFilename());
        assertEquals("txt.txt", java_dev.getFilename());
    }

    @Test
    public void messageServiceTest() {
        List<Message> userMessages = messageService.getUserMessages("5");
        List<MessageDto> dtos = userMessages.stream().map(e -> mapper.map(e, MessageDto.class)).collect(Collectors.toList());
        log.info(dtos.toString());
    }

    @After
    public void finish() {
        submissionRepository.deleteByTaskAndSubmiter_Account_Email(testTaskRepository.findByVacancy(vacancyRepository.findByNameAndOwner_Account_Email("java dev", "5")).get(), "4");
        testTaskRepository.deleteByVacancy(vacancyRepository.findByNameAndOwner_Account_Email("java dev", "5"));
        vacancyRepository.deleteByNameAndOwner_Account_Email("java dev", "5");
        skillToOwnerRepository.deleteAllByOwner(resumeRepository.findByResumeNameAndOwner_Account_Email("qwe", "4"));
        skillsRepository.deleteAllByNameContaining("java");
        reviewRepository.deleteAllByReviewDest_Email("5");
        resumeRepository.deleteAllByOwner_Account_Email("4");
        messageRepository.deleteAllByMessageDest_Email("5");
        applicantRepository.deleteByAccount_Email("4");
        companyRepository.deleteByAccount_Email("4");
        accountRepository.deleteByEmail("4");
        accountRepository.deleteByEmail("5");
    }


}
