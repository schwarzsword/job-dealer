package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.dto.VacancyDto;
import edu.netcracker.jobdealer.entity.Company;
import edu.netcracker.jobdealer.entity.Vacancy;
import edu.netcracker.jobdealer.exceptions.VacancyNotFoundException;
import edu.netcracker.jobdealer.repository.VacancyRepository;
import edu.netcracker.jobdealer.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("vacancyService")
@Transactional
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;


    @Autowired
    public VacancyServiceImpl(final VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    @Override
    public List<Vacancy> getAll() {
        return vacancyRepository.findAll();
    }

    @Override
    public List<Vacancy> getVacanciesByCompany(Company company) {
        return vacancyRepository.findAllByOwner(company);
    }

    @Override
    public List<Vacancy> getVacanciesByCompanyEmail(String email) {
        return vacancyRepository.findAllByOwner_Account_Email(email);
    }


    @Override
    public Vacancy addVacancy(Vacancy vacancy) {

        Vacancy vacancyToAdd = new Vacancy(
                vacancy.getName(),
                vacancy.getDescription(),
                vacancy.getMoney(),
                vacancy.getRequestedSkills(),
                vacancy.getOwner()
        );

        return vacancyRepository.saveAndFlush(vacancyToAdd);
    }

    @Override
    public void remove(UUID vacancyId) throws VacancyNotFoundException{
        Optional<Vacancy> byId = vacancyRepository.findById(vacancyId);
        if (byId.isPresent()) {
            vacancyRepository.delete(byId.get());
        } else throw new VacancyNotFoundException("Vacancy not found");
    }


    @Override
    public Vacancy updateVacancy(VacancyDto vacancyDTO) {
        Vacancy vacancyToUpdate = vacancyRepository.findByNameAndOwner(vacancyDTO.getName(), vacancyDTO.getOwner());

        if (vacancyDTO.getDescription() != null) {
            vacancyToUpdate.setDescription(vacancyDTO.getDescription());
        }
        if (vacancyDTO.getMoney() != null) {
            vacancyToUpdate.setMoney(vacancyDTO.getMoney());
        }
        if (vacancyDTO.getName() != null) {
            vacancyToUpdate.setName(vacancyDTO.getName());
        }
        if (vacancyDTO.getOwner() != null) {
            vacancyToUpdate.setOwner(vacancyDTO.getOwner());
        }
        if (vacancyDTO.getRequestedSkills() != null) {
            vacancyToUpdate.setRequestedSkills(vacancyDTO.getRequestedSkills());
        }

        return vacancyRepository.saveAndFlush(vacancyToUpdate);
    }

}
