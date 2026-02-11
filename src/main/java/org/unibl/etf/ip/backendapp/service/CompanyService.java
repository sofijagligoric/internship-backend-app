package org.unibl.etf.ip.backendapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.backendapp.dto.CreateUserDTO;
import org.unibl.etf.ip.backendapp.dto.CompanyDTO;
import org.unibl.etf.ip.backendapp.entity.Company;
import org.unibl.etf.ip.backendapp.entity.User;
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

    public List<CompanyDTO> getAllCompanys() {
        return companyRepository.findAll().stream().map(company -> modelMapper.map(company, CompanyDTO.class)).collect(Collectors.toList());
    }

    public boolean deleteCompany(Integer companyId) {

        Company company = companyRepository.findById(companyId)
                .orElse(null);

        if (company == null) return false;

        userRepository.delete(company.getUser());
        companyRepository.delete(company);

        return true;
    }

    public CompanyDTO createCompany(CreateUserDTO user, CompanyDTO companyDTO) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(Role.COMPANY);
        User savedUser = userRepository.save(newUser);
        Company company = modelMapper.map(companyDTO, Company.class);
        company.setUser(savedUser);
        Company companySaved = companyRepository.save(company);
        return modelMapper.map(companySaved, CompanyDTO.class);
    }

    public CompanyDTO updateCompany(Integer companyId, CompanyDTO companyDTO) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

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
        return modelMapper.map(companyRepository.findById(companyId).orElse(null), CompanyDTO.class);
    }

    public void changeStatus(Integer companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new RuntimeException("Company not found"));
        company.setActive(!company.getActive());
        companyRepository.save(company);
    }

    public boolean changePassword(Integer companyId, String oldPassword, String newPassword) {
        User user = userRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.COMPANY) {
            return false;
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return true;
    }
}
