package it.planetek.marinecmems.managerod.manager.repositories;

import it.planetek.marinecmems.managerod.manager.domains.Processing;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created by Francesco Bruni on 7/4/17.
 */
@RepositoryRestResource(collectionResourceRel = "processings", path = "processings")
public interface ProcessingRepository extends PagingAndSortingRepository<Processing, Long> {
    @RestResource(exported = false)
    List<Processing> findByUserEmail(@Param("userEmail") String name);

    @RestResource(exported = false)
    @Query(nativeQuery = true,
            value = "SELECT * FROM processing WHERE user_email = ?1 AND status in ?2")
    List<Processing> findByUserEmailAndStatusNotIn(String userEmail, List<Integer> statuses);
}
