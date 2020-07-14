import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SongService } from 'app/entities/song/song.service';
import { ISong, Song } from 'app/shared/model/song.model';

describe('Service Tests', () => {
  describe('Song Service', () => {
    let injector: TestBed;
    let service: SongService;
    let httpMock: HttpTestingController;
    let elemDefault: ISong;
    let expectedResult: ISong | ISong[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SongService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Song(
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
        'AAAAAAA',
        currentDate
      );
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

      it('should create a Song', () => {
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

        service.create(new Song()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Song', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            composedBy: 'BBBBBB',
            artist: 'BBBBBB',
            album: 'BBBBBB',
            band: 'BBBBBB',
            year: 'BBBBBB',
            copyright: 'BBBBBB',
            lang: 'BBBBBB',
            audioUrl: 'BBBBBB',
            videoUrl: 'BBBBBB',
            enteredBy: 'BBBBBB',
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

      it('should return a list of Song', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            description: 'BBBBBB',
            composedBy: 'BBBBBB',
            artist: 'BBBBBB',
            album: 'BBBBBB',
            band: 'BBBBBB',
            year: 'BBBBBB',
            copyright: 'BBBBBB',
            lang: 'BBBBBB',
            audioUrl: 'BBBBBB',
            videoUrl: 'BBBBBB',
            enteredBy: 'BBBBBB',
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

      it('should delete a Song', () => {
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
