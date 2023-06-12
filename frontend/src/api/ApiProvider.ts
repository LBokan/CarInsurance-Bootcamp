import { getToken } from '@/helpers/authorization';
import { 
  BASE_API_URL
} from '@/helpers/authConstants';

interface IHeaders {
  [n: string]: string
};

export const createRequest = async (
    endpoint: string, 
    method: string, 
    data: object | null = null
  ) => {
  const url = `${BASE_API_URL}/${endpoint}`;
  const headers: IHeaders = {
    'Content-Type': 'application/json'
  };
  
  const token = getToken();

  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }

  const options = {
    method,
    headers,
    body: data ? JSON.stringify(data) : null,
    credentials: 'include' as RequestCredentials
  };

  const response = await fetch(url, options);

  if (!response.ok) {
    const errorData = await response.text();

    throw new Error(errorData);
  }

  return response.json();
}
