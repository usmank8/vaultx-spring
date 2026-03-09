package com.vaultx.vaultxsp.repositories;

import com.vaultx.vaultxsp.models.Society;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SocietyRepository extends JpaRepository<Society, String> {

    @Query("SELECT s FROM Society s ORDER BY s.societyId ASC")
    java.util.List<Society> findAllOrdered();

    default Optional<Society> findFirst() {
        return findAllOrdered().stream().findFirst();
    }
}
