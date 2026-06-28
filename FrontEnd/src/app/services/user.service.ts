import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http'
@Service()
export class UserService {
    private readonly http = inject(HttpClient);

    readonly API_URL = "http://localhost:8080"
    readonly ENDPOINT_USERS = "/users"


    getUsers() {
        return this.http.get(this.API_URL+this.ENDPOINT_USERS)
    }
}
