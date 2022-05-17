package com.company.GameStore.repository;

import com.company.GameStore.models.TShirt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TShirtRepository extends JpaRepository<TShirt, Integer> {

    List<TShirt> findBySize(String size);

    List<TShirt> findByColor(String color);

    List<TShirt> findBySizeAndColor(String size, String color);
}
