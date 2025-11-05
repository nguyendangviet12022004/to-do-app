package com.viet.to_do_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viet.to_do_api.entity.task.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

}
