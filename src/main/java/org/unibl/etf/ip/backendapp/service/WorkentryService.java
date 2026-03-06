package org.unibl.etf.ip.backendapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.backendapp.dto.WorkentryDTO;
import org.unibl.etf.ip.backendapp.dto.WorkentryViewDTO;
import org.unibl.etf.ip.backendapp.entity.Internship;
import org.unibl.etf.ip.backendapp.entity.Workentry;
import org.unibl.etf.ip.backendapp.exceptions.ResourceNotFoundException;
import org.unibl.etf.ip.backendapp.repository.InternshipRepository;
import org.unibl.etf.ip.backendapp.repository.WorkentryRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkentryService {

    private WorkentryRepository workentryRepository;
    private InternshipRepository internshipRepository;
    private ModelMapper modelMapper;

    public WorkentryService(WorkentryRepository workentryRepository, ModelMapper modelMapper, InternshipRepository internshipRepository) {
        this.workentryRepository = workentryRepository;
        this.modelMapper = modelMapper;
        this.internshipRepository = internshipRepository;
    }

    public WorkentryDTO create(WorkentryDTO workentryDTO) {
        LocalDate weekStart = workentryDTO.getWeekStart()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        workentryDTO.setWeekStart(weekStart);
        if(workentryRepository.existsByStudentIdAndInternshipIdAndWeekStart(
                workentryDTO.getStudentId(),
                workentryDTO.getInternshipId(),
                weekStart
        )) {
            throw new RuntimeException("Work entry already exists for this week");
        }

        Internship internship = internshipRepository.findById(workentryDTO.getInternshipId())
                .orElseThrow(() -> new ResourceNotFoundException("Internship not found"));

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

    public List<WorkentryViewDTO> getAllForStudentView(Integer studentId)
    {
        return workentryRepository.findByStudentId(studentId).stream().map(workentry -> modelMapper.map(workentry, WorkentryViewDTO.class)).collect(Collectors.toList());
    }

    public List<WorkentryDTO> getAllForStudentAndInternship(Integer companyId, Integer studentId, Integer internhsipId)
    {
        Internship internship = internshipRepository.findById(internhsipId)
                .orElseThrow(() -> new ResourceNotFoundException("Internship not found"));
        if (!internship.getCompany().getId().equals(companyId)) {
            throw new ResourceNotFoundException("This internship does not belong to the specified company.");
        }
        return workentryRepository.findByStudentIdAndInternshipId(studentId, internhsipId).stream().map(workentry -> modelMapper.map(workentry, WorkentryDTO.class)).collect(Collectors.toList());
    }


    public List<WorkentryDTO> getAllForInternship(Integer internshipId)
    {
        return workentryRepository.findByInternshipId(internshipId).stream().map(workentry -> modelMapper.map(workentry, WorkentryDTO.class)).collect(Collectors.toList());
    }

    public WorkentryDTO getById(Integer workentryId) {
        Workentry workentry = workentryRepository.findById(workentryId).orElseThrow(() -> new ResourceNotFoundException("Workentry with id " + workentryId + " not found"));
        return modelMapper.map(workentry, WorkentryDTO.class);
    }

    public List<WorkentryDTO> getAll(){
        return workentryRepository.findAll().stream().map(workentry -> modelMapper.map(workentry, WorkentryDTO.class)).collect(Collectors.toList());
    }
}
