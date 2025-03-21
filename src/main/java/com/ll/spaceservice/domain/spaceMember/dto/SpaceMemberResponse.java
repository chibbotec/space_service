package com.ll.spaceservice.domain.spaceMember.dto;

import com.ll.spaceservice.domain.spaceMember.entity.SpaceMember;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpaceMemberResponse {
  private Long id;
  private String nickname;

  public static SpaceMemberResponse of(SpaceMember spaceMember){
    return SpaceMemberResponse.builder()
        .id(spaceMember.getMemberId())
        .nickname(spaceMember.getNickname())
        .build();
  }
}
