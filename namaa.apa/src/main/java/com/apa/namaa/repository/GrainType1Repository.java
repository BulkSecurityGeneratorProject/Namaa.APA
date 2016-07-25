package com.apa.namaa.repository;

import com.apa.namaa.domain.GrainType1;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GrainType1 entity.
 */
public interface GrainType1Repository extends JpaRepository<GrainType1,Long> {

    @Query("select grainType1 from GrainType1 grainType1 where grainType1.owner.login = ?#{principal.username}")
    List<GrainType1> findByOwnerIsCurrentUser();

}
