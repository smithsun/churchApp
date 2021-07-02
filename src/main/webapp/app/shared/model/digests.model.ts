import { IPublication } from '@/shared/model/publication.model';
import { IDailyVerses } from '@/shared/model/daily-verses.model';

import { DigestType } from '@/shared/model/enumerations/digest-type.model';
export interface IDigests {
  id?: number;
  type?: DigestType;
  topic?: string;
  title?: string;
  img?: string | null;
  imgVerse?: string | null;
  prayReadVerse?: string | null;
  content?: string;
  lastUpdateBy?: string | null;
  status?: string | null;
  eventDate?: Date | null;
  publication?: IPublication | null;
  dailyVerses?: IDailyVerses[] | null;
  extend?: boolean | null;
}

export class Digests implements IDigests {
  constructor(
    public id?: number,
    public type?: DigestType,
    public topic?: string,
    public title?: string,
    public img?: string | null,
    public imgVerse?: string | null,
    public prayReadVerse?: string | null,
    public content?: string,
    public lastUpdateBy?: string | null,
    public status?: string | null,
    public eventDate?: Date | null,
    public publication?: IPublication | null,
    public dailyVerses?: IDailyVerses[] | null
  ) {}
}
