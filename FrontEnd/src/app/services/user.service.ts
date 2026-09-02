import { Service, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { API_URL } from '@constants';
@Service()
export class UserService {
    private readonly http = inject(HttpClient);

    readonly ENDPOINT_USERS = "/users"


    getUsers() {
        return this.http.get(API_URL+this.ENDPOINT_USERS)
    }
}
