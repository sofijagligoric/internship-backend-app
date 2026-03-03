package org.unibl.etf.ip.backendapp.service;

import com.lowagie.text.pdf.BaseFont;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.backendapp.dto.CvDTO;
import org.unibl.etf.ip.backendapp.dto.PdfDTO;
import org.unibl.etf.ip.backendapp.entity.Cv;
import org.unibl.etf.ip.backendapp.entity.Student;
import org.unibl.etf.ip.backendapp.exceptions.ResourceNotFoundException;
import org.unibl.etf.ip.backendapp.repository.CVRepository;
import org.unibl.etf.ip.backendapp.repository.StudentRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Service
public class CvService {

    private CVRepository cvRepository;
    private StudentRepository studentRepository;
    private ModelMapper modelMapper;
    private final FileStorageService fileStorageService;

    private final String imagesDir = "uploads/cvImages/";

    public CvService(CVRepository cvRepository, StudentRepository studentRepository, ModelMapper modelMapper, FileStorageService fileStorageService) {
        this.cvRepository = cvRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
        this.fileStorageService = fileStorageService;
    }

    public PdfDTO generateCvPdf(Integer studentId) throws DocumentException {
        CvDTO cv = getByStudent(studentId);
        String fileName = "CV_" + cv.getFirstName() + "_" + cv.getLastName() + ".pdf";
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, out);
        document.open();
        Font titleFont, sectionFont, textFont;
        try {
            BaseFont base = BaseFont.createFont("src/main/resources/fonts/DejaVuSans.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            BaseFont baseBold = BaseFont.createFont("src/main/resources/fonts/DejaVuSans-Bold.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            titleFont = new Font(baseBold, 20);
            sectionFont = new Font(baseBold, 14);
            textFont = new Font(base, 11);
        } catch (Exception e) {
            titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
            sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            textFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
        }

        Paragraph name = new Paragraph(cv.getFirstName() + " " + cv.getLastName(), titleFont);
        name.setAlignment(Element.ALIGN_CENTER);
        name.setSpacingAfter(10);
        document.add(name);

        Paragraph contact = new Paragraph("Contact: " + cv.getContact(), textFont);
        contact.setAlignment(Element.ALIGN_CENTER);
        contact.setSpacingAfter(20);
        document.add(contact);
        document.add(new LineSeparator());

        addPdfSection(document, "About", cv.getDescription(), sectionFont, textFont);
        addPdfSection(document, "Education", cv.getSchool(), sectionFont, textFont);
        addPdfSection(document, "Skills", cv.getSkills(), sectionFont, textFont);
        addPdfSection(document, "Experience", cv.getExperience(), sectionFont, textFont);
        addPdfSection(document, "Internships", cv.getInternships(), sectionFont, textFont);
        addPdfSection(document, "Training", cv.getTraining(), sectionFont, textFont);
        addPdfSection(document, "Interests", cv.getInterests(), sectionFont, textFont);

        document.close();
        return new PdfDTO(fileName, out.toByteArray());
    }

    private void addPdfSection(Document document, String title, String content, Font sFont, Font tFont) throws DocumentException {
        if (content != null && !content.trim().isEmpty()) {
            Paragraph pTitle = new Paragraph("\n" + title, sFont);
            pTitle.setSpacingBefore(0);
            document.add(pTitle);
            Paragraph pContent = new Paragraph(content, tFont);
            pContent.setSpacingBefore(5);
            document.add(pContent);
        }
    }

    public CvDTO getById(Integer cvId) {
        Cv cv = cvRepository.findById(cvId).orElseThrow(() -> new ResourceNotFoundException("CV with id " + cvId + " not found"));
        return modelMapper.map(cv, CvDTO.class);
    }

    public CvDTO getByStudent(Integer studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found");
        }
        Cv cv = cvRepository.findByStudentId(studentId).orElseThrow(() -> new ResourceNotFoundException("CV not found"));
        return modelMapper.map(cv, CvDTO.class);
    }

    public List<CvDTO> getAll() {
        return cvRepository.findAll().stream().map(cv -> modelMapper.map(cv, CvDTO.class)).collect(Collectors.toList());
    }

    public CvDTO create(CvDTO cvDTO, MultipartFile image) {
        if (cvRepository.existsByStudentId(cvDTO.getStudentId())) {
            throw new RuntimeException("This student already has CV.");
        }
        Student student = studentRepository.findById(cvDTO.getStudentId()).orElseThrow(() -> new ResourceNotFoundException("Student does not exist."));
        Cv cv = modelMapper.map(cvDTO, Cv.class);
        cv.setStudent(student);
        if (image != null && !image.isEmpty()) {
            String fileName = fileStorageService.saveFile(image, imagesDir);
            cv.setImagePath(fileName);
        }
        Cv saved = cvRepository.save(cv);
        return modelMapper.map(saved, CvDTO.class);
    }

    public boolean delete(Integer cvId) {
        Cv original = cvRepository.findById(cvId)
                .orElseThrow(() -> new ResourceNotFoundException("CV not found"));
        if (original.getImagePath() != null) {
            fileStorageService.deleteFile(imagesDir, original.getImagePath());
        }
        cvRepository.deleteById(cvId);
        return true;
    }
    public CvDTO update(Integer cvId, CvDTO cvDTO, MultipartFile image) {

        Cv original = cvRepository.findById(cvId)
                .orElseThrow(() -> new ResourceNotFoundException("CV not found"));
        if (cvDTO.getStudentId() != null &&
                !original.getStudent().getId().equals(cvDTO.getStudentId())) {
            throw new RuntimeException("Student id mismatch");
        }
        if (cvDTO.getContact() != null)
            original.setContact(cvDTO.getContact());
        if (cvDTO.getExperience() != null)
            original.setExperience(cvDTO.getExperience());
        if (cvDTO.getDescription() != null)
            original.setDescription(cvDTO.getDescription());
        if (cvDTO.getInterests() != null)
            original.setInterests(cvDTO.getInterests());
        if (cvDTO.getFirstName() != null)
            original.setFirstName(cvDTO.getFirstName());
        if (cvDTO.getLastName() != null)
            original.setLastName(cvDTO.getLastName());
        if (cvDTO.getSchool() != null)
            original.setSchool(cvDTO.getSchool());
        if (cvDTO.getInternships() != null)
            original.setInternships(cvDTO.getInternships());
        if (cvDTO.getSkills() != null)
            original.setSkills(cvDTO.getSkills());
        if (cvDTO.getTraining() != null)
            original.setTraining(cvDTO.getTraining());
        if (cvDTO.getImagePath() != null && cvDTO.getImagePath().isEmpty()) {
            if (original.getImagePath() != null) {
                fileStorageService.deleteFile(imagesDir, original.getImagePath());
                original.setImagePath(null);
            }
        }
        if (image != null && !image.isEmpty()) {
            if (original.getImagePath() != null) {
                fileStorageService.deleteFile(imagesDir, original.getImagePath());
            }
            String fileName = fileStorageService.saveFile(image, imagesDir);
            original.setImagePath(fileName);
        }
        Cv saved = cvRepository.save(original);
        return modelMapper.map(saved, CvDTO.class);
    }
}
