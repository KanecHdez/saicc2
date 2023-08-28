import { IComisionDictaminadora } from 'app/entities/comision-dictaminadora/comision-dictaminadora.model';

export interface ICentroDocente {
  id: number;
  cve?: number | null;
  nombre?: string | null;
  comisionDictaminadora?: Pick<IComisionDictaminadora, 'id' | 'nombre'> | null;
}

export type NewCentroDocente = Omit<ICentroDocente, 'id'> & { id: null };
