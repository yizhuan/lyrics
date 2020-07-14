import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { LyricsTestModule } from '../../../test.module';
import { LyricsUpdateComponent } from 'app/entities/lyrics/lyrics-update.component';
import { LyricsService } from 'app/entities/lyrics/lyrics.service';
import { Lyrics } from 'app/shared/model/lyrics.model';

describe('Component Tests', () => {
  describe('Lyrics Management Update Component', () => {
    let comp: LyricsUpdateComponent;
    let fixture: ComponentFixture<LyricsUpdateComponent>;
    let service: LyricsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [LyricsTestModule],
        declarations: [LyricsUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(LyricsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LyricsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LyricsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Lyrics(123);
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
        const entity = new Lyrics();
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
