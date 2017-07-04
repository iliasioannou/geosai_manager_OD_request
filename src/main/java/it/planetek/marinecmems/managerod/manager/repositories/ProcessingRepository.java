package it.planetek.marinecmems.managerod.manager.repositories;

import it.planetek.marinecmems.managerod.manager.domains.Processing;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by Francesco Bruni on 7/4/17.
 */
@RepositoryRestResource(collectionResourceRel = "processings", path = "processings")
public interface ProcessingRepository extends PagingAndSortingRepository<Processing, Long> {
    List<Processing> findByUserEmail(@Param("userEmail") String name);
}
