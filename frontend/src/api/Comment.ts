import { createRequest } from '@/api/ApiProvider';
import { HTTP_METHODS } from '@/helpers/constants';

import type { ICreateCommentAPI } from '@/helpers/interfaces';

interface ICreateCommentApiArgs {
  assignmentId: number,
  data: ICreateCommentAPI
}

interface ISetIsCommentReadApiArgs {
  assignmentId: number,
  commentId: number
}

export const getCommentsApi = async (assignmentId: number) => {
  return createRequest({
    endpoint: `api/v1/${assignmentId}/comment`,
    method: HTTP_METHODS.get
  });
};

export const createCommentsApi = async ({assignmentId, data}: ICreateCommentApiArgs) => {
  return createRequest({
    endpoint: `api/v1/insurance/${assignmentId}/comment`,
    method: HTTP_METHODS.post,
    data: JSON.stringify(data)
  });
};

export const setIsCommentReadApi = async ({assignmentId, commentId}: ISetIsCommentReadApiArgs) => {
  return createRequest({
    endpoint: `api/v1/repair/${assignmentId}/comment/${commentId}`,
    method: HTTP_METHODS.put
  });
};