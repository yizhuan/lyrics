import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { LyricsService } from 'app/entities/lyrics/lyrics.service';
import { ILyrics, Lyrics } from 'app/shared/model/lyrics.model';

describe('Service Tests', () => {
  describe('Lyrics Service', () => {
    let injector: TestBed;
    let service: LyricsService;
    let httpMock: HttpTestingController;
    let elemDefault: ILyrics;
    let expectedResult: ILyrics | ILyrics[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LyricsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Lyrics(0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', false, 'AAAAAAA', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            lastModified: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Lyrics', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            lastModified: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastModified: currentDate,
          },
          returnedFromService
        );

        service.create(new Lyrics()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Lyrics', () => {
        const returnedFromService = Object.assign(
          {
            songId: 1,
            lyrics: 'BBBBBB',
            lang: 'BBBBBB',
            author: 'BBBBBB',
            copyright: 'BBBBBB',
            isTranslated: true,
            translatedBy: 'BBBBBB',
            charset: 'BBBBBB',
            lastModified: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastModified: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Lyrics', () => {
        const returnedFromService = Object.assign(
          {
            songId: 1,
            lyrics: 'BBBBBB',
            lang: 'BBBBBB',
            author: 'BBBBBB',
            copyright: 'BBBBBB',
            isTranslated: true,
            translatedBy: 'BBBBBB',
            charset: 'BBBBBB',
            lastModified: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            lastModified: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Lyrics', () => {
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
