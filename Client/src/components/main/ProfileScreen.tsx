import useUserInfo from '../../hooks/useUserInfo';
import Backdrop from '../common/Backdrop';
import Button from '../common/Button';
import { useState } from 'react';
import LoadingBar from '../common/LoadingBar';
import 'cropperjs/dist/cropper.css';
import { Link, useNavigate } from 'react-router-dom';
import ChangePwTab from './ChangePwTab';
import ProfileHeader from './ProfileHeader';

const ProfileScreen = () => {
  const [tab, setTab] = useState<'password' | 'post'>('post');

  const { userInfoQuery } = useUserInfo();
  const { isLoading, data: userInfo } = userInfoQuery;

  const navigate = useNavigate();

  if (isLoading)
    return (
      <Backdrop>
        <LoadingBar />
      </Backdrop>
    );

  if (!userInfo) {
    alert('정보를 불러오는 데 실패했습니다.');
    navigate('/main');
    return;
  }
  return (
    <Backdrop>
      <div className="relative w-full h-full flex flex-col justify-center items-center gap-[2rem]">
        <div className="relative w-[500px] h-[600px] p-[40px] bg-[url('/src/assets/common/modal-frame-paper.png')] bg-center bg-cover bg-no-repeat flex flex-col justify-between">
          <ProfileHeader userInfo={userInfo} />
          {/* 탭 메뉴 */}
          <div className="w-[400px] h-[310px]">
            {/* 탭 버튼 */}
            <div className="h-[50px] flex flex-row">
              <button
                style={{
                  backgroundColor: tab === 'post' ? '#bf916b' : '#ffc98f',
                }}
                className="text-[.7rem] flex-1 rounded-t-md"
                onClick={() => {
                  setTab('post');
                }}
              >
                MY POST
              </button>
              <button
                style={{
                  backgroundColor: tab === 'password' ? '#bf916b' : '#ffc98f',
                }}
                className="text-[.7rem] flex-1 rounded-t-md"
                onClick={() => {
                  setTab('password');
                }}
              >
                CHANGE PASSWORD
              </button>
            </div>
            {/* 탭 내용 */}
            <div className="w-full h-[260px] p-2 bg-[#bf916b] rounded-b-md">
              {tab === 'post' && <div className="w-full h-full">MY POST</div>}
              {tab === 'password' && <ChangePwTab />}
            </div>
          </div>
        </div>
        <Link to="/main">
          <Button>Close</Button>
        </Link>
      </div>
    </Backdrop>
  );
};

export default ProfileScreen;
