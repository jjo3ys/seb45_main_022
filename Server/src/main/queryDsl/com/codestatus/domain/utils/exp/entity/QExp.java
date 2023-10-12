package com.codestatus.domain.utils.exp.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QExp is a Querydsl query type for Exp
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExp extends EntityPathBase<Exp> {

    private static final long serialVersionUID = 936452968L;

    public static final QExp exp = new QExp("exp");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> required = createNumber("required", Integer.class);

    public QExp(String variable) {
        super(Exp.class, forVariable(variable));
    }

    public QExp(Path<? extends Exp> path) {
        super(path.getType(), path.getMetadata());
    }

    public QExp(PathMetadata metadata) {
        super(Exp.class, metadata);
    }

}

