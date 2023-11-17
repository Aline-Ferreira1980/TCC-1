export interface EstagiarioEntity {
  id: number;
  nome: string;
  sobrenome: string;
  email: string;
  ra: string;
  turno: string;
  turma: string;
  semestre: number;
  pacientes: paciente[];
  servicos: servico[];
  professorResponsavel: docente
  horariosTrabalho: horarioTrabalho[]
}

export interface paciente{
    id: number;
    nome: string;
    sobrenome: string;
    nomeSocial: string;
    email: string;
    genero: string
}

interface servico {
  id: number;
  acronimo: string;
  nome: string;
  descricao: string
}

interface docente {
  id: number;
  nome: string;
  sobrenome: string;
  email: string;
}
interface horarioTrabalho {
    id: number;
    diaSemana: string;
    horarioInicio: string;
    horarioFim: string;
  }
