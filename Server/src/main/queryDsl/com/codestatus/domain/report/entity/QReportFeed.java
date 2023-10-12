package com.codestatus.domain.report.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReportFeed is a Querydsl query type for ReportFeed
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReportFeed extends EntityPathBase<ReportFeed> {

    private static final long serialVersionUID = -1513669525L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReportFeed reportFeed = new QReportFeed("reportFeed");

    public final com.codestatus.global.audit.QAuditable _super = new com.codestatus.global.audit.QAuditable(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.codestatus.domain.feed.entity.QFeed feed;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> reportId = createNumber("reportId", Long.class);

    public final com.codestatus.domain.user.entity.QUser user;

    public QReportFeed(String variable) {
        this(ReportFeed.class, forVariable(variable), INITS);
    }

    public QReportFeed(Path<? extends ReportFeed> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReportFeed(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReportFeed(PathMetadata metadata, PathInits inits) {
        this(ReportFeed.class, metadata, inits);
    }

    public QReportFeed(Class<? extends ReportFeed> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.feed = inits.isInitialized("feed") ? new com.codestatus.domain.feed.entity.QFeed(forProperty("feed"), inits.get("feed")) : null;
        this.user = inits.isInitialized("user") ? new com.codestatus.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

