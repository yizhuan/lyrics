import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { LyricsTranslatorPage } from './lyrics-translator.page';

describe('LyricsTranslatorPage', () => {
  let component: LyricsTranslatorPage;
  let fixture: ComponentFixture<LyricsTranslatorPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LyricsTranslatorPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(LyricsTranslatorPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
