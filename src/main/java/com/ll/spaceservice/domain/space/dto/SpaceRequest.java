package com.ll.spaceservice.domain.space.dto;

import com.ll.spaceservice.domain.spaceMember.dto.SpaceMemberRequest;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpaceRequest {
  @NotNull(message = "공간명은 필수입니다")
  private String spaceName;

  @Nullable
  private List<SpaceMemberRequest> members;
}