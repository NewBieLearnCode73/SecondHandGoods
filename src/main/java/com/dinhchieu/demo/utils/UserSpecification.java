package com.dinhchieu.demo.utils;

import com.dinhchieu.demo.entity.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Support for filtering users base on activation, accountState, and role
public class UserSpecification {
    public static Specification<User> UserSpecificationBuildFromFilter(Map<String, String> filter){
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filter.containsKey("activation")){
               predicates.add(criteriaBuilder.equal(root.get("activation"), filter.get("activation")));
            }

            if(filter.containsKey("accountState")){
                predicates.add(criteriaBuilder.equal(root.get("accountState"), AccountState.valueOf(filter.get("accountState"))));
            }

            if(filter.containsKey("role")){
                predicates.add(criteriaBuilder.equal(root.get("role").get("roleName"), filter.get("role")));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

    }
}
