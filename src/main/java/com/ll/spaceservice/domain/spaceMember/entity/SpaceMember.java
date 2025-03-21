package com.ll.spaceservice.domain.spaceMember.entity;

import static lombok.AccessLevel.PROTECTED;

import com.ll.spaceservice.domain.space.entity.Space;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Getter
@Setter
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
@ToString
@EqualsAndHashCode
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"space_id", "memberId"})
})
public class SpaceMember {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "space_id", nullable = false)
  private Space space;

  @Column(nullable = false)
  private Long memberId; // 외부 서비스에서 받아오는 회원 ID

  private String nickname; // 외부 서비스에서 받아오는 회원 닉네임

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private MemberRole role;

  @CreatedDate
  private LocalDateTime joinedAt;

  // 회원 역할 정의
  public enum MemberRole {
    OWNER, ADMIN, MEMBER
  }
}