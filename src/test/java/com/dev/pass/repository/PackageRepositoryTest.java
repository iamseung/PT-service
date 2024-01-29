package com.dev.pass.repository;

import com.dev.pass.repository.packaze.PackageEntity;
import com.dev.pass.repository.packaze.PackageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class PackageRepositoryTest {

    @Autowired
    private PackageRepository packageRepository;

    @Test
    @DisplayName("패키지를 저장했을 때 PK 가 정상적으로 삽입됨을 확인")
    void save() {
        // Given
        PackageEntity packageEntity = new PackageEntity();
        packageEntity.setPackageName("바디 챌린지 PT 12주");
        packageEntity.setPeriod(78);

        // When
        packageRepository.save(packageEntity);

        // Then
        assertNotNull(packageEntity.getPackageSeq());
    }

    @Test
    void test_findByCreatedAtAfter() {
        // Given
        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(1); // 지금 시점에서 1분 전

        PackageEntity packageEntity0 = new PackageEntity();
        packageEntity0.setPackageName("학생 전용 3개월");
        packageEntity0.setPeriod(90);
        packageRepository.save(packageEntity0);

        PackageEntity packageEntity1 = new PackageEntity();
        packageEntity1.setPackageName("학생 전용 6개월");
        packageEntity1.setPeriod(180);
        packageRepository.save(packageEntity1);

        // When
        final List<PackageEntity> packageEntities = packageRepository.findByCreatedAtAfter(dateTime, PageRequest.of(0, 1, Sort.by("packageSeq").descending()));

        // Then
        assertEquals(1, packageEntities.size());
        assertEquals(packageEntity1.getPackageSeq(), packageEntities.get(0).getPackageSeq());
    }

    @Test
    void test_updateCountAndPeriod() {
        // Given
        PackageEntity packageEntity = new PackageEntity();
        packageEntity.setPackageName("바디프로필 이벤트 4개월");
        packageEntity.setPeriod(90);
        packageRepository.save(packageEntity);

        // When
        int updateCount = packageRepository.updateCountAndPeriod(packageEntity.getPackageSeq(), 30, 120);
        PackageEntity updatePackageEntity = packageRepository.findById(packageEntity.getPackageSeq()).get();

        // Then
        assertEquals(1, updateCount);
        assertEquals(30, updatePackageEntity.getCount());
        assertEquals(120, updatePackageEntity.getPeriod());
    }

    @Test
    void test_delete() {
        // Given
        PackageEntity packageEntity = new PackageEntity();
        packageEntity.setPackageName("제거할 이용권");
        packageEntity.setPeriod(1);
        PackageEntity newPackageEntity = packageRepository.save(packageEntity);

        // When
        packageRepository.deleteById(newPackageEntity.getPackageSeq());
        // Then
        assertTrue(packageRepository.findById(newPackageEntity.getPackageSeq()).isEmpty());
    }
}