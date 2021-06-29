export interface IFriends {
  id?: number;
  name?: string;
  lastName?: string;
  cellPhone?: string | null;
  lastUsedTime?: Date | null;
  intimacyOrder?: number | null;
}

export class Friends implements IFriends {
  constructor(
    public id?: number,
    public name?: string,
    public lastName?: string,
    public cellPhone?: string | null,
    public lastUsedTime?: Date | null,
    public intimacyOrder?: number | null
  ) {}
}
