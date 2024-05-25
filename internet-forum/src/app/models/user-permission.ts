import { PermissionType } from "./permission-type";
import { Room } from "./room";

export class UserPermission{
    room? : Room;
    permission?: PermissionType;

    constructor(room : Room , permission : PermissionType){
        this.room = room;
        this.permission = permission;
    }
}