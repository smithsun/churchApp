import { IPublication } from '@/shared/model/publication.model';

export interface IVideos {
  id?: number;
  title?: string | null;
  type?: string | null;
  content?: string | null;
  notes?: string | null;
  keyWords?: string | null;
  lastUpdateBy?: string | null;
  status?: string | null;
  eventDate?: Date | null;
  publication?: IPublication | null;
}

export class Videos implements IVideos {
  constructor(
    public id?: number,
    public title?: string | null,
    public type?: string | null,
    public content?: string | null,
    public notes?: string | null,
    public keyWords?: string | null,
    public lastUpdateBy?: string | null,
    public status?: string | null,
    public eventDate?: Date | null,
    public publication?: IPublication | null
  ) {}
}
