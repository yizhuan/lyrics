import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LyricsTestModule } from '../../../test.module';
import { SocialUserUpdateComponent } from 'app/entities/social-user/social-user-update.component';
import { SocialUserService } from 'app/entities/social-user/social-user.service';
import { SocialUser } from 'app/shared/model/social-user.model';

describe('Component Tests', () => {
  describe('SocialUser Management Update Component', () => {
    let comp: SocialUserUpdateComponent;
    let fixture: ComponentFixture<SocialUserUpdateComponent>;
    let service: SocialUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LyricsTestModule],
        declarations: [SocialUserUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(SocialUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SocialUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SocialUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new SocialUser(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new SocialUser();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
