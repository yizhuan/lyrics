import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SupportedlanguagesService {

  private _languages: any[] = [
    {
      id: 1,
      code: "en",
      language: "English",
    },
    {
      id: 2,
      code: "ja",
      language: "日本語",
    },
    {
      id: 3,
      code: "ko",
      language: "한국어",
    },
    {
      id: 1,
      code: "zh_CN",
      language: "中文",
    },
  ];

  constructor() { }

  get languages() {
    return this._languages;
  }
}
