import { IPublication } from '@/shared/model/publication.model';

import { NoticeType } from '@/shared/model/enumerations/notice-type.model';
export interface IPublicNotice {
  id?: number;
  title?: string;
  type?: NoticeType;
  content?: string;
  lastUpdateBy?: string | null;
  status?: string | null;
  eventDate?: Date | null;
  publication?: IPublication | null;
}

export class PublicNotice implements IPublicNotice {
  constructor(
    public id?: number,
    public title?: string,
    public type?: NoticeType,
    public content?: string,
    public lastUpdateBy?: string | null,
    public status?: string | null,
    public eventDate?: Date | null,
    public publication?: IPublication | null
  ) {}
}
