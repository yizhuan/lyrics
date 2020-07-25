import { ZonedDateTime } from '@js-joda/core'

export class Lyrics {
    id: number;
    songId: number;
    lyrics: string;
    lang: string;
    author: string;
    copyright: string;
    isTranslated: boolean;
    translatedBy: string;
    charset: string;
    lastModified: ZonedDateTime;
}
