import { 
  BASE_API_URL,
  LOCAL_STORAGE_API_KEY
} from '@/helpers/constants';

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
  
  const token = localStorage.getItem(LOCAL_STORAGE_API_KEY);

  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }

  const options = {
    method,
    headers,
    body: data ? JSON.stringify(data) : null
  };

  const response = await fetch(url, options);

  if (!response.ok) {
    const errorData = await response.text();

    throw new Error(errorData);
  }

  return response.json();
}
