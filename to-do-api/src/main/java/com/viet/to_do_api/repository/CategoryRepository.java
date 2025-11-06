package com.viet.to_do_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viet.to_do_api.entity.task.Category;

import java.util.Collection;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByTitle(String title);
    List<Category> findByAccountEmail(String name);
}
