package com.viet.to_do_api.repository;

import com.viet.to_do_api.entity.payment.Donate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonateRepository extends JpaRepository<Donate, Integer> {
}
