import { Component, OnInit } from '@angular/core';
import { ServiceService } from '../service.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  constructor(private service:ServiceService) {
   }
   message;
   composeMail(form){
     console.log(form.value);
    return this.service.composeEmail(form.value).subscribe(data=>{
      this.message=data.message;
    })
   
  }
  draft(form){
    console.log(form.value);
    let user=JSON.parse(localStorage.getItem("user"));
    return this.service.adddraftMail(form.value,user.email);
  }
  check(){
    let user=localStorage.getItem("user");
    console.log(JSON.parse(user));
    if(JSON.parse(user)===null){
    console.log("hai");
    return true;
    }
    else{
      return false;
    }
  }
  

  draftmail(form){
    console.log(form.value);
    return this.service.adddraftMail(form.value).subscribe(data=>{
      this.message=data
    })
  }
   logout(){
     localStorage.clear();
   }


  ngOnInit() {
  }
  
}
