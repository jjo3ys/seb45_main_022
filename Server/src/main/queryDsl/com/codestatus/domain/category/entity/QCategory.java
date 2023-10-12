package com.codestatus.domain.category.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategory extends EntityPathBase<Category> {

    private static final long serialVersionUID = 355113089L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCategory category1 = new QCategory("category1");

    public final StringPath category = createString("category");

    public final NumberPath<Long> categoryId = createNumber("categoryId", Long.class);

    public final ListPath<com.codestatus.domain.feed.entity.Feed, com.codestatus.domain.feed.entity.QFeed> feeds = this.<com.codestatus.domain.feed.entity.Feed, com.codestatus.domain.feed.entity.QFeed>createList("feeds", com.codestatus.domain.feed.entity.Feed.class, com.codestatus.domain.feed.entity.QFeed.class, PathInits.DIRECT2);

    public final com.codestatus.domain.status.entity.QStat stat;

    public QCategory(String variable) {
        this(Category.class, forVariable(variable), INITS);
    }

    public QCategory(Path<? extends Category> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCategory(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCategory(PathMetadata metadata, PathInits inits) {
        this(Category.class, metadata, inits);
    }

    public QCategory(Class<? extends Category> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.stat = inits.isInitialized("stat") ? new com.codestatus.domain.status.entity.QStat(forProperty("stat")) : null;
    }

}

