package edu.netcracker.jobdealer.controller.test;

import edu.netcracker.jobdealer.controller.ResumeController;
import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.entity.Resume;
import edu.netcracker.jobdealer.repository.*;
import edu.netcracker.jobdealer.service.ResumeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ResumeController.class)
@ActiveProfiles("test")
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

    @MockBean
    private ResumeService resumeService;

    @Test
    public void contextLoads() throws Exception {
        assertThat(resumeController).isNotNull();
    }

    @Test
    public void testAddResumeForUser() throws Exception {
        Account account = new Account("1242", "test@mail.ru", "ROLE_USER");
        account.setUsername("testUser");
        Applicant applicant = new Applicant(account);
        Resume resume = new Resume(applicant);

        List<Resume> allResumes = Arrays.asList(resume);



        mockMvc.perform(get("localhost:8080/api/user/{login}/resumes/", "testUser")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(resume));
    }


}
