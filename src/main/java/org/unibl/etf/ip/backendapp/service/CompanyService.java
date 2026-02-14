package org.unibl.etf.ip.backendapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.backendapp.dto.CompanyDTO;
import org.unibl.etf.ip.backendapp.dto.CreateCompanyDTO;
import org.unibl.etf.ip.backendapp.entity.Company;
import org.unibl.etf.ip.backendapp.entity.User;
import org.unibl.etf.ip.backendapp.exceptions.ResourceNotFoundException;
import org.unibl.etf.ip.backendapp.repository.CompanyRepository;
import org.unibl.etf.ip.backendapp.repository.UserRepository;
import org.unibl.etf.ip.backendapp.util.Role;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompanyService {
    private CompanyRepository companyRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll().stream().map(company -> modelMapper.map(company, CompanyDTO.class)).collect(Collectors.toList());
    }

    public boolean deleteCompany(Integer companyId) {
        Company company = companyRepository.findById(companyId)
                .orElse(null);
        if (company == null) return false;
        companyRepository.delete(company);
        userRepository.delete(company.getUser());
        return true;
    }

    public CompanyDTO createCompany(CreateCompanyDTO newCompanyDTO) {
        User newUser = new User();
        newUser.setUsername(newCompanyDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(newCompanyDTO.getPassword()));
        newUser.setRole(Role.COMPANY);
        User savedUser = userRepository.save(newUser);
        Company company = new Company();
        company.setActive(newCompanyDTO.getActive());
        company.setAddress(newCompanyDTO.getAddress());
        company.setEmail(newCompanyDTO.getEmail());
        company.setName(newCompanyDTO.getName());
        company.setUser(savedUser);
        Company companySaved = companyRepository.save(company);
        return modelMapper.map(companySaved, CompanyDTO.class);
    }

    public CompanyDTO updateCompany(Integer companyId, CompanyDTO companyDTO) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        if(companyDTO.getName() != null) {
          company.setName(companyDTO.getName());
        }
       if(companyDTO.getEmail() != null) {
           company.setEmail(companyDTO.getEmail());
       }
       if(companyDTO.getAddress() != null) {
           company.setAddress(companyDTO.getAddress());
       }
       if(companyDTO.getActive() != null) {
           company.setActive(companyDTO.getActive());
       }

        Company updatedCompany = companyRepository.save(company);
        return modelMapper.map(updatedCompany, CompanyDTO.class);
    }

    public CompanyDTO getCompanyById(Integer companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("Company with id " + companyId + " not found"));
        return modelMapper.map(company, CompanyDTO.class);
    }

    public void changeStatus(Integer companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("Company not found"));
        company.setActive(!company.getActive());
        companyRepository.save(company);
    }

}
