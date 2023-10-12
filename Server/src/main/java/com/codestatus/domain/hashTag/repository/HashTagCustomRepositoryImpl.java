package com.codestatus.domain.hashTag.repository;

import com.codestatus.domain.hashTag.entity.HashTag;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.codestatus.domain.hashTag.entity.QHashTag.hashTag;

@RequiredArgsConstructor
@Repository
public class HashTagCustomRepositoryImpl implements HashTagCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Optional<HashTag> findHashTagByBody(String body) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(hashTag)
                        .where(
                                eqBody(body)
                        ).fetchOne()
        );
    }

    @Override
    public Page<HashTag> findHashTagByBodyLike(String body, Pageable pageable) {
        List<HashTag> content = getHashtags(body, pageable);
        JPAQuery<Long> countQuery = getCount(body);
        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetchOne());
    }
    private List<HashTag> getHashtags(String body, Pageable pageable) {

        return jpaQueryFactory
                .selectFrom(hashTag)
                .where(
                        containsBody(body)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private JPAQuery<Long> getCount(String body){
        return jpaQueryFactory
                .select(hashTag.count())
                .from(hashTag)
                .where(
                        containsBody(body)
                );
    }


    private BooleanExpression eqBody(String body){
        return StringUtils.hasText(body) ? hashTag.body.eq(body) : null;
    }

    private BooleanExpression containsBody(String body) {
        return StringUtils.hasText(body) ? hashTag.body.contains(body) : null;
    }
}
