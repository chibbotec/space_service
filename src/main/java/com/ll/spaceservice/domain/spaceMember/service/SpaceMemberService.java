package com.ll.spaceservice.domain.spaceMember.service;


import com.ll.spaceservice.domain.space.entity.Space;
import com.ll.spaceservice.domain.space.repository.SpaceRepository;
import com.ll.spaceservice.domain.spaceMember.dto.SpaceMemberRequest;
import com.ll.spaceservice.domain.spaceMember.dto.SpaceMemberResponse;
import com.ll.spaceservice.domain.spaceMember.dto.SpaceMemberRoleRequest;
import com.ll.spaceservice.domain.spaceMember.entity.SpaceMember;
import com.ll.spaceservice.domain.spaceMember.repository.SpaceMemberRepository;
import com.ll.spaceservice.global.client.MemberResponse;
import com.ll.spaceservice.global.error.ErrorCode;
import com.ll.spaceservice.global.exception.CustomException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SpaceMemberService {

  private final SpaceMemberRepository spaceMemberRepository;
  private final SpaceRepository spaceRepository;

  public List<SpaceMemberResponse> getMyMembers(Long spaceId) {
    return spaceMemberRepository.findBySpaceId(spaceId).stream()
        .map(SpaceMemberResponse::of)
        .collect(Collectors.toList());
  }

  @Transactional
  public List<SpaceMemberResponse> addMember(Long spaceId, List<SpaceMemberRequest> request) {
    Space space = spaceRepository.findById(spaceId)
        .orElseThrow(() -> new CustomException(ErrorCode.DATABASE_ERROR));

    List<SpaceMemberResponse> addMembers = new ArrayList<>();

    request.forEach(memberRequest -> {
      SpaceMember spaceMember = SpaceMember.builder()
          .space(space)
          .memberId(memberRequest.getId())
          .nickname(memberRequest.getNickname())
          .role(SpaceMember.MemberRole.MEMBER)
          .build();
      spaceMemberRepository.save(spaceMember);
      addMembers.add(SpaceMemberResponse.of(spaceMember));
    });
    return addMembers;
  }

  @Transactional
  public String changeRole(MemberResponse loginUser, Long spaceId, Long userId, SpaceMemberRoleRequest request) {

    SpaceMember.MemberRole role = spaceMemberRepository.findRoleBySpaceIdAndMemberId(spaceId,
        loginUser.getId());

    if (role != SpaceMember.MemberRole.OWNER &&
        role != SpaceMember.MemberRole.ADMIN) {
      throw new CustomException(ErrorCode.PERMISSION_DENIED);
    }

    SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberId(spaceId, userId);
    spaceMember.setRole(request.getRole());
    spaceMemberRepository.save(spaceMember);
    return "success";
  }

  @Transactional
  public String deleteMember(MemberResponse loginUser,Long spaceId, Long userId) {

    SpaceMember.MemberRole role = spaceMemberRepository.findRoleBySpaceIdAndMemberId(spaceId,
        loginUser.getId());

    if (role != SpaceMember.MemberRole.OWNER &&
        role != SpaceMember.MemberRole.ADMIN) {
      throw new CustomException(ErrorCode.PERMISSION_DENIED);
    }
    
    SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberId(spaceId, userId);
    spaceMemberRepository.delete(spaceMember);
    return "success";
  }

  public SpaceMemberResponse findSpacesByMemberId(Long spaceId, Long userId) {
    Space space = spaceRepository.findById(spaceId)
        .orElseThrow(() -> new CustomException(ErrorCode.SPACE_NOT_FOUND));

    SpaceMember spaceMember = spaceMemberRepository.findBySpaceIdAndMemberId(spaceId, userId);
    if (spaceMember == null) {
      throw new CustomException(ErrorCode.NOT_FOUND_USER);
    }
    return SpaceMemberResponse.of(spaceMember);
  }
}
