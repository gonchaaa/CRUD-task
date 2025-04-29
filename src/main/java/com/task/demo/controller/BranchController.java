package com.task.demo.controller;

import com.task.demo.DTOs.filter.BranchFilterDTO;
import com.task.demo.DTOs.request.BranchUIDTO;
import com.task.demo.DTOs.response.BranchDTO;
import com.task.demo.entities.Branch;
import com.task.demo.services.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {


    private final BranchService branchService;

    @PostMapping()
    public BranchDTO saveBranch(@RequestBody @Valid BranchUIDTO branchUIDTO) {
        log.info("Saving branch with details: {}", branchUIDTO);
        return branchService.saveBranch(branchUIDTO);
    }

    @GetMapping(path = "/get-all-branches")
    public List<BranchDTO> getAllBranches() {

        log.info("Fetching all branches");
        return branchService.getAllBranches();
    }

    @GetMapping(path = "/get-branch/{id}")
    public BranchDTO getBranchById(@PathVariable(name = "id") Long id) {

        log.info("Fetching branch with id: {}", id);
        return branchService.getBranchById(id);
    }

    @GetMapping(path = "/get-branch-filter")
    public List<Branch> getBranchFilter(@RequestParam(required = false) Integer branchCode,
                                        @RequestParam(required = false) String branchAddress,
                                        @RequestParam(required = false) String branchName) {
        log.info("Fetching branches with filter - code: {}, address: {}, name: {}", branchCode, branchAddress, branchName);
        BranchFilterDTO branchFilterDTO = new BranchFilterDTO();
        branchFilterDTO.setBranchAddress(branchAddress);
        branchFilterDTO.setBranchCode(branchCode);
        branchFilterDTO.setBranchName(branchName);

        return branchService.getBranchFilter(branchFilterDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteBranchById(@PathVariable(name = "id") Long id) {

        log.info("Deleting branch with id: {}", id);
        return branchService.deleteBranchById(id);
    }

    @PutMapping("/{id}")
    public BranchDTO updateUser(@PathVariable(name = "id") Long id, @RequestBody BranchUIDTO branchUIDTO) {
        log.info("Updating branch with id: {} and details: {}", id, branchUIDTO);
        return branchService.updateBranch(id, branchUIDTO);
    }
}
