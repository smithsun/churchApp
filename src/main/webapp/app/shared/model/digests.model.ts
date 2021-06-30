import { IPublication } from '@/shared/model/publication.model';
import { IDailyVerses } from '@/shared/model/daily-verses.model';

import { DigestType } from '@/shared/model/enumerations/digest-type.model';
export interface IDigests {
  id?: number;
  type?: DigestType;
  title?: string;
  imgVerse?: string | null;
  prayReadVerse?: string | null;
  content?: string;
  lastUpdateBy?: string | null;
  status?: string | null;
  eventDate?: Date | null;
  publication?: IPublication | null;
  dailyVerses?: IDailyVerses[] | null;
}

export class Digests implements IDigests {
  constructor(
    public id?: number,
    public type?: DigestType,
    public title?: string,
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
