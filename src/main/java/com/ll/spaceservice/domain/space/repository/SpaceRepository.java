package com.ll.spaceservice.domain.space.repository;

import com.ll.spaceservice.domain.space.entity.Space;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceRepository extends JpaRepository<Space, Long> {

}
