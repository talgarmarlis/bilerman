package com.artatech.bilerman.Repositories;

import com.artatech.bilerman.Entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String tagName);

    Boolean existsByName(String tagName);
}
