package com.apa.namaa.repository;

import com.apa.namaa.domain.GrainType2;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GrainType2 entity.
 */
public interface GrainType2Repository extends JpaRepository<GrainType2,Long> {

    @Query("select grainType2 from GrainType2 grainType2 where grainType2.owner.login = ?#{principal.username}")
    List<GrainType2> findByOwnerIsCurrentUser();

}
