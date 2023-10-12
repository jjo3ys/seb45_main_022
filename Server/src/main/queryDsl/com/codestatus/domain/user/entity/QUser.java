package com.codestatus.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1113327589L;

    public static final QUser user = new QUser("user");

    public final com.codestatus.global.audit.QAuditable _super = new com.codestatus.global.audit.QAuditable(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final ListPath<com.codestatus.domain.feed.entity.Feed, com.codestatus.domain.feed.entity.QFeed> feeds = this.<com.codestatus.domain.feed.entity.Feed, com.codestatus.domain.feed.entity.QFeed>createList("feeds", com.codestatus.domain.feed.entity.Feed.class, com.codestatus.domain.feed.entity.QFeed.class, PathInits.DIRECT2);

    public final ListPath<com.codestatus.domain.like.entity.Like, com.codestatus.domain.like.entity.QLike> likes = this.<com.codestatus.domain.like.entity.Like, com.codestatus.domain.like.entity.QLike>createList("likes", com.codestatus.domain.like.entity.Like.class, com.codestatus.domain.like.entity.QLike.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath profileImage = createString("profileImage");

    public final ListPath<String, StringPath> roles = this.<String, StringPath>createList("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public final ListPath<com.codestatus.domain.status.entity.Status, com.codestatus.domain.status.entity.QStatus> statuses = this.<com.codestatus.domain.status.entity.Status, com.codestatus.domain.status.entity.QStatus>createList("statuses", com.codestatus.domain.status.entity.Status.class, com.codestatus.domain.status.entity.QStatus.class, PathInits.DIRECT2);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final EnumPath<User.UserStatus> userStatus = createEnum("userStatus", User.UserStatus.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

