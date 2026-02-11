package org.unibl.etf.ip.backendapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.backendapp.dto.InternshipDTO;
import org.unibl.etf.ip.backendapp.entity.Company;
import org.unibl.etf.ip.backendapp.entity.Internship;
import org.unibl.etf.ip.backendapp.repository.CompanyRepository;
import org.unibl.etf.ip.backendapp.repository.InternshipRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InternshipService {

    private InternshipRepository internshipRepository;
    private ModelMapper modelMapper;
    private CompanyRepository companyRepository;

    public InternshipService(InternshipRepository internshipRepository, ModelMapper modelMapper, CompanyRepository companyRepository) {
        this.internshipRepository = internshipRepository;
        this.modelMapper = modelMapper;
        this.companyRepository = companyRepository;
    }

    public List<InternshipDTO> getAll() {
        return internshipRepository.findAll().stream().map(internship -> modelMapper.map(internship, InternshipDTO.class)).collect(Collectors.toList());
    }

    public List<InternshipDTO> getAllByCompanyId(int companyId) {
        return internshipRepository.findByCompanyId(companyId).stream().map(internship -> modelMapper.map(internship, InternshipDTO.class)).collect(Collectors.toList());
    }

   public List<InternshipDTO> getAllAvailable() {
        return internshipRepository.findByAvailable(true).stream().map(internship -> modelMapper.map(internship, InternshipDTO.class)).collect(Collectors.toList());
   }

   public InternshipDTO getById(int id) {
        return modelMapper.map(internshipRepository.findById(id).orElse(null), InternshipDTO.class);
   }

   public InternshipDTO create(InternshipDTO internshipDTO) {
       Company company = companyRepository.findById(internshipDTO.getCompanyId())
               .orElseThrow(() -> new RuntimeException("Company does not exist."));
       Internship internship = modelMapper.map(internshipDTO, Internship.class);
       internship.setCompany(company);
       Internship internshipSaved = internshipRepository.save(internship);
       return modelMapper.map(internshipSaved, InternshipDTO.class);
   }

   public boolean delete(Integer id)
   {
       if(internshipRepository.existsById(id)) {
           internshipRepository.deleteById(id);
           return true;
       }
       return false;
   }

   public InternshipDTO update(Integer id, InternshipDTO internshipDTO) {
        Internship internship = internshipRepository.findById(id).orElseThrow(() -> new RuntimeException("Application does not exist."));
      if(internshipDTO.getAbout() != null) {
          internship.setAbout(internshipDTO.getAbout());
      }
      if(internshipDTO.getName() != null) {
          internship.setName(internshipDTO.getName());
      }
      if(internshipDTO.getStartDate() != null) {
          internship.setStartDate(internshipDTO.getStartDate());
      }
      if(internshipDTO.getEndDate() != null) {
          internship.setEndDate(internshipDTO.getEndDate());
      }
      if(internshipDTO.getAvailable() != null) {
          internship.setAvailable(internshipDTO.getAvailable());
      }
      if (internshipDTO.getRequirements() != null) {
          internship.setRequirements(internshipDTO.getRequirements());
      }
      if(internshipDTO.getSkills() != null)
      {
          internship.setSkills(internshipDTO.getSkills());
      }
      if(internshipDTO.getTechnologies() != null)
      {
          internship.setTechnologies(internshipDTO.getTechnologies());
      }
        Internship saved = internshipRepository.save(internship);
        return modelMapper.map(saved, InternshipDTO.class);

   }
}
