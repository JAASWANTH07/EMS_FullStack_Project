import User from "./user.model";

export interface UserResponse {
    user: User;
    token: string;
}

export default UserResponse;
