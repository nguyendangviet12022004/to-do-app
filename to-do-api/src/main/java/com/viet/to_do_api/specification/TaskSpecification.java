package com.viet.to_do_api.specification;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.viet.to_do_api.constant.StatusValue;
import com.viet.to_do_api.entity.task.Task;

public class TaskSpecification {
    public static Specification<Task> hasCategoryId(List<Integer> categoryIds) {
        return (root, query, criteriaBuilder) -> {
            if (categoryIds == null || categoryIds.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("category").get("id").in(categoryIds);
        };
    }

    public static Specification<Task> hasTagId(List<Integer> tagIds) {
        return (root, query, criteriaBuilder) -> {
            if (tagIds == null || tagIds.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.join("tags").get("id").in(tagIds);
        };
    }

    public static Specification<Task> hasPriority(List<Integer> priorities) {
        return (root, query, criteriaBuilder) -> {
            if (priorities == null || priorities.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("priority").in(priorities);
        };
    }

    public static Specification<Task> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> {
            if (title == null || title.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
        };
    }

    public static Specification<Task> hasStatus(List<StatusValue> statuses) {
        return (root, query, criteriaBuilder) -> {
            if (statuses == null || statuses.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("currentStatus").get("value").in(statuses);
        };
    }

}