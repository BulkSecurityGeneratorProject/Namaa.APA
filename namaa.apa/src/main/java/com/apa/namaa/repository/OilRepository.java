package com.apa.namaa.repository;

import com.apa.namaa.domain.Oil;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Oil entity.
 */
public interface OilRepository extends JpaRepository<Oil,Long> {

    @Query("select oil from Oil oil where oil.owner.login = ?#{principal.username}")
    List<Oil> findByOwnerIsCurrentUser();

}
