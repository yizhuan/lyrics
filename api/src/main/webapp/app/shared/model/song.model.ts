import { Moment } from 'moment';

export interface ISong {
  id?: number;
  name?: string;
  description?: string;
  composedBy?: string;
  artist?: string;
  album?: string;
  band?: string;
  year?: string;
  copyright?: string;
  lang?: string;
  audioUrl?: string;
  videoUrl?: string;
  enteredBy?: string;
  lastModified?: Moment;
}

export class Song implements ISong {
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
    public lastModified?: Moment
  ) {}
}
