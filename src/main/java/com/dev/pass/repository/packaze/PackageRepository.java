package com.dev.pass.repository.packaze;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface PackageRepository extends JpaRepository<PackageEntity, Integer> {
    /*
    select
        packageent0_.package_seq as package_1_0_,
        packageent0_.created_at as created_2_0_,
        packageent0_.modified_at as modified3_0_,
        packageent0_.count as count4_0_,
        packageent0_.package_name as package_5_0_,
        packageent0_.period as period6_0_
    from
        package packageent0_
    where
        packageent0_.created_at>?
    order by
        packageent0_.package_seq desc limit ?
    */
    List<PackageEntity> findByCreatedAtAfter(LocalDateTime dateTime, Pageable pageable);

    /*
    Spring Data JPA의 쿼리 메서드에서 명명된 파라미터를 사용할 때,
    각 메서드 파라미터에 대한 이름을 명시적으로 제공해야 함
     */
    @Transactional
    @Modifying // 데이터 변경시 사용하는 어노테이션
    @Query(value = "UPDATE PackageEntity p " +
            "           SET p.count = :count," +
            "               p.period = :period" +
            "         WHERE p.packageSeq = :packageSeq")
    int updateCountAndPeriod(@Param("packageSeq") Integer packageSeq,
                             @Param("count") Integer count,
                             @Param("period") Integer period);
}
