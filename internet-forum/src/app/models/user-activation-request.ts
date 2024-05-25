import { UserAccess } from "./user-access";

export class UserActivationRequest{
    userType? : number;
    activated?: boolean;
    userAccess? : UserAccess
    userId?: number;
}