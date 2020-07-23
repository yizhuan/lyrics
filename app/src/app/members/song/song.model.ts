import { BaseEntity } from 'src/model/base-entity';

export class Song implements BaseEntity {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public composedBy?: string,
    public artist?: string,
    public album?: string,
    public band?: string,
    public year?: string,
    public copyright?: string,
    public lang?: string,
    public audioUrl?: string,
    public videoUrl?: string,
    public enteredBy?: string,
    public lastModified?: any
  ) {}
}
