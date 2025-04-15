package com.task.demo.controller.impl;

import com.task.demo.DTOs.filter.BranchFilterDTO;
import com.task.demo.DTOs.filter.UserFilterDTO;
import com.task.demo.DTOs.request.BranchUIDTO;
import com.task.demo.DTOs.response.BranchDTO;
import com.task.demo.controller.IBranchController;
import com.task.demo.entities.Branch;
import com.task.demo.services.IBranchService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/rest/api/branches")
public class BranchControllerImpl implements IBranchController {

    @Autowired
    private IBranchService branchService;

    @PostMapping(path = "/save-branch")
    @Override
    public BranchDTO saveBranch(@RequestBody @Valid BranchUIDTO branchUIDTO) {
        return branchService.saveBranch(branchUIDTO);
    }
    @GetMapping(path = "/get-all-branches")
    @Override
    public List<BranchDTO> getAllBranches() {
        return branchService.getAllBranches();
    }
    @GetMapping(path = "/get-branch/{id}")
    @Override
    public BranchDTO getBranchById(@PathVariable(name = "id") Long id) {
        return branchService.getBranchById(id);
    }
    @GetMapping(path = "/get-branch-filter")
    @Override
    public List<Branch> getBranchFilter(@RequestParam(required = false) Integer branchCode,
                                        @RequestParam(required = false) String branchAddress,
                                        @RequestParam(required = false) String branchName) {
        BranchFilterDTO branchFilterDTO = new BranchFilterDTO();
        branchFilterDTO.setBranchAddress(branchAddress);
        branchFilterDTO.setBranchCode(branchCode);
        branchFilterDTO.setBranchName(branchName);

        return branchService.getBranchFilter(branchFilterDTO);
    }
    @DeleteMapping("/delete/{id}")
    @Override
    public String deleteBranchById(@PathVariable(name = "id") Long id) {
       return branchService.deleteBranchById(id);
    }
    @PutMapping("/update/{id}")
    @Override
    public BranchDTO updateUser(@PathVariable(name = "id") Long id, @RequestBody BranchUIDTO branchUIDTO) {
        return branchService.updateBranch(id, branchUIDTO);
    }
}
