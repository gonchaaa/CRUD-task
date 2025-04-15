package com.task.demo.specification;

import com.task.demo.DTOs.filter.UserFilterDTO;
import com.task.demo.entities.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification implements Specification<User> {

    private final UserFilterDTO userFilterDTO;

    public UserSpecification(UserFilterDTO userFilterDTO) {
        this.userFilterDTO = userFilterDTO;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();


        if (userFilterDTO.getFirstName() != null && !userFilterDTO.getFirstName().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("firstName")),  "%" +userFilterDTO.getFirstName().toLowerCase() + "%" ));
        }


        if (userFilterDTO.getLastName() != null && !userFilterDTO.getLastName().isEmpty()) {
            predicates.add(cb.like(cb.lower(root.get("lastName")),  "%" +userFilterDTO.getLastName().toLowerCase() + "%" ));
        }

        if (userFilterDTO.getAge() != null) {
            predicates.add(cb.equal(root.get("age"), userFilterDTO.getAge()));
        }

        if (userFilterDTO.getBirthDate() != null) {
            predicates.add(cb.equal(root.get("birthDate"), userFilterDTO.getBirthDate()));
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    }
}

