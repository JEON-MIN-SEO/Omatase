package Omatase.omatase.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {

    private String username;

    private String password;

    private LocalDate joinDate;
}
