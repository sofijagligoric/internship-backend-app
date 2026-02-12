package org.unibl.etf.ip.backendapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.backendapp.dto.WorkentryDTO;
import org.unibl.etf.ip.backendapp.dto.WorkentryDTO;
import org.unibl.etf.ip.backendapp.entity.Workentry;
import org.unibl.etf.ip.backendapp.entity.Workentry;
import org.unibl.etf.ip.backendapp.exceptions.ResourceNotFoundException;
import org.unibl.etf.ip.backendapp.repository.WorkentryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkentryService {

    private WorkentryRepository workentryRepository;
    private ModelMapper modelMapper;

    public WorkentryService(WorkentryRepository workentryRepository, ModelMapper modelMapper) {
        this.workentryRepository = workentryRepository;
        this.modelMapper = modelMapper;
    }

    public WorkentryDTO create(WorkentryDTO workentryDTO) {
        if(workentryRepository.existsByWeekStart(workentryDTO.getWeekStart())) {
            throw new RuntimeException("Week start already exists");
        }
        Workentry workentry = modelMapper.map(workentryDTO, Workentry.class);
        workentry = workentryRepository.save(workentry);
        return modelMapper.map(workentry, WorkentryDTO.class);
    }

    public boolean deleteWorkEntry(Integer workentryId)
    {
        if(workentryRepository.existsById(workentryId)) {
            workentryRepository.deleteById(workentryId);
            return true;
        }
        return false;
    }

    public List<WorkentryDTO> getAllForStudent(Integer studentId)
    {
        return workentryRepository.findByStudentId(studentId).stream().map(workentry -> modelMapper.map(workentry, WorkentryDTO.class)).collect(Collectors.toList());
    }

    public List<WorkentryDTO> getAllForInternship(Integer internshipId)
    {
        return workentryRepository.findByInternshipId(internshipId).stream().map(workentry -> modelMapper.map(workentry, WorkentryDTO.class)).collect(Collectors.toList());
    }

    public WorkentryDTO getById(Integer workentryId) {
        Workentry workentry = workentryRepository.findById(workentryId).orElseThrow(() -> new ResourceNotFoundException("Workentry with id " + workentryId + " not found"));
        return modelMapper.map(workentry, WorkentryDTO.class);
    }
}
