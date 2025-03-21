package com.ll.spaceservice.domain.spaceMember.repository;

import com.ll.spaceservice.domain.space.entity.Space;
import com.ll.spaceservice.domain.spaceMember.entity.SpaceMember;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceMemberRepository extends JpaRepository<SpaceMember, Long> {
  // 특정 멤버가 속한 모든 SpaceMember 조회
  List<SpaceMember> findByMemberId(Long memberId);

  // 특정 멤버가 속한 모든 Space 객체 직접 조회 (JPQL 사용)
  @Query("SELECT sm.space FROM SpaceMember sm WHERE sm.memberId = :memberId")
  List<Space> findSpacesByMemberId(@Param("memberId") Long memberId);

  // 특정 멤버가 특정 스페이스에 속해 있는지 확인
  boolean existsBySpaceIdAndMemberId(Long spaceId, Long memberId);

  @Query("SELECT sm FROM SpaceMember sm WHERE sm.space.id = :spaceId AND sm.memberId = :memberId")
  SpaceMember findBySpaceIdAndMemberId(
      @Param("spaceId") Long spaceId,
      @Param("memberId") Long memberId);


  // 특정 멤버의 특정 스페이스에서의 역할 조회
  @Query("SELECT sm.role FROM SpaceMember sm WHERE sm.space.id = :spaceId AND sm.memberId = :memberId")
  SpaceMember.MemberRole findRoleBySpaceIdAndMemberId(
      @Param("spaceId") Long spaceId,
      @Param("memberId") Long memberId);

  List<SpaceMember> findBySpaceId(Long spaceId);
}
