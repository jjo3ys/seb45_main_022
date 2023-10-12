package com.codestatus.domain.feed.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFeed is a Querydsl query type for Feed
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFeed extends EntityPathBase<Feed> {

    private static final long serialVersionUID = -1863923903L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFeed feed = new QFeed("feed");

    public final com.codestatus.global.audit.QAuditable _super = new com.codestatus.global.audit.QAuditable(this);

    public final StringPath body = createString("body");

    public final com.codestatus.domain.category.entity.QCategory category;

    public final ListPath<com.codestatus.domain.comment.entity.Comment, com.codestatus.domain.comment.entity.QComment> comments = this.<com.codestatus.domain.comment.entity.Comment, com.codestatus.domain.comment.entity.QComment>createList("comments", com.codestatus.domain.comment.entity.Comment.class, com.codestatus.domain.comment.entity.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath data = createString("data");

    public final BooleanPath deleted = createBoolean("deleted");

    public final ListPath<com.codestatus.domain.hashTag.entity.FeedHashTag, com.codestatus.domain.hashTag.entity.QFeedHashTag> feedHashTags = this.<com.codestatus.domain.hashTag.entity.FeedHashTag, com.codestatus.domain.hashTag.entity.QFeedHashTag>createList("feedHashTags", com.codestatus.domain.hashTag.entity.FeedHashTag.class, com.codestatus.domain.hashTag.entity.QFeedHashTag.class, PathInits.DIRECT2);

    public final NumberPath<Long> feedId = createNumber("feedId", Long.class);

    public final ListPath<com.codestatus.domain.like.entity.Like, com.codestatus.domain.like.entity.QLike> likes = this.<com.codestatus.domain.like.entity.Like, com.codestatus.domain.like.entity.QLike>createList("likes", com.codestatus.domain.like.entity.Like.class, com.codestatus.domain.like.entity.QLike.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final com.codestatus.domain.user.entity.QUser user;

    public QFeed(String variable) {
        this(Feed.class, forVariable(variable), INITS);
    }

    public QFeed(Path<? extends Feed> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFeed(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFeed(PathMetadata metadata, PathInits inits) {
        this(Feed.class, metadata, inits);
    }

    public QFeed(Class<? extends Feed> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.codestatus.domain.category.entity.QCategory(forProperty("category"), inits.get("category")) : null;
        this.user = inits.isInitialized("user") ? new com.codestatus.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

