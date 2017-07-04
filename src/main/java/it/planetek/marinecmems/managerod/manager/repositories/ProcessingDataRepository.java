package it.planetek.marinecmems.managerod.manager.repositories;

import it.planetek.marinecmems.managerod.manager.domains.ProcessingData;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by francesco on 7/4/17.
 */
@RepositoryRestResource(collectionResourceRel = "processingData", path = "processingData")
public interface ProcessingDataRepository extends PagingAndSortingRepository<ProcessingData, Long> {
}
