package com.task.demo.controller;

import com.task.demo.DTOs.request.BranchUIDTO;
import com.task.demo.DTOs.request.UserUIDTO;
import com.task.demo.DTOs.response.BranchDTO;
import com.task.demo.DTOs.response.UserDTO;
import com.task.demo.entities.Branch;
import com.task.demo.entities.User;

import java.util.List;

public interface IBranchController {
     BranchDTO saveBranch(BranchUIDTO branchUIDTO);
     List<BranchDTO> getAllBranches();
     BranchDTO getBranchById(Long id);
     List<Branch> getBranchFilter(Integer branchCode, String branchAddress, String branchName);
     String deleteBranchById(Long id);
     BranchDTO updateUser (Long id,BranchUIDTO branchUIDTO);
}
