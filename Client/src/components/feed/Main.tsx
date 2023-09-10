import FilterButton from './FilterButton';
import SearchBar from './SearchBar';
import { Feed } from '../../api/feed';
import { CategoryCode } from '../../api/category';
import { Link } from 'react-router-dom';
import { useState } from 'react';
import LatestFeedItem from './LatestFeedItem';
import BestFeedItem from './BestFeedItem';

interface Props {
  categoryCode: CategoryCode;
  latestFeedQueries: Feed[];
  bestFeedQueries: Feed[];
}

const Main = ({ latestFeedQueries, bestFeedQueries, categoryCode }: Props) => {
  const [latestFeeds, setLatestFeed] = useState<boolean>(true);
  const [bestFeeds, setBestFeeds] = useState<boolean>(false);

  const handleFilterNewest = () => {
    setLatestFeed(true);
    setBestFeeds(false);
    console.log('최신순');
  };

  const handleFilterByBest = () => {
    setBestFeeds(true);
    setLatestFeed(false);
    console.log('주간 베스트');
  };

  return (
    <div className="relative w-full h-[31.25rem] flex flex-col justify-start items-center mt-[55px] ml-1 ">
      <div className="w-full h-[3rem] flex justify-around items-center bg-[#f8d8ae] gap-[20rem]">
        <FilterButton
          latestFeeds={latestFeeds}
          bestFeeds={bestFeeds}
          handleFilterNewest={handleFilterNewest}
          handleFilterByBest={handleFilterByBest}
        />
        <SearchBar categoryCode={categoryCode} />
      </div>
      <div className="flex items-center justify-around w-[1000px] flex-wrap p-3  overflow-y-scroll ">
        {latestFeeds
          ? latestFeedQueries.map((feed) => (
              <LatestFeedItem
                key={feed.feedId}
                feed={feed}
                categoryCode={categoryCode}
              />
            ))
          : bestFeedQueries.map((feed) => (
              <BestFeedItem
                key={feed.feedId}
                feed={feed}
                categoryCode={categoryCode}
              />
            ))}
      </div>
      <Link to="/post">
        <button className="absolute bottom-5 right-5 w-[50px] h-[50px] bg-[#f8d8ae] bg-[url('/src/assets/icons/btn-pencil.png')] bg-no-repeat bg-cover rounded-full p-2 duration-300 shadow-[0_0_5px_#e1772d] hover:brightness-110 hover:scale-110" />
      </Link>
    </div>
  );
};

export default Main;
