package com.task.demo.services;

import com.task.demo.DTOs.filter.BranchFilterDTO;
import com.task.demo.DTOs.request.BranchUIDTO;
import com.task.demo.DTOs.response.BranchDTO;
import com.task.demo.entities.Branch;

import java.util.List;

public interface BranchService {
     BranchDTO saveBranch(BranchUIDTO branchUIDTO);
     List<BranchDTO> getAllBranches();
     BranchDTO getBranchById(Long id);
     List<Branch> getBranchFilter(BranchFilterDTO branchFilterDTO);
     String deleteBranchById(Long id);
     BranchDTO updateBranch (Long id,BranchUIDTO branchUIDTO);
}
