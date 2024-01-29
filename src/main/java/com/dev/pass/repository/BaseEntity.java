package com.dev.pass.repository;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/*
@MappedSuperclass
: 엔티티가 해당 엔티티를 상속하게 되면 두 필드를 모두 컬럼으로 인식한다
@EntityListeners(AuditingEntityListener.class)
: jpa 엔티티의 이벤트가 발생할 때 콜백 처리하고 코드를 실행

이러한 설정들이 enable 하게 /config/JpaConfig 에 @EnableJpaAuditing
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}
