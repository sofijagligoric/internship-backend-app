package org.unibl.etf.ip.backendapp.controller;

import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.SyndFeedOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.unibl.etf.ip.backendapp.dto.InternshipDTO;
import org.unibl.etf.ip.backendapp.service.InternshipService;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/rss")
public class RssController {

    @Autowired
    private InternshipService internshipService;

    @GetMapping(value = "/internships", produces = "application/rss+xml")
    public String getInternshipsRss() throws Exception {
        List<InternshipDTO> internships = internshipService.getAllAvailable();
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");
        feed.setTitle("Available internships");
        feed.setLink("http://localhost:8080/api/rss/internships");
        feed.setDescription("List of currently available internships");
        List<SyndEntry> entries = new ArrayList<>();

        for (InternshipDTO internship : internships) {
            SyndEntry entry = new SyndEntryImpl();
            entry.setTitle(internship.getName());
            entry.setPublishedDate(new Date());
            entry.setLink("http://localhost:8080/api/internship/" + internship.getId());

            SyndContent description = new SyndContentImpl();
            description.setType("text/plain");
            description.setValue(
                    "Start date: " + internship.getStartDate() + ", end date: " + internship.getEndDate() +
                            "\nAbout: " + internship.getAbout() +
                            "\nTechnologies: " + internship.getTechnologies() +
                            "\nSkills: " + internship.getSkills() +
                            "\nRequirements: " + internship.getRequirements()
            );
            entry.setDescription(description);
            entries.add(entry);
        }

        feed.setEntries(entries);
        SyndFeedOutput output = new SyndFeedOutput();
        return output.outputString(feed);
    }
}
