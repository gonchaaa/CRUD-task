package com.task.demo.controller;

import com.task.demo.DTOs.filter.BranchFilterDTO;
import com.task.demo.DTOs.request.BranchUIDTO;
import com.task.demo.DTOs.response.BranchDTO;
import com.task.demo.entities.Branch;
import com.task.demo.services.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {


    private final BranchService branchService;

    @PostMapping()
    public BranchDTO saveBranch(@RequestBody @Valid BranchUIDTO branchUIDTO) {
        return branchService.saveBranch(branchUIDTO);
    }

    @GetMapping(path = "/get-all-branches")
    public List<BranchDTO> getAllBranches() {
        return branchService.getAllBranches();
    }

    @GetMapping(path = "/get-branch/{id}")
    public BranchDTO getBranchById(@PathVariable(name = "id") Long id) {
        return branchService.getBranchById(id);
    }

    @GetMapping(path = "/get-branch-filter")
    public List<Branch> getBranchFilter(@RequestParam(required = false) Integer branchCode,
                                        @RequestParam(required = false) String branchAddress,
                                        @RequestParam(required = false) String branchName) {
        BranchFilterDTO branchFilterDTO = new BranchFilterDTO();
        branchFilterDTO.setBranchAddress(branchAddress);
        branchFilterDTO.setBranchCode(branchCode);
        branchFilterDTO.setBranchName(branchName);

        return branchService.getBranchFilter(branchFilterDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteBranchById(@PathVariable(name = "id") Long id) {
       return branchService.deleteBranchById(id);
    }

    @PutMapping("/{id}")
    public BranchDTO updateUser(@PathVariable(name = "id") Long id, @RequestBody BranchUIDTO branchUIDTO) {
        return branchService.updateBranch(id, branchUIDTO);
    }
}
