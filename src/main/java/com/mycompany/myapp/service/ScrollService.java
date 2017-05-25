package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Scroll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Scroll.
 */
public interface ScrollService {

    /**
     * Save a scroll.
     *
     * @param scroll the entity to save
     * @return the persisted entity
     */
    Scroll save(Scroll scroll);

    /**
     *  Get all the scrolls.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Scroll> findAll(Pageable pageable);

    /**
     *  Get the "id" scroll.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Scroll findOne(Long id);

    /**
     *  Delete the "id" scroll.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
