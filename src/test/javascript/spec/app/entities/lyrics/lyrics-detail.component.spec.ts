import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { LyricsTestModule } from '../../../test.module';
import { LyricsDetailComponent } from 'app/entities/lyrics/lyrics-detail.component';
import { Lyrics } from 'app/shared/model/lyrics.model';

describe('Component Tests', () => {
  describe('Lyrics Management Detail Component', () => {
    let comp: LyricsDetailComponent;
    let fixture: ComponentFixture<LyricsDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ lyrics: new Lyrics(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LyricsTestModule],
        declarations: [LyricsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(LyricsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LyricsDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load lyrics on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.lyrics).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
