import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ThemePalette } from '@angular/material/core';
import { UserService } from 'src/app/admin-panel/services/user.service';
import { PermissionType } from 'src/app/models/permission-type';
import { Room } from 'src/app/models/room';
import { User } from 'src/app/models/user';
import { UserWithRegistrationInfo } from 'src/app/models/user-with-registration-info';
import { UserItem } from 'src/app/models/user.item';
import { PermissionService } from 'src/app/services/permission.service';
import { RoomServiceService } from 'src/app/user-module/services/room-service.service';
import { UseraccessesService } from '../services/useraccesses.service';
import { UserAccess } from 'src/app/models/user-access';
import { UserActivationRequest } from 'src/app/models/user-activation-request';
import { UserPermission } from 'src/app/models/user-permission';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {


  items: UserItem[] = [];

  rooms: Room[] = [];

  permissionTypes: PermissionType[] = [];

  displayedColumns : String[] = [];


  constructor(private userService: UserService,
    private formBuilder: FormBuilder,
    private roomService: RoomServiceService,
    private permissionService: PermissionService,
    private userAccessesService : UseraccessesService,
    private _snackBar: MatSnackBar
  ) {

  }

  color: ThemePalette = 'primary';


 

  isChecked(id : number, index: number){
      let item = this.items[index];

      for(let access of item.userAccess?.accesses!){
        if(access.permission?.id === id) return true;
      }
      return false;
  }

  ngOnInit(): void {

    this.roomService.getRooms().subscribe({
      next: (response: any) => {
        this.rooms = response;
      },
      error: (error: Error) => {

      }
    })

    this.permissionService.getAll().subscribe({
      next: (response : any) => {
        this.permissionTypes = response;
       
       
        this.displayedColumns.push("Room")
        this.displayedColumns.push(...this.permissionTypes.map(p => p.name));
      },
      error: (error: Error) => {

      }
    })

   


    this.userService.getAllUsersWithRegistrationInfo().subscribe({
      next: (response: UserWithRegistrationInfo[] | any) => {


        this.items = response.map((element: UserWithRegistrationInfo) => {

          return {
            user: element,
            isExpanded: false,
            formGroup: this.formBuilder.group({ isRegistered: [element.registered, Validators.required]
             
            })
          };
        });

        this.items.sort((i1, i2)=>{
          if(i1.user?.registered && !i2.user?.registered) return 1;
          else if(!i1.user?.registered && i2.user?.registered) return -1;
          return 1;
        });





      },
      error: (error: Error) => {

      }

    })
  }

  checked : boolean = false;

  doesAccessPermissionExists(index : number, permissionName : string, roomId : number) : boolean{
    let accesses = this.items[index].userAccess?.accesses;
    if(accesses){
      for(let access of accesses){
        if(access.permission?.name === permissionName && access.room?.id == roomId) {
          
          console.log("Pronasao: " + permissionName + roomId);
          return true;
        }
      }
    }
    return false;
   
  }

  expandItem(index: number, isToggle : boolean ): void {
    if (!this.rooms) return;

    if(!this.permissionTypes){
      return;
    }
    
    let formGroup = this.items[index].formGroup;


    if(this.items[index].isExpanded){
      this.items[index].isExpanded = !this.items[index].isExpanded;

      for(let room of this.rooms){
        for(let permissionType of this.permissionTypes){
          console.log("remvoing" + permissionType.name + room.id);
          formGroup.removeControl(permissionType.name + room.id);
         // formGroup.addControl(permissionType.name + room.id, this.formBuilder.control(this.doesAccessPermissionExists(index, permissionType.name, room.id!), Validators.required));
        }
  
      }
      console.log("exit");
     if(!isToggle)formGroup.get('isRegistered')?.setValue( !formGroup.get('isRegistered')?.value );
      return;
    }
   
    this.userAccessesService.getOneByUser(this.items[index].user?.id!).subscribe({
      next: (response : any | UserAccess) =>{
       
        this.items[index].userAccess = response;
        if(this.items[index].userAccess) this.items[index].userAccess!.accessesRoom = this.rooms;
        for(let room of this.rooms){
          for(let permissionType of this.permissionTypes){
            console.log("adding" + permissionType.name + room.id);
            formGroup.addControl(permissionType.name + room.id, this.formBuilder.control(this.doesAccessPermissionExists(index, permissionType.name, room.id!), Validators.required));
          }
        }
      },
      error: (error: Error) =>{

      }
    })

    

    

   

  
    

    // this.rooms.forEach(room => {
    //   formGroup!.addControl(room.name!, this.formBuilder.control('', Validators.required))
    // })
    
    formGroup.addControl("userType", this.formBuilder.control({ value:  this.items[index].user?.userGroup?.toString(),  disabled: this.items[index].isExpanded! && this.items[index].user?.registered! }, Validators.required))

    this.items[index].isExpanded = !this.items[index].isExpanded;
    
   

  }


  onSubmit(index: number): void {
    if (this.items[index].formGroup.valid) {
      // console.log('Form submitted:', this.formGroup.value);
    } else {
      console.error('Form is invalid.');
    }
  }

  save(index : number){
    let item = this.items[index].formGroup;
    let userAccess : UserAccess = new UserAccess();
    for(let room of this.rooms){
      for(let permissionType of this.permissionTypes){
        console.log("adding" + permissionType.name + room.id);
        if(item.get(permissionType.name + room.id) ){
          if(item.get(permissionType.name + room.id)!.value === true){
            let permission : UserPermission = {};
           
            permission.permission = new PermissionType();
            permission.permission.name = permissionType.name;
            permission.room = new Room();
            permission.room.id = room.id;
            userAccess.userId = this.items[index].user?.id;
            userAccess.accesses.push(permission);
          }
        }
        
       
      }
    }

    let req : UserActivationRequest = {
      userType : item.value.userType,
      activated : item.value.isRegistered,
      userAccess: userAccess,
      userId:  this.items[index].user?.id
    }

    this.userService.acivateUser(req).subscribe({
      next: (response : any) =>{
        this.items[index].user!.registered = item.value.isRegistered;
        this.expandItem(index, false);
        item.get("isRegistered")?.setValue(this.items[index].user!.registered);

        this._snackBar.open("Succesfully added", "OK", {
          duration: 3000,
          panelClass: ['custom-snackbar']
        });
      },
      error: (error : Error) =>{
        this._snackBar.open("Something went wrong", "OK", {
          duration: 3000,
          panelClass: ['custom-snackbar']
        });
      }
    })
   
  
  
    console.log(req);
  }



  changePermission(index: number, permissionType : PermissionType, id : number){
  
    let item = this.items[index].formGroup.get(permissionType.name + id);
    if(item){
      item.setValue(!item.value)
    }
    }
     


}
