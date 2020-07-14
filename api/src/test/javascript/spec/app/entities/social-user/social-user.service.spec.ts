import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { SocialUserService } from 'app/entities/social-user/social-user.service';
import { ISocialUser, SocialUser } from 'app/shared/model/social-user.model';

describe('Service Tests', () => {
  describe('SocialUser Service', () => {
    let injector: TestBed;
    let service: SocialUserService;
    let httpMock: HttpTestingController;
    let elemDefault: ISocialUser;
    let expectedResult: ISocialUser | ISocialUser[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SocialUserService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new SocialUser(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SocialUser', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new SocialUser()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SocialUser', () => {
        const returnedFromService = Object.assign(
          {
            provider: 'BBBBBB',
            socialUserId: 'BBBBBB',
            email: 'BBBBBB',
            name: 'BBBBBB',
            photoUrl: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            authToken: 'BBBBBB',
            idToken: 'BBBBBB',
            authorizationCode: 'BBBBBB',
            facebook: 'BBBBBB',
            linkedIn: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SocialUser', () => {
        const returnedFromService = Object.assign(
          {
            provider: 'BBBBBB',
            socialUserId: 'BBBBBB',
            email: 'BBBBBB',
            name: 'BBBBBB',
            photoUrl: 'BBBBBB',
            firstName: 'BBBBBB',
            lastName: 'BBBBBB',
            authToken: 'BBBBBB',
            idToken: 'BBBBBB',
            authorizationCode: 'BBBBBB',
            facebook: 'BBBBBB',
            linkedIn: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SocialUser', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
