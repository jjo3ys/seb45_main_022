import { useMutation } from '@tanstack/react-query';
import { addCommentData } from '../api/comment';

interface Params {
  feedId: number;
}

function useCommentPostMutation({ feedId }: Params) {
  return useMutation((body: string) => addCommentData({ feedId, body }), {
    meta: {
      errorMessage: 'Failed to post comment',
    },
  });
}

export default useCommentPostMutation;
