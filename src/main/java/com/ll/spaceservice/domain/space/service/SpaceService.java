package com.ll.spaceservice.domain.space.service;

import com.ll.spaceservice.domain.space.dto.SpaceRequest;
import com.ll.spaceservice.domain.space.dto.SpaceResponse;
import com.ll.spaceservice.domain.space.entity.Space;
import com.ll.spaceservice.domain.space.entity.Space.SpaceType;
import com.ll.spaceservice.domain.space.repository.SpaceRepository;
import com.ll.spaceservice.domain.spaceMember.entity.SpaceMember;
import com.ll.spaceservice.domain.spaceMember.repository.SpaceMemberRepository;
import com.ll.spaceservice.domain.spaceMember.service.SpaceMemberService;
import com.ll.spaceservice.global.client.MemberResponse;
import com.ll.spaceservice.global.error.ErrorCode;
import com.ll.spaceservice.global.exception.CustomException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class SpaceService {

  private final SpaceRepository spaceRepository;
  private final SpaceMemberRepository spaceMemberRepository;
  private final SpaceMemberService spaceMemberService;

  @Transactional
  public SpaceResponse createSpace(MemberResponse loginUser, SpaceType type, SpaceRequest request) {
    Space space = Space.builder()
        .spaceName(request.getSpaceName())
        .spaceOwner(loginUser.getUsername())
        .type(type)
        .members(new ArrayList<>())
        .build();

    SpaceMember member = SpaceMember.builder()
        .space(space)
        .memberId(loginUser.getId())
        .nickname(loginUser.getUsername())
        .role(SpaceMember.MemberRole.OWNER)
        .build();

    space.addMember(member);

    if(request.getMembers() != null){
      request.getMembers().forEach(memberRequest -> {
        SpaceMember spaceMember = SpaceMember.builder()
            .space(space)
            .memberId(memberRequest.getId())
            .nickname(memberRequest.getNickname())
            .role(SpaceMember.MemberRole.MEMBER)
            .build();
        space.addMember(spaceMember);
      });
    }

    Space savedSpace = spaceRepository.save(space);
    return SpaceResponse.of(savedSpace);
  }

  public List<SpaceResponse> getMySpaces(MemberResponse loginUser) {
    List<Space> spaces = spaceMemberRepository.findSpacesByMemberId(loginUser.getId());

    return spaces.stream()
        .map(SpaceResponse::of)
        .collect(Collectors.toList());
  }

  public SpaceResponse getSpaceById(Long id) {
    Space space = spaceRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.SPACE_NOT_FOUND));

    return SpaceResponse.of(space);
  }

  @Transactional
  public SpaceResponse updateSpace(MemberResponse loginUser, Long id, SpaceRequest request) {
    Space space = spaceRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.SPACE_NOT_FOUND));
    log.info("spaceid: {}", space.getId());
    log.info("loginUser: {}", loginUser.getId());
    SpaceMember.MemberRole role = spaceMemberRepository.findRoleBySpaceIdAndMemberId(id,
        loginUser.getId());
    log.info("role: {}", role);
    if (role != SpaceMember.MemberRole.OWNER &&
        role != SpaceMember.MemberRole.ADMIN) {
      throw new CustomException(ErrorCode.PERMISSION_DENIED);
    }

    if (request.getSpaceName() != null) {
      space.setSpaceName(request.getSpaceName());
    }

    return SpaceResponse.of(space);
  }

  @Transactional
  public String deleteSpace(MemberResponse loginUser, Long id) {
    Space space = spaceRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.SPACE_NOT_FOUND));

    SpaceMember.MemberRole role = spaceMemberRepository.findRoleBySpaceIdAndMemberId(id,
        loginUser.getId());

    if (role != SpaceMember.MemberRole.OWNER &&
        role != SpaceMember.MemberRole.ADMIN) {
      throw new CustomException(ErrorCode.PERMISSION_DENIED);
    }

    spaceRepository.delete(space);
    return "Space deleted successfully";
  }
}
