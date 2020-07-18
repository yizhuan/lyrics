export class User {
    constructor(
        public login: string,
        public activated: boolean,
        public authorities: string[],
        public createdBy: string,
        public createdDate: Date,
        public email: string,
        public firstName: string,
        public lastName: string,
        public imageUrl: string,
        public langKey: string,
        public lastModifiedBy: string,
        public lastModifiedDate: Date                
      ) {}
}

/*
{
    "activated": true,
    "authorities": [
      "ROLE_USER"
    ],
    "createdBy": "admin",
    "createdDate": "2020-06-07T14:38:27.982Z",
    "email": "ddd@ddd",
    "firstName": "Yizhuan",
    "imageUrl": "http://www.google.com/images",
    "langKey": "en",
    "lastModifiedBy": "admin",
    "lastModifiedDate": "2020-06-07T14:38:27.982Z",
    "lastName": "Yu",
    "login": "df754juitrfgh"
  }
  */