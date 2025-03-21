package com.ll.spaceservice.domain.spaceMember.dto;

import com.ll.spaceservice.domain.spaceMember.entity.SpaceMember.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SpaceMemberRoleRequest {
  private MemberRole role;
}
