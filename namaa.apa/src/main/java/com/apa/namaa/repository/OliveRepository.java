package com.apa.namaa.repository;

import com.apa.namaa.domain.Olive;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Olive entity.
 */
public interface OliveRepository extends JpaRepository<Olive,Long> {

    @Query("select olive from Olive olive where olive.owner.login = ?#{principal.username}")
    List<Olive> findByOwnerIsCurrentUser();

}
