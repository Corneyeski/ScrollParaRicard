package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.ScrollService;
import com.mycompany.myapp.domain.Scroll;
import com.mycompany.myapp.repository.ScrollRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Scroll.
 */
@Service
@Transactional
public class ScrollServiceImpl implements ScrollService{

    private final Logger log = LoggerFactory.getLogger(ScrollServiceImpl.class);
    
    private final ScrollRepository scrollRepository;

    public ScrollServiceImpl(ScrollRepository scrollRepository) {
        this.scrollRepository = scrollRepository;
    }

    /**
     * Save a scroll.
     *
     * @param scroll the entity to save
     * @return the persisted entity
     */
    @Override
    public Scroll save(Scroll scroll) {
        log.debug("Request to save Scroll : {}", scroll);
        Scroll result = scrollRepository.save(scroll);
        return result;
    }

    /**
     *  Get all the scrolls.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Scroll> findAll(Pageable pageable) {
        log.debug("Request to get all Scrolls");
        Page<Scroll> result = scrollRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one scroll by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Scroll findOne(Long id) {
        log.debug("Request to get Scroll : {}", id);
        Scroll scroll = scrollRepository.findOne(id);
        return scroll;
    }

    /**
     *  Delete the  scroll by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Scroll : {}", id);
        scrollRepository.delete(id);
    }
}
