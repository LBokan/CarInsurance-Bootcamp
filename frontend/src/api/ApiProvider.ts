import { getToken, isTokenValid, logout } from '@/helpers/authorization';
import { BASE_API_URL } from '@/helpers/constants';

interface IHeaders {
  [n: string]: string
};

interface ICreateRequestArgs {
  endpoint: string,
  method: string,
  isMultipartHeaders?: boolean,
  isGetPhoto?: boolean,
  data?: string | FormData | null
};

interface IOptions {
  method: string,
  headers: IHeaders,
  body: string | FormData | null,
  credentials: RequestCredentials
};

export const createRequest = async ({
  endpoint, 
  method,
  isMultipartHeaders = false,
  isGetPhoto = false,
  data = null
}: ICreateRequestArgs) => {
  const token = getToken();

  if (token && !isTokenValid()) {
    logout();
  }

  const url = `${BASE_API_URL}/${endpoint}`;
  const headers: IHeaders = {};

  if (!isMultipartHeaders) {
    headers['Content-Type'] = 'application/json';
  }
  
  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }

  const options: IOptions = {
    method,
    headers,
    body: data,
    credentials: 'include'
  };

  const response = await fetch(url, options);

  if (!response.ok) {
    const errorData = await response.text();

    throw new Error(errorData);
  }

  if (isGetPhoto) {
    return response;
  }

  return response.json();
}
