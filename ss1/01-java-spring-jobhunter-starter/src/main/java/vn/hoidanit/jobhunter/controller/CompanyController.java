package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.service.CompanyService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> fetchGetListCompany() {
        List<Company> companyList = companyService.getListCompany();
        return ResponseEntity.status(HttpStatus.OK).body(companyList);
    }

    @PostMapping("/companies/create")
    public ResponseEntity<Company> fetchCreateNew(@Valid @RequestBody Company company) {
        Company newCompany = this.companyService.createNewCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCompany);

    }
}
