package com.viet.to_do_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viet.to_do_api.constant.AuthorityName;
import com.viet.to_do_api.entity.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Optional<Authority> findByName(AuthorityName name);
}
