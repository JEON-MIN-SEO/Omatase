package Omatase.omatase.DTO;

import Omatase.omatase.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String username;

    private String password;

    private Role role;
}
