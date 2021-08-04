package com.artatech.bilerman.Forum.Repositories;

import com.artatech.bilerman.Forum.Entities.ResponseReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseReplyRepository extends JpaRepository<ResponseReply, Long> {
    //Page<ResponseReply> findAllByResponseReplyId(Long questionId, Pageable pageable);
}
