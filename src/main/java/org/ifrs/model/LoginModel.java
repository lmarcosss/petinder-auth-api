package org.ifrs.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class LoginModel {
    @NotNull(message = "Campo email é obrigatório")
    @Email
    public String email;
    
    @NotNull(message = "Campo senha é obrigatório")
    public String password;
}
