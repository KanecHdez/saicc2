export interface IComisionDictaminadora {
  id: number;
  nombre?: string | null;
}

export type NewComisionDictaminadora = Omit<IComisionDictaminadora, 'id'> & { id: null };
