package com.artatech.bilerman.Forum.Repositories;

import com.artatech.bilerman.Entities.Comment;
import com.artatech.bilerman.Forum.Entities.ResponseReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseReplyRepository extends JpaRepository<ResponseReply, Long> {
    //Page<ResponseReply> findAllByResponseId(Long questionId, Pageable pageable);
}

