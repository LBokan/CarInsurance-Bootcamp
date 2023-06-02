import { 
  BASE_API_URL
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
  
  const cookies = document.cookie;
  const cookiesArr = cookies.split(";");
  const token = cookiesArr.find(cookie => cookie.includes("token"));

  if (token) {
    headers['Authorization'] = `Bearer ${token.replace("token=", "")}`;
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
