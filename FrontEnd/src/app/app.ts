import { TuiRoot } from '@taiga-ui/core';
import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { UserService } from './services/user.service';
import { OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { User } from '@models/user.model';
import { Login } from './features/auth/login/login';
@Component({
  selector: 'app-root',
  imports: [RouterOutlet, CommonModule, TuiRoot, Login],
  templateUrl: './app.html',
  styleUrl: './app.css',
  providers: [UserService],
})
export class App implements OnInit {
  protected readonly title = signal('PasswordManager')
  protected users: User[] = []

  constructor(private userService: UserService) {}
  ngOnInit(): void {
    console.log('c bon')
    this.userService.getUsers().subscribe((datas) => {
      this.users = datas as User[]
    })
  }
}
