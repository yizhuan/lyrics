import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { LyricsTestModule } from '../../../test.module';
import { SocialUserDetailComponent } from 'app/entities/social-user/social-user-detail.component';
import { SocialUser } from 'app/shared/model/social-user.model';

describe('Component Tests', () => {
  describe('SocialUser Management Detail Component', () => {
    let comp: SocialUserDetailComponent;
    let fixture: ComponentFixture<SocialUserDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ socialUser: new SocialUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LyricsTestModule],
        declarations: [SocialUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(SocialUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SocialUserDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load socialUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.socialUser).toEqual(jasmine.objectContaining({ id: 123 }));
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
