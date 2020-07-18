import {ZonedDateTime} from '@js-joda/core'

export class Song {
    id: number;
    name: string;
    description: string;
    composedBy: string;
    artist: string;
    album: string;
    band: string;
    year: string;
    copyright: string;
    lang: string;
    audioUrl: string;
    videoUrl: string;
    enteredBy: string;
    lastModified: ZonedDateTime;
}
