import likeButton from '../../assets/feed/likeButton.png';
import commentButton from '../../assets/feed/commentButton.png';
import { CategoryCode } from '../../api/category';
import { STATUS_ICON } from '../../utility/status';
import { CATEGORY_STATUS_MAP } from '../../utility/category';
import { Feed } from '../../api/feed';
import UserPost from '../post/UserPost';
import { useState } from 'react';

interface FeedItemProps {
  feed: Feed;
  categoryCode: CategoryCode;
}

const LatestFeedItem = ({ feed, categoryCode }: FeedItemProps) => {
  const {
    nickname,
    profileImage,
    level,
    body,
    likeCount,
    commentCount,
    createdAt,
  } = feed;

  const [openFeedItem, setOpenFeedItem] = useState(false);

  const handleShowPost = () => {
    setOpenFeedItem(true);
  };

  const handleCloseScreen = () => {
    setOpenFeedItem(false);
  };

  return (
    <>
      {openFeedItem && <UserPost handleCloseScreen={handleCloseScreen} />}

      <div
        onClick={handleShowPost}
        className="w-[295px] h-[137px] hover:cursor-pointer m-3 bg-cover bg-center bg-feedBox"
      >
        <div className="w-full h-full p-7 flex">
          {/* 왼쪽 구간 (전체 너비 1/3) */}
          <div className="w-[70px] h-full flex flex-col justify-between items-start gap-1 cursor-pointer">
            <div className="w-[35px] h-[35px] mt-[3px] ml-[10px]">
              <img src={profileImage} alt="profile" />
            </div>
            <div className="w-[60px] mt-1 ml-[6px] text-[1rem]">{nickname}</div>
            <div className="w-full flex justify-start items-center -mt-1">
              <div className="w-[10px]">
                <img
                  src={STATUS_ICON[CATEGORY_STATUS_MAP[categoryCode]]}
                  alt="스탯 아이콘"
                />
              </div>
              <div className="text-[10px]">Lv_{level}</div>
            </div>
          </div>

          {/* 오른쪽 구간 (전체 너비 2/3) */}
          <div className="w-full h-full flex flex-col justify-between items-end">
            <div className="w-full h-[35px] font-[Pretendard] cursor-pointer">
              {/* {body.length > 20 ? `${body.slice(0, 20)}...` : body} */}
              {body.slice(0, 20)}
            </div>
            <div className="w-full flex justify-end items-center gap-1.5">
              <div className="text-[10px] font-[Pretendard]">
                {new Date(createdAt).toLocaleTimeString('ko-KR', {
                  year: '2-digit',
                  month: '2-digit',
                  day: '2-digit',
                  hour: '2-digit',
                  minute: '2-digit',
                })}
              </div>
              <div className="flex gap-1 text-[10px] cursor-pointer">
                <img src={likeButton} alt="좋아요 아이콘" width={15} />
                {likeCount}
              </div>
              <div className="flex gap-1 text-[10px] mr-1 cursor-pointer">
                <img src={commentButton} alt="코멘트 아이콘" width={15} />
                {commentCount}
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default LatestFeedItem;
