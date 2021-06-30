import { IDigests } from '@/shared/model/digests.model';

export interface IDailyVerses {
  id?: number;
  verses?: string | null;
  order?: number | null;
  digests?: IDigests | null;
}

export class DailyVerses implements IDailyVerses {
  constructor(public id?: number, public verses?: string | null, public order?: number | null, public digests?: IDigests | null) {}
}
