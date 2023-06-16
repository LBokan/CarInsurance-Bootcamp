import { getToken } from '@/helpers/authorization';
import { 
  BASE_API_URL
} from '@/helpers/authConstants';

interface IHeaders {
  [n: string]: string
};

interface ICreateRequestArgs {
  endpoint: string,
  method: string,
  isMultipartHeaders?: boolean,
  data: string | FormData | null
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
  data = null
}: ICreateRequestArgs) => {
  const url = `${BASE_API_URL}/${endpoint}`;
  const headers: IHeaders = {};

  const token = getToken();

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

  return response.json();
}
