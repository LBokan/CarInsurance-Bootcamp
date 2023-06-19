import jwtDecode from 'jwt-decode';

interface ITokenUserData {
  sub: string,
  iat: number,
  exp: number
}

export const clearAllCookies = () => {
  const cookies = document.cookie.split(";") || [];

  if (cookies.length) {
    cookies.forEach(cookie => {
      const equalPos = cookie.indexOf("=");
      const name = equalPos > -1 ? cookie.slice(0, equalPos) : cookie;

      document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT;path=/";
    })
  }
}

export const getToken = () => {
  const cookies = document.cookie;
  const cookiesArr = cookies.split(";");
  const token = cookiesArr.find(cookie => cookie.includes("token"));

  if (token) {
    return token.replace("token=", "");
  }

  return null;
}

export const isTokenValid = () => {
  const token = getToken();

  if (!token) return false;

  const tokenUserData: ITokenUserData = jwtDecode(token);

  return tokenUserData.exp > parseInt(new Date().getTime().toString().slice(0, 10));
};

export const logout = () => {
  clearAllCookies();

  return window.location.replace('/login');
};
