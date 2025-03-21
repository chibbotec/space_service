package com.ll.spaceservice.domain.space.entity;

import static lombok.AccessLevel.PROTECTED;

import com.ll.spaceservice.domain.spaceMember.entity.SpaceMember;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Getter
@Setter
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@ToString(exclude = "members") // 순환 참조 방지
@EqualsAndHashCode(exclude = "members") // 순환 참조 방지
@Entity
public class Space {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String spaceName;

  @Column(nullable = false)
  private String spaceOwner;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private SpaceType type;

  @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SpaceMember> members = new ArrayList<>();

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  // 공간 타입 정의
  public enum SpaceType {
    PERSONAL, TEAM
  }

  // 편의 메서드: 멤버 추가
  public void addMember(SpaceMember member) {
    members.add(member);
    member.setSpace(this);
  }

  // 편의 메서드: 멤버 제거
  public void removeMember(SpaceMember member) {
    members.remove(member);
    member.setSpace(null);
  }
}