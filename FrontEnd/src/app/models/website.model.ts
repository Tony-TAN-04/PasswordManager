import { User } from "@models/user.model";

export interface Website {
    id: number;
    name: string;
    url: string;
    password: string;
    user: User;
}