package com.task.demo.services.impl;

import com.task.demo.DTOs.filter.BranchFilterDTO;
import com.task.demo.DTOs.request.BranchUIDTO;
import com.task.demo.DTOs.response.BranchDTO;
import com.task.demo.entities.Branch;
import com.task.demo.exceptions.BaseException;
import com.task.demo.exceptions.ErrorMessage;
import com.task.demo.exceptions.ErrorsType;
import com.task.demo.repository.BranchRepository;
import com.task.demo.services.BranchService;
import com.task.demo.specification.BranchSpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BranchServcieImpl implements BranchService {

    @Autowired
    private BranchRepository branchRespository;

    @Override
    public BranchDTO saveBranch(BranchUIDTO branchUIDTO) {

            BranchDTO branchDTO = new BranchDTO();
            Branch branch = new Branch();
        try {
            BeanUtils.copyProperties(branchUIDTO,branch);
            Branch dbBranch = branchRespository.save(branch);
            BeanUtils.copyProperties(dbBranch,branchDTO);
            return branchDTO;
        }catch (DataIntegrityViolationException e){
            throw new BaseException(new ErrorMessage(ErrorsType.INVALID_DATA,
                    "Verilən məlumatlarla qeydiyyat mümkün olmadı. Məlumatları yoxlayın: " + e.getMostSpecificCause().getMessage()));
        }

    }

    @Override
    public List<BranchDTO> getAllBranches() {
        List<BranchDTO> branchDTOList = new ArrayList<>();
        List<Branch> branches = branchRespository.findAll();
        for (Branch branch : branches) {
            BranchDTO branchDTO = new BranchDTO();
            BeanUtils.copyProperties(branch,branchDTO);
            branchDTOList.add(branchDTO);
        }
        return branchDTOList;
    }

    @Override
    public BranchDTO getBranchById(Long id) {
        Optional<Branch> branchOptional = branchRespository.findById(id);
        if(branchOptional.isPresent()){
            BranchDTO branchDTO = new BranchDTO();
            Branch branch = branchOptional.get();
            BeanUtils.copyProperties(branch,branchDTO);
            return branchDTO;
        }
        throw new BaseException(new ErrorMessage(ErrorsType.NO_DATA_FOUND,id.toString()));
    }

    @Override
    public List<Branch> getBranchFilter(BranchFilterDTO branchFilterDTO) {
        BranchSpecification branchSpecification = new BranchSpecification(branchFilterDTO);
        return branchRespository.findAll(branchSpecification);
    }

    @Override
    public String deleteBranchById(Long id) {
        Optional<Branch> branchOptional = branchRespository.findById(id);
        if(branchOptional.isPresent()) {
            branchRespository.deleteById(id);
        return "success";
        }
        throw new BaseException(new ErrorMessage(ErrorsType.NO_DATA_FOUND,id.toString()));

    }

    @Override
    public BranchDTO updateBranch(Long id, BranchUIDTO branchUIDTO) {
        BranchDTO branchDTO = new BranchDTO();
        Optional<Branch> branchOptional = branchRespository.findById(id);
        if(branchOptional.isPresent()){
            Branch branch = branchOptional.get();

            branch.setBranchName(branchUIDTO.getBranchName());
            branch.setBranchCode(branchUIDTO.getBranchCode());
            branch.setBranchAddress(branchUIDTO.getBranchAddress());

            Branch updatedBranch = branchRespository.save(branch);
            BeanUtils.copyProperties(updatedBranch,branchDTO);
            return branchDTO;

        }
        throw new BaseException(new ErrorMessage(ErrorsType.NO_DATA_FOUND,id.toString()));
    }
}
