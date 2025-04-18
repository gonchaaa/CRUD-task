package com.task.demo.specification;

import com.task.demo.DTOs.filter.BranchFilterDTO;
import com.task.demo.DTOs.filter.UserFilterDTO;
import com.task.demo.entities.Branch;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BranchSpecification implements Specification<Branch> {
    private final BranchFilterDTO branchFilterDTO;


    @Override
    public Predicate toPredicate(Root<Branch> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (branchFilterDTO.getBranchName() != null && !branchFilterDTO.getBranchName().isEmpty()) {
            predicates.add(criteriaBuilder.like( criteriaBuilder.lower(root.get("branchName")),
                    "%" + branchFilterDTO.getBranchName().toLowerCase() + "%"));
        }


        if (branchFilterDTO.getBranchAddress() != null && !branchFilterDTO.getBranchAddress().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("branchAddress")
            ), "%" + branchFilterDTO.getBranchAddress().toLowerCase() + "%"));
        }

        if (branchFilterDTO.getBranchCode() != null) {
            predicates.add(criteriaBuilder.equal(root.get("branchCode"), branchFilterDTO.getBranchCode()));
        }


        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
