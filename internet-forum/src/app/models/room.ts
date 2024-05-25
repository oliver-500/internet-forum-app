import { RoomComment } from "./room-comment";

export class Room{
    name?: string;
    id?: number;
    comments?: RoomComment[]
    newCommentsCount?: number;
    
}