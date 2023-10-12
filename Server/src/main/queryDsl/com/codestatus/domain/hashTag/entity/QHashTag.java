package com.codestatus.domain.hashTag.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHashTag is a Querydsl query type for HashTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHashTag extends EntityPathBase<HashTag> {

    private static final long serialVersionUID = 886319019L;

    public static final QHashTag hashTag = new QHashTag("hashTag");

    public final StringPath body = createString("body");

    public final BooleanPath deleted = createBoolean("deleted");

    public final ListPath<FeedHashTag, QFeedHashTag> feedHashTags = this.<FeedHashTag, QFeedHashTag>createList("feedHashTags", FeedHashTag.class, QFeedHashTag.class, PathInits.DIRECT2);

    public final NumberPath<Long> hashTagId = createNumber("hashTagId", Long.class);

    public QHashTag(String variable) {
        super(HashTag.class, forVariable(variable));
    }

    public QHashTag(Path<? extends HashTag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHashTag(PathMetadata metadata) {
        super(HashTag.class, metadata);
    }

}

