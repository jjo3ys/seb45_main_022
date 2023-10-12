package com.codestatus.domain.status.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStatus is a Querydsl query type for Status
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStatus extends EntityPathBase<Status> {

    private static final long serialVersionUID = 435327529L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStatus status = new QStatus("status");

    public final NumberPath<Integer> requiredExp = createNumber("requiredExp", Integer.class);

    public final QStat stat;

    public final NumberPath<Integer> statExp = createNumber("statExp", Integer.class);

    public final NumberPath<Integer> statLevel = createNumber("statLevel", Integer.class);

    public final NumberPath<Long> statusId = createNumber("statusId", Long.class);

    public final com.codestatus.domain.user.entity.QUser user;

    public QStatus(String variable) {
        this(Status.class, forVariable(variable), INITS);
    }

    public QStatus(Path<? extends Status> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStatus(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStatus(PathMetadata metadata, PathInits inits) {
        this(Status.class, metadata, inits);
    }

    public QStatus(Class<? extends Status> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.stat = inits.isInitialized("stat") ? new QStat(forProperty("stat")) : null;
        this.user = inits.isInitialized("user") ? new com.codestatus.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

