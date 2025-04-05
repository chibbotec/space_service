package com.ll.spaceservice.domain.space.eventListener;

import static com.ll.spaceservice.domain.space.entity.Space.SpaceType.PERSONAL;

import com.ll.spaceservice.domain.space.dto.SpaceRequest;
import com.ll.spaceservice.domain.space.service.SpaceService;
import com.ll.spaceservice.global.client.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpaceEventListener {

  private final SpaceService spaceService;

  @KafkaListener(
      topics = "signup",
      groupId = "space",
      containerFactory = "kafkaListenerContainerFactory"
  )public void consumeSingup(MemberResponse response) {
    SpaceRequest request = SpaceRequest.builder()
        .spaceName(response.getNickname() + "의 개인 스페이스")
        .build();
    spaceService.createSpace(response, PERSONAL, request);
  }
}
