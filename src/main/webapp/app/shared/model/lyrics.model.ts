import { Moment } from 'moment';

export interface ILyrics {
  id?: number;
  songId?: number;
  lyrics?: any;
  lang?: string;
  author?: string;
  copyright?: string;
  isTranslated?: boolean;
  translatedBy?: string;
  charset?: string;
  lastModified?: Moment;
}

export class Lyrics implements ILyrics {
  constructor(
    public id?: number,
    public songId?: number,
    public lyrics?: any,
    public lang?: string,
    public author?: string,
    public copyright?: string,
    public isTranslated?: boolean,
    public translatedBy?: string,
    public charset?: string,
    public lastModified?: Moment
  ) {
    this.isTranslated = this.isTranslated || false;
  }
}
