package edu.netcracker.jobdealer.controller.test;

import edu.netcracker.jobdealer.controller.ResumeController;
import edu.netcracker.jobdealer.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(ResumeController.class)
@ContextConfiguration
public class ResumeControllerTest {

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private CompanyRepository companyRepository;

    @MockBean
    private ResumeRepository resumeRepository;

    @MockBean
    private ApplicantRepository applicantRepository;

    @MockBean
    private VacancyRepository vacancyRepository;

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private ReviewRepository reviewRepository;

    @MockBean
    private TestTaskRepository testTaskRepository;

    @MockBean
    private SubmissionRepository submissionRepository;

    @Autowired
    private ResumeController resumeController;

    @Autowired
    private MockMvc mockMvc;

//    @Autowired
//    private TestEntityManager entityManager;

//    @MockBean
//    private ResumeService service;

    @Test
    public void contextLoads() throws Exception {
        assertThat(resumeController).isNotNull();
    }

//    @Test
//    public void testAllResumesOfUserReturned() throws Exception {
//        Account account = new Account("1242", "test@mail.ru", "ROLE_USER");
//        account.setUsername("testUser");
//        entityManager.persist(account);
//        entityManager.flush();
//
//        Applicant applicant = new Applicant(account);
//        entityManager.persist(applicant);
//        entityManager.flush();
//
//        Resume resume = new Resume(applicant);
//        entityManager.persist(resume);
//        entityManager.flush();
//
//        mockMvc.perform(get("api/user/{login}/resumes/", "testUser")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(resume));
//    }


}
