import { BaseEntity } from 'src/model/base-entity';

export class Lyrics implements BaseEntity {
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
    public lastModified?: any
  ) {
    this.isTranslated = false;
  }
}
