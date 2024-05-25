import { PermissionType } from "./permission-type";
import { Room } from "./room";
import { UserPermission } from "./user-permission";

export class UserAccess{
    userId?: number;
    accesses : UserPermission[] = []
    accessesRoom : Room[] = [];
}