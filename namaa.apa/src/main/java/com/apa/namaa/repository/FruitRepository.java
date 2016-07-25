package com.apa.namaa.repository;

import com.apa.namaa.domain.Fruit;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Fruit entity.
 */
public interface FruitRepository extends JpaRepository<Fruit,Long> {

    @Query("select fruit from Fruit fruit where fruit.owner.login = ?#{principal.username}")
    List<Fruit> findByOwnerIsCurrentUser();

    @Query("select fruit from Fruit fruit where fruit.owner.login = ?#{principal.username}")
    Fruit findOneByOwnerIsCurrentUser(Long fruit_id);

}
