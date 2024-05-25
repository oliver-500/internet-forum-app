import { FormBuilder, FormGroup } from "@angular/forms";
import { UserWithRegistrationInfo } from "./user-with-registration-info";
import { UserAccess } from "./user-access";


export class UserItem{
    user? : UserWithRegistrationInfo;
    isExpanded?: boolean;
    formGroup: FormGroup;
    userAccess?: UserAccess;
   
    

    

    constructor(private formBuilder: FormBuilder){
        this.formGroup = this.formBuilder.group({});
    }
}