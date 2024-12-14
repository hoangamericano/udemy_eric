package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.dto.Meta;
import vn.hoidanit.jobhunter.dto.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.CompanyRepository;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public ResultPaginationDTO handleGetListCompany(Specification<Company> spec, Pageable pageable) {
        // TODO Auto-generated method stub
        Page<Company> companyList = this.companyRepository.findAll(pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        Meta mt = new Meta();
        mt.setPage(pageable.getPageNumber());
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(companyList.getTotalPages());
        mt.setTotal(companyList.getTotalElements());
        resultPaginationDTO.setMeta(mt);
        resultPaginationDTO.setResult(companyList.getContent());
        return resultPaginationDTO;
    }

    public Company handleCreateNewCompany(Company company) {
        return this.companyRepository.save(company);
    }

    public void hanldeDeleteCompany(Integer id) {
        // TODO Auto-generated method stub
        Company company = this.handleFindIdCompany(id);
        this.companyRepository.deleteById(id);
    }

    public Company handleFindIdCompany(Integer id) {
        // TODO Auto-generated method stub
        Optional<Company> companyOptional = this.companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            return companyOptional.get();
        }
        throw new UnsupportedOperationException("Unimplemented method 'handleFindIdCompany'");
    }

    public Company handleUpdateCompany(Company c) {
        Optional<Company> companyOptional = this.companyRepository.findById(c.getId());
        if (companyOptional.isPresent()) {
            Company currentCompany = companyOptional.get();
            currentCompany.setLogo(c.getLogo());
            currentCompany.setName(c.getName());
            currentCompany.setDescription(c.getDescription());
            currentCompany.setAddress(c.getAddress());
            return this.companyRepository.save(currentCompany);
        }
        return null;
    }

}
