export interface JwtPayloadEntity {
  authorities?: string[];
  client_id?: string;
  exp?: number;
  jti?: string;
  scope?: string[];
  user_id: number;
  user_name?: string;
  user_nome?: string;
}
