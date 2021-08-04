package com.artatech.bilerman.Forum.Repositories;

import com.artatech.bilerman.Forum.Entities.Response;
import com.artatech.bilerman.Forum.Entities.ResponseVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseVoteRepository extends JpaRepository<ResponseVote, Long> {
    //findallbySmth
}
