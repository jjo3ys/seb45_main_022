package com.codestatus.domain.status.entity;

import com.codestatus.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@OptimisticLocking(type = OptimisticLockType.VERSION)
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stat_id")
    private Stat stat;

    @Column(nullable = false)
    private int statLevel;

    @Column(nullable = false)
    private int statExp;

    @Column(nullable = false)
    private int requiredExp;

//    @Version
//    private Long version;
}
