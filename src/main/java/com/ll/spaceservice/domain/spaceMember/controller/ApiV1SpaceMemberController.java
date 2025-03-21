package com.ll.spaceservice.domain.spaceMember.controller;


import com.ll.spaceservice.domain.spaceMember.dto.SpaceMemberRequest;
import com.ll.spaceservice.domain.spaceMember.dto.SpaceMemberResponse;
import com.ll.spaceservice.domain.spaceMember.dto.SpaceMemberRoleRequest;
import com.ll.spaceservice.domain.spaceMember.service.SpaceMemberService;
import com.ll.spaceservice.global.client.MemberResponse;
import com.ll.spaceservice.global.webMvc.LoginUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/space/{id}/members")
@RequiredArgsConstructor
public class ApiV1SpaceMemberController {

  private final SpaceMemberService spaceMemberService;

  //GET /api/spaces - 내 스페이스 팀원 목록 (개인/팀 모두)
  @GetMapping
  public ResponseEntity<List<SpaceMemberResponse>> getMyMembers(
      @PathVariable("id") Long spaceId
  ) {
    return ResponseEntity.ok(spaceMemberService.getMyMembers(spaceId));
  }

  //POST /api/spaces - 스페이스 팀원 추가
  @PostMapping
  public ResponseEntity<List<SpaceMemberResponse>> addMember(
      @PathVariable("id") Long spaceId,
      @RequestBody List<SpaceMemberRequest> request) {
    return ResponseEntity.ok(spaceMemberService.addMember(spaceId, request));
  }

  //PUT /api/spaces/{id}/members/{userId}/role - 멤버 역할 변경
  @PutMapping("/{userId}")
  public ResponseEntity<String> changeRole(
      @LoginUser MemberResponse loginUser,
      @PathVariable("id") Long spaceId,
      @PathVariable("userId") Long userId,
      @RequestBody SpaceMemberRoleRequest request) {
    return ResponseEntity.ok(spaceMemberService.changeRole(loginUser, spaceId, userId, request));
  }

  //DELETE /api/spaces/{id} - 스페이스 팀원 삭제
  @DeleteMapping("/{userId}")
  public ResponseEntity<String> deleteSpace(
      @LoginUser MemberResponse loginUser,
      @PathVariable("id") Long spaceId,
      @PathVariable("userId") Long userId) {
    return ResponseEntity.ok(spaceMemberService.deleteMember(loginUser, spaceId, userId));
  }
}
