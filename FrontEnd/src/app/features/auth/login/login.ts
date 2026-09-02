import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TuiButton, TuiIcon, TuiInput, TuiLabel, TuiLoader, TuiTextfield, TuiTitle } from '@taiga-ui/core';
import {TuiForm, TuiHeader} from '@taiga-ui/layout';
import { TuiPassword } from '@taiga-ui/kit';
import { AuthService } from '@services/auth.service';
@Component({
  selector: 'app-login',
  imports: [
    ReactiveFormsModule,
    CommonModule,
    TuiTextfield,
    TuiForm,
    TuiHeader,
    TuiIcon,
    TuiPassword,
    TuiInput,
    TuiLabel,
    TuiButton,
    TuiTitle,
    TuiLoader],
  templateUrl: './login.html',
  styleUrl: './login.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Login {

  private authService = inject(AuthService)

  protected loginForm = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
  });
  protected isLoading = false;

  protected login(): void {
    if(this.loginForm.invalid) {
    return
    }

    const credentials = {
      username: this.loginForm.value.username!,
      password: this.loginForm.value.password!,
    }

    this.authService.login(credentials).subscribe({
      next: (response) => {
        console.log('Connexion réussie')
        console.log(response)

        this.isLoading = false
      },

      error: (error) => {
        if (error.status === 401) {
          console.log('Identifiants incorrects');
        } else {
          console.error('Erreur de connexion', error);
        }

        this.isLoading = false
      },
    })
  }

}
