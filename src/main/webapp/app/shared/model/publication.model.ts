import { IDigests } from '@/shared/model/digests.model';
import { IPublicNotice } from '@/shared/model/public-notice.model';
import { IVideos } from '@/shared/model/videos.model';

export interface IPublication {
  id?: number;
  type?: string;
  startDate?: Date | null;
  endDate?: Date | null;
  priority?: number | null;
  createdBy?: string | null;
  lastUpdateBy?: string | null;
  orgSignature?: string | null;
  digests?: IDigests | null;
  publicNotice?: IPublicNotice | null;
  videos?: IVideos | null;
}

export class Publication implements IPublication {
  constructor(
    public id?: number,
    public type?: string,
    public startDate?: Date | null,
    public endDate?: Date | null,
    public priority?: number | null,
    public createdBy?: string | null,
    public lastUpdateBy?: string | null,
    public orgSignature?: string | null,
    public digests?: IDigests | null,
    public publicNotice?: IPublicNotice | null,
    public videos?: IVideos | null
  ) {}
}
