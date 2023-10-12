package com.codestatus.domain.status.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStat is a Querydsl query type for Stat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStat extends EntityPathBase<Stat> {

    private static final long serialVersionUID = -616306101L;

    public static final QStat stat = new QStat("stat");

    public final ListPath<com.codestatus.domain.category.entity.Category, com.codestatus.domain.category.entity.QCategory> categories = this.<com.codestatus.domain.category.entity.Category, com.codestatus.domain.category.entity.QCategory>createList("categories", com.codestatus.domain.category.entity.Category.class, com.codestatus.domain.category.entity.QCategory.class, PathInits.DIRECT2);

    public final NumberPath<Long> statId = createNumber("statId", Long.class);

    public final StringPath statName = createString("statName");

    public QStat(String variable) {
        super(Stat.class, forVariable(variable));
    }

    public QStat(Path<? extends Stat> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStat(PathMetadata metadata) {
        super(Stat.class, metadata);
    }

}

