import Backdrop from '../components/common/Backdrop';
import Header from '../components/feed/Header';
import FeedList from '../components/feed/FeedList';
import { Outlet, useParams } from 'react-router';
import { CATEGORY_FEED_MAP } from '../utils/category';
import { CategoryCode } from '../api/category';

const FeedPage = () => {
  const { categoryCodeParam } = useParams();
  const categoryCode: CategoryCode = Number(categoryCodeParam);

  return (
    <div
      className={`flex flex-col justify-center items-center w-screen h-screen bg-center bg-cover ${CATEGORY_FEED_MAP[categoryCode]}`}
    >
      <Backdrop>
        <div className="flex flex-col justify-between items-center gap-[32px] mt-[32px]">
          <div className=" w-[1080px] h-[720px] p-[42px] bg-board bg-cover bg-center">
            <Header categoryCode={categoryCode} />
            <FeedList categoryCode={categoryCode} />
          </div>
        </div>
      </Backdrop>
      <Outlet />
    </div>
  );
};

export default FeedPage;
