package org.unibl.etf.ip.backendapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.backendapp.repository.CVRepository;
import org.unibl.etf.ip.backendapp.repository.StudentRepository;

@Transactional
@Service
public class CvService {

    private CVRepository cvRepository;
    private StudentRepository studentRepository;
    private ModelMapper modelMapper;

    public CvService(CVRepository cvRepository, StudentRepository studentRepository, ModelMapper modelMapper) {
        this.cvRepository = cvRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }


}
