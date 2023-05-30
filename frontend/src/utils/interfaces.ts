// User interface
interface IAuthoritiesObj {
  authority: string
};

interface IRoleObj {
  roleId?: number,
  name?: string
};

export interface IUserState {
  accountNonExpired?: boolean,
  accountNonLocked?: boolean,
  authorities?: Array<IAuthoritiesObj>,
  credentialsNonExpired?: boolean,
  email: string,
  enabled?: boolean,
  firstName: string,
  lastName: string,
  role: IRoleObj,
  roleId?: number,
  userId: number | null,
  username?: string
};

export interface ICredentials {
  email: string,
  password: string
};