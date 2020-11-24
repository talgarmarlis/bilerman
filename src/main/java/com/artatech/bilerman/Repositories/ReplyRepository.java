package com.artatech.bilerman.Repositories;

import com.artatech.bilerman.Entities.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {

    Page<Reply> findAllByCommentId(Long commentId, Pageable pageable);
}
