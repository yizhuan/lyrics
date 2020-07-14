export interface ISocialUser {
  id?: number;
  provider?: string;
  socialUserId?: string;
  email?: string;
  name?: string;
  photoUrl?: string;
  firstName?: string;
  lastName?: string;
  authToken?: string;
  idToken?: string;
  authorizationCode?: string;
  facebook?: any;
  linkedIn?: any;
}

export class SocialUser implements ISocialUser {
  constructor(
    public id?: number,
    public provider?: string,
    public socialUserId?: string,
    public email?: string,
    public name?: string,
    public photoUrl?: string,
    public firstName?: string,
    public lastName?: string,
    public authToken?: string,
    public idToken?: string,
    public authorizationCode?: string,
    public facebook?: any,
    public linkedIn?: any
  ) {}
}
