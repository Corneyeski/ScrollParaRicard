package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Scroll;
import com.mycompany.myapp.service.ScrollService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Scroll.
 */
@RestController
@RequestMapping("/api")
public class ScrollResource {

    private final Logger log = LoggerFactory.getLogger(ScrollResource.class);

    private static final String ENTITY_NAME = "scroll";
        
    private final ScrollService scrollService;

    public ScrollResource(ScrollService scrollService) {
        this.scrollService = scrollService;
    }

    /**
     * POST  /scrolls : Create a new scroll.
     *
     * @param scroll the scroll to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scroll, or with status 400 (Bad Request) if the scroll has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/scrolls")
    @Timed
    public ResponseEntity<Scroll> createScroll(@Valid @RequestBody Scroll scroll) throws URISyntaxException {
        log.debug("REST request to save Scroll : {}", scroll);
        if (scroll.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new scroll cannot already have an ID")).body(null);
        }
        Scroll result = scrollService.save(scroll);
        return ResponseEntity.created(new URI("/api/scrolls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /scrolls : Updates an existing scroll.
     *
     * @param scroll the scroll to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated scroll,
     * or with status 400 (Bad Request) if the scroll is not valid,
     * or with status 500 (Internal Server Error) if the scroll couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/scrolls")
    @Timed
    public ResponseEntity<Scroll> updateScroll(@Valid @RequestBody Scroll scroll) throws URISyntaxException {
        log.debug("REST request to update Scroll : {}", scroll);
        if (scroll.getId() == null) {
            return createScroll(scroll);
        }
        Scroll result = scrollService.save(scroll);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, scroll.getId().toString()))
            .body(result);
    }

    /**
     * GET  /scrolls : get all the scrolls.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of scrolls in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/scrolls")
    @Timed
    public ResponseEntity<List<Scroll>> getAllScrolls(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Scrolls");
        Page<Scroll> page = scrollService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/scrolls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /scrolls/:id : get the "id" scroll.
     *
     * @param id the id of the scroll to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scroll, or with status 404 (Not Found)
     */
    @GetMapping("/scrolls/{id}")
    @Timed
    public ResponseEntity<Scroll> getScroll(@PathVariable Long id) {
        log.debug("REST request to get Scroll : {}", id);
        Scroll scroll = scrollService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(scroll));
    }

    /**
     * DELETE  /scrolls/:id : delete the "id" scroll.
     *
     * @param id the id of the scroll to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/scrolls/{id}")
    @Timed
    public ResponseEntity<Void> deleteScroll(@PathVariable Long id) {
        log.debug("REST request to delete Scroll : {}", id);
        scrollService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
