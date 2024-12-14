package vn.hoidanit.jobhunter.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.CompanyService;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    public ResponseEntity<ResultPaginationDTO> fetchGetListCompany(Specification<Company> spec, Pageable pageable) {
        ResultPaginationDTO rs = this.companyService.handleGetListCompany(spec, pageable);
        // @RequestParam("current") Optional<String> currentOptional,
        // @RequestParam("pageSize") Optional<String> pageSizeOptional) {
        // String sCurrent = currentOptional.isPresent() ? currentOptional.get() : "";
        // String sPage = pageSizeOptional.isPresent() ? pageSizeOptional.get() : "";
        // int current = Integer.parseInt(sCurrent);
        // int page = Integer.parseInt(sPage);
        // Pageable pageable = PageRequest.of(current - 1, page);
        // Page<Company> companyList = companyService.handleGetListCompany(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(rs);
    }

    @PostMapping("/companies/create")
    public ResponseEntity<Company> fetchCreateNew(@Valid @RequestBody Company company) {
        Company newCompany = this.companyService.handleCreateNewCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCompany);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<String> fetchDeledeCompany(@PathVariable("id") Integer id) throws IdInvalidException {
        if (id >= 1500) {
            throw new IdInvalidException("Id not already exits");

        }
        this.companyService.hanldeDeleteCompany(id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<Company> fetchFindCompany(@PathVariable("id") Integer id) {
        Company companyFindId = this.companyService.handleFindIdCompany(id);
        return ResponseEntity.status(HttpStatus.OK).body(companyFindId);

    }

    @PutMapping("/companies")
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody Company reqCompany) {
        Company updatedCompany = this.companyService.handleUpdateCompany(reqCompany);
        return ResponseEntity.ok(updatedCompany);
    }
}
