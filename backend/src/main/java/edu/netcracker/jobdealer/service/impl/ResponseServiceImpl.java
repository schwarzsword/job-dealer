package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Applicant;
import edu.netcracker.jobdealer.entity.Response;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.ApplicantNotFoundException;
import edu.netcracker.jobdealer.exceptions.NoPermissionException;
import edu.netcracker.jobdealer.exceptions.ResponseNotFoundException;
import edu.netcracker.jobdealer.exceptions.VacancyNotFoundException;
import edu.netcracker.jobdealer.repository.ApplicantRepository;
import edu.netcracker.jobdealer.repository.ResponseRepository;
import edu.netcracker.jobdealer.repository.VacancyRepository;
import edu.netcracker.jobdealer.service.ResponseService;
import edu.netcracker.jobdealer.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ResponseServiceImpl implements ResponseService {

    private final VacancyRepository vacancyRepository;
    private final ApplicantRepository applicantRepository;
    private final ResponseRepository responseRepository;


    public ResponseServiceImpl(VacancyRepository vacancyRepository, ApplicantRepository applicantRepository, ResponseRepository responseRepository) {
        this.vacancyRepository = vacancyRepository;
        this.applicantRepository = applicantRepository;
        this.responseRepository = responseRepository;
    }

    @Override
    public List<Response> getRespondents(UUID vacancyId, String email) {
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow(VacancyNotFoundException::new);
        if (vacancy.getOwner().getAccount().getEmail().equals(email)) {
            return vacancy.getResponses();
        } else throw new NoPermissionException();
    }

    @Override
    public List<Response> getResponses(String email) {
        Applicant applicant = applicantRepository.findByAccountEmail(email).orElseThrow(ApplicantNotFoundException::new);
        return applicant.getResponses();
    }

    @Override
    public void apply(UUID vacancyId, String email) {
        Applicant applicant = applicantRepository.findByAccountEmail(email).orElseThrow(ApplicantNotFoundException::new);
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow(VacancyNotFoundException::new);
        Response response = new Response(null, "APPLIED", applicant, vacancy);
        responseRepository.save(response);
        vacancy.addResponse(response);
        vacancyRepository.save(vacancy);
        applicant.addResponse(response);
        applicantRepository.save(applicant);
    }

    @Override
    public boolean isApplied(UUID id, String email) {
        Applicant applicant = applicantRepository.findByAccountEmail(email).orElseThrow(ApplicantNotFoundException::new);
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(VacancyNotFoundException::new);
        return vacancy.getResponses()
                .stream().map(Response::getApplicant)
                .map(Applicant::getId)
                .collect(Collectors.toList())
                .contains(applicant.getId());
    }

    @Override
    public void setStatus(UUID id, String status, String email) {
        Response response = responseRepository.findById(id).orElseThrow(ResponseNotFoundException::new);
        if (response.getVacancy().getOwner().getAccount().getEmail().equals(email)) {
            response.setStatus(status);
            //TODO: send message to user about changing status
            responseRepository.save(response);
        } else throw new NoPermissionException();
    }

}
