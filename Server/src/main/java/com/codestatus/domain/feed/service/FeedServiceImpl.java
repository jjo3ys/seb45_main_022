package com.codestatus.domain.feed.service;

import com.codestatus.domain.comment.command.CommentCommand;
import com.codestatus.domain.feed.command.FeedCommand;
import com.codestatus.domain.feed.dto.FeedDto;
import com.codestatus.domain.hashTag.command.FeedHashTagCommand;
import com.codestatus.domain.feed.entity.Feed;
import com.codestatus.domain.feed.repository.FeedRepository;
import com.codestatus.auth.dto.PrincipalDto;
import com.codestatus.domain.utils.pageable.CustomPageImpl;
import com.codestatus.domain.utils.user.CheckUser;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {
    private final FeedRepository feedRepository;

    private final FeedCommand feedCommand;
    private final FeedHashTagCommand feedHashTagCommand;
    private final CommentCommand commentCommand;
    @Override
    public void createEntity(Feed feed) {
        feedRepository.save(feed);
    }

    @Override
    public FeedDto.FeedDetailDto findEntity(long feedId) {
        return feedCommand.findVerifiedFeedWithUserStatusesAndCategoryStat(feedId);
    }

    //피드리스트에서 유저가 좋아요한 피드 아이디 리스트
    @Override
    public List<Long> isLikeFeedIds(List<FeedDto.FeedListDto> feeds, PrincipalDto principal) {
        if(principal == null){
            return Collections.emptyList();
        }
        return feedRepository
                .findFeedsLikedByUserInList(principal.getId(), feeds.stream().map(feed ->feed.getFeedId()).collect(Collectors.toList()));
    }

    //카테고리 내 피드리스트 조회
    @Override
    @Transactional(readOnly = true)
    public Page<FeedDto.FeedListDto> findAllFeedByCategory(long categoryId, int page, int size) {
        return feedRepository.findAllByDeletedIsFalseAndCategoryId(
                categoryId,
                getPageable(page, size)
        );
    }

    //일주일 안에 작성된 피드를 좋아요 순으로 정렬해서 조회
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "Feeds", cacheManager = "redisCacheManager")
    public Page<FeedDto.FeedListDto> findAllWeeklyBestFeeds(long categoryId, int page, int size) {
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
        Page<FeedDto.FeedListDto> feedListDto = feedRepository.findFeedsByCategoryAndCreatedAtAndSortLikes(
                categoryId, oneWeekAgo,
                getPageable(page, size));
        return CustomPageImpl.of(feedListDto);
    }

    //(검색기능)텍스트 받아서 해당 카테고리 내에서 해당하는 바디 가지고 있는 피드목록 조회
    @Override
    @Transactional(readOnly = true)
    public Page<FeedDto.FeedListDto> findAllFeedByBodyAndCategory(long categoryId, String text, int page, int size) {
        return feedRepository.findAllByCategoryIdAndBodyContaining(
                categoryId, text,
                getPageable(page, size)
        );
    }

    //(검색기능)텍스트 받아서 해당 카테고리 내에서 해당하는 유저가 쓴 피드목록 조회
    @Override
    @Transactional(readOnly = true)
    public Page<FeedDto.FeedListDto> findAllFeedByUserAndCategory(long categoryId, String text, int page, int size) {
        return feedRepository.findAllByUserAndDeleted(
                categoryId, text,
                getPageable(page, size)
        );
    }

    //(검색기능)텍스트 받아서 해당 카테고리 내에서 해당하는 해쉬태그 가지고 있는 피드목록 조회
    @Override
    @Transactional(readOnly = true)
    public Page<FeedDto.FeedListDto> findFeedByHashTagAndCategory(long categoryId, long hashTagId, int page, int size) {
        return feedRepository.findAllByCategoryIdAndHashTagId(
                categoryId, hashTagId,
                getPageable(page, size)
        );
    }

    // hashTag Body 로 feed 조회
    @Override
    @Transactional(readOnly = true)
    public Page<FeedDto.FeedListDto> findFeedByHashTagBody(long categoryId, String body, int page, int size) {
        return feedRepository.findAllByCategoryIdAndHashTagBodyContaining(
                categoryId, body,
                getPageable(page, size)
        );
    }

    //삭제되지않은 피드목록 조회
    @Override
    @Transactional(readOnly = true)
    public Page<FeedDto.FeedListDto> findAllFeedByDeleted(int page, int size) {
        return feedRepository.findAllByDeletedIsFalse(
                getPageable(page, size)
        );
    }

    //관리자 용 모든 피드 조회
    @Override
    @Transactional(readOnly = true)
    public Page<Feed> findAllEntity(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return feedRepository.findAll(pageRequest);
    }

    @Override
    public int getStatId(long feedId) {
        return feedCommand.findVerifiedFeedWithFeedCategoryStat(feedId).getCategory().getStat().getStatId().intValue();
    }

    // feed가 존재하는지, 요청한 유저와 리소스의 주인이 일치하는지 검사하고,
    // body값의 null 판별을 통해 수정
    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public void updateEntity(Feed feed, long userId) {
        Feed findFeed = feedCommand.findVerifiedFeedWithUser(feed.getFeedId());
        CheckUser.isCreator(findFeed.getUser().getUserId(), userId);

        Optional.ofNullable(feed.getBody())
                .ifPresent(findFeed::setBody);
        Optional.ofNullable(feed.getData())
                .ifPresent(findFeed::setData);

        feedRepository.save(findFeed);
    }

    // 로그인한 유저가 작성한 피드 조회
    @Override
    @Transactional(readOnly = true)
    public Page<FeedDto.FeedListDto> userPost(long userId, int page, int size) {
        return feedRepository.findAllByUserId(
                userId,
                getPageable(page, size)
        );
    }

    //db에서 완전 삭제가 아닌 deleted=true 로 수정
    @Override
    public void deleteEntity(long feedId, long userId) {
        Feed findFeed = feedCommand.findVerifiedFeedWithUser(feedId);
        CheckUser.isCreator(findFeed.getUser().getUserId(), userId);
        findFeed.setDeleted(true);
        feedRepository.save(findFeed);

        commentCommand.deleteCommentAll(findFeed.getComments());
        feedHashTagCommand.deleteFeedHashtagAll(findFeed.getFeedHashTags());
    }

    private Pageable getPageable(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return PageRequest.of(page, size, sort);
    }
}
