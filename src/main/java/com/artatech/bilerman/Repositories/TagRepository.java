package com.artatech.bilerman.Repositories;

import com.artatech.bilerman.Entities.Tag;
import org.hibernate.sql.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String tagName);

    Boolean existsByName(String tagName);
}
