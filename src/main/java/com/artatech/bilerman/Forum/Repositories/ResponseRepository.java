package com.artatech.bilerman.Forum.Repositories;

import com.artatech.bilerman.Forum.Entities.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<Response, Long> {
    //Page<Response> findAllResponseId(Long responseId, Pageable pageable);

}
