package com.codestatus.global.event.handler;

import com.codestatus.domain.status.entity.Status;
import com.codestatus.domain.status.repository.StatusRepository;
import com.codestatus.domain.utils.exp.repository.ExpTableRepository;
import com.codestatus.global.event.GotExpEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Component
public class ExpEventHandler {
    private final StatusRepository statusRepository;
    private final ExpTableRepository expTableRepository;

    @Async
    @EventListener
    public void gainExp(GotExpEvent event) {
        event.getStatus().setStatExp(
                event.getStatus().getStatExp() + event.getExp()
        );
        levelUpCheck(event.getStatus());
    }
    private void levelUpCheck(Status status) { // chooseStat: 1(str), 2(dex), 3(int), 4(charm), 5(vitality)
        int currentLevel = status.getStatLevel(); // 현재 레벨
        int currentExp = status.getStatExp(); // 현재 경험치
        int requiredExp = expTableRepository.findById((long) currentLevel).get().getRequired(); // 필요 경험치
        int maxLevel = 100; // 최대 레벨

        if (currentLevel >= maxLevel) { // 현재 레벨이 최대 레벨이라면 레벨업 불가
            return;
        }

        if (currentExp >= requiredExp) { // 현재 경험치가 필요 경험치보다 많다면 레벨업
            currentLevel += 1; // 레벨업
            status.setStatLevel(currentLevel); // 레벨 저장
            currentExp -= requiredExp; // 현재 경험치에서 필요 경험치 차감
            status.setStatExp(currentExp); // 경험치 차감
        }

        // 현재 레벨에서 다음 레벨까지 필요한 경험치 = 다음 레벨까지 필요한 경험치 - 현재 레벨까지 필요한 경험치
        int nextLevelRequiredExp = expTableRepository.findById((long) (currentLevel)).get().getRequired() - currentExp;
        status.setRequiredExp(nextLevelRequiredExp); // 다음 레벨까지 필요한 경험치 저장

        statusRepository.save(status); // 유저 정보 저장
    }
}
