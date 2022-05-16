package com.company.GameStore.repository;

import com.company.GameStore.models.TShirts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TShirtRepository extends JpaRepository<TShirts, Integer> {
}
