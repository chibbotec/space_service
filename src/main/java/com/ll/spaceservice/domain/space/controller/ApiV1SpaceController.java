package com.ll.spaceservice.domain.space.controller;


import com.ll.spaceservice.domain.space.dto.SpaceRequest;
import com.ll.spaceservice.domain.space.dto.SpaceResponse;
import com.ll.spaceservice.domain.space.entity.Space.SpaceType;
import com.ll.spaceservice.domain.space.service.SpaceService;
import com.ll.spaceservice.global.client.MemberResponse;
import com.ll.spaceservice.global.webMvc.LoginUser;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/space")
@RequiredArgsConstructor
public class ApiV1SpaceController {

  private final SpaceService spaceService;

  //GET /api/spaces - 내 스페이스 목록 (개인/팀 모두)
  @GetMapping
  public ResponseEntity<List<SpaceResponse>> getMySpaces(@LoginUser MemberResponse loginUser) {
    return ResponseEntity.ok(spaceService.getMySpaces(loginUser));
  }

  //GET /api/spaces/{id} - 스페이스 정보 조회
  @GetMapping("/{id}")
  public ResponseEntity<SpaceResponse> getSpaceById(
      @PathVariable("id") Long id) {
    return ResponseEntity.ok(spaceService.getSpaceById(id));
  }

  //POST /api/spaces - 스페이스 생성 (type으로 개인/팀 구분)
  @PostMapping
  public ResponseEntity<SpaceResponse> createSpace(
      @LoginUser MemberResponse loginUser,
      @RequestParam("type") SpaceType type,
      @RequestBody SpaceRequest request) {
    return ResponseEntity.ok(spaceService.createSpace(loginUser, type, request));
  }

  //PATCH /api/spaces/{id} - 스페이스 정보 수정
  @PatchMapping("/{id}")
  public ResponseEntity<SpaceResponse> patchSpace(
      @LoginUser MemberResponse loginUser,
      @PathVariable("id") Long id,
      @RequestBody SpaceRequest request) {
    return ResponseEntity.ok(spaceService.updateSpace(loginUser, id, request));
  }

  //DELETE /api/spaces/{id} - 스페이스 삭제
  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteSpace(
      @LoginUser MemberResponse loginUser,
      @PathVariable("id") Long id) {
    return ResponseEntity.ok(spaceService.deleteSpace(loginUser, id));
  }
}
