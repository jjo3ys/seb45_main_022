package com.codestatus.domain.hashTag.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeedHashTag is a Querydsl query type for FeedHashTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeedHashTag extends EntityPathBase<FeedHashTag> {

    private static final long serialVersionUID = 1274729645L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFeedHashTag feedHashTag = new QFeedHashTag("feedHashTag");

    public final com.codestatus.domain.feed.entity.QFeed feed;

    public final NumberPath<Long> feedHashTagId = createNumber("feedHashTagId", Long.class);

    public final QHashTag hashTag;

    public QFeedHashTag(String variable) {
        this(FeedHashTag.class, forVariable(variable), INITS);
    }

    public QFeedHashTag(Path<? extends FeedHashTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFeedHashTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFeedHashTag(PathMetadata metadata, PathInits inits) {
        this(FeedHashTag.class, metadata, inits);
    }

    public QFeedHashTag(Class<? extends FeedHashTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.feed = inits.isInitialized("feed") ? new com.codestatus.domain.feed.entity.QFeed(forProperty("feed"), inits.get("feed")) : null;
        this.hashTag = inits.isInitialized("hashTag") ? new QHashTag(forProperty("hashTag")) : null;
    }

}

