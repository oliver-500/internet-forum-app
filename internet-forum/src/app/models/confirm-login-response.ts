export interface ConfirmLoginResponse {
    token : string;
    verified: boolean;
    id : number;
    userGroup: number;
}