package com.apa.namaa.repository;

import com.apa.namaa.domain.GrainType3;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GrainType3 entity.
 */
public interface GrainType3Repository extends JpaRepository<GrainType3,Long> {

    @Query("select grainType3 from GrainType3 grainType3 where grainType3.owner.login = ?#{principal.username}")
    List<GrainType3> findByOwnerIsCurrentUser();

}
