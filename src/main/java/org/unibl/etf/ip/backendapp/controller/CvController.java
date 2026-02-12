package org.unibl.etf.ip.backendapp.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.backendapp.dto.CvDTO;
import org.unibl.etf.ip.backendapp.service.CvService;

@RestController
@RequestMapping("/api/cv")
public class CvController {

    @Autowired
    private CvService cvService;

    @PostMapping("/new")
    public CvDTO create(
            @RequestPart("cv") CvDTO cvDTO,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return cvService.create(cvDTO, image);
    }
}
