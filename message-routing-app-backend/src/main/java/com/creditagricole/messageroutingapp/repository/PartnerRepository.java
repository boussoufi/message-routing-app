package com.creditagricole.messageroutingapp.repository;

import com.creditagricole.messageroutingapp.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Optional<Partner> findByAlias(String alias);
    boolean existsByAlias(String alias);
}
