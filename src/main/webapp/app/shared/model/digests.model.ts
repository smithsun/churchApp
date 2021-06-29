import { IPublication } from '@/shared/model/publication.model';

import { DigestType } from '@/shared/model/enumerations/digest-type.model';
export interface IDigests {
  id?: number;
  title?: string;
  type?: DigestType;
  contentContentType?: string;
  content?: string;
  lastUpdateBy?: string | null;
  status?: string | null;
  eventDate?: Date | null;
  publication?: IPublication | null;
}

export class Digests implements IDigests {
  constructor(
    public id?: number,
    public title?: string,
    public type?: DigestType,
    public contentContentType?: string,
    public content?: string,
    public lastUpdateBy?: string | null,
    public status?: string | null,
    public eventDate?: Date | null,
    public publication?: IPublication | null
  ) {}
}
