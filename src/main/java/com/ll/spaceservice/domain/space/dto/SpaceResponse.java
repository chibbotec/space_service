package com.ll.spaceservice.domain.space.dto;

import com.ll.spaceservice.domain.space.entity.Space;
import com.ll.spaceservice.domain.spaceMember.dto.SpaceMemberResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpaceResponse {
  private Long id;
  private String spaceName;
  private String spaceOwner;
  private String type; // PERSONAL 또는 TEAM
  private List<SpaceMemberResponse> members;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public static SpaceResponse of(Space space){
    return SpaceResponse.builder()
        .id(space.getId())
        .spaceName(space.getSpaceName())
        .spaceOwner(space.getSpaceOwner())
        .type(space.getType().name())
        .members(space.getMembers().stream().map(SpaceMemberResponse::of).toList())
        .createdAt(space.getCreatedAt())
        .updatedAt(space.getUpdatedAt())
        .build();
  }
}
