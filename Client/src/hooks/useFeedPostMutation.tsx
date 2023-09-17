import { useMutation } from '@tanstack/react-query';
import { PostFeedData, postFeed } from '../api/feed';

function useFeedPostMutation() {
  return useMutation((postFeedData: PostFeedData) => postFeed(postFeedData), {
    meta: {
      errorMessage: 'Failed to post feed',
    },
  });
}

export default useFeedPostMutation;
