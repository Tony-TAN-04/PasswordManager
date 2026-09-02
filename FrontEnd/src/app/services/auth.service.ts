import { HttpClient } from '@angular/common/http';
import { inject, Service } from '@angular/core';
import { API_URL } from '@constants';
import { Observable, tap } from 'rxjs';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
}

@Service()
export class AuthService {

    private http = inject(HttpClient)

    private ENDPOINT_LOGIN = "/auth/login"
    private readonly TOKEN_KEY = 'auth_token';



    login(credentials: LoginRequest): Observable<LoginResponse> {
        return this.http.post<LoginResponse>(
        API_URL + this.ENDPOINT_LOGIN,
        credentials
        ).pipe(
      tap(response => {
        localStorage.setItem(this.TOKEN_KEY, response.token);
      })
    )
    }

      getToken(): string | null {
        return localStorage.getItem(this.TOKEN_KEY);
      }

      logout(): void {
        localStorage.removeItem(this.TOKEN_KEY);
      }

      isAuthenticated(): boolean {
        return this.getToken() !== null;
      }

      
}
