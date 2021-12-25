package org.ifrs.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class EditUserModel {
    @NotNull(message = "Campo nome é obrigatório")
    public String name;

    @NotNull(message = "Campo data de nascimento é obrigatório")
    public String birthDay;

    @NotNull(message = "Campo cpf é obrigatório")
    public String cpf;

    @NotNull(message = "Campo telefone é obrigatório")
    public String phone;

    @NotNull(message = "Campo descrição é obrigatório")
    public String description;

    @NotNull(message = "Campo email é obrigatório")
    @Email
    public String email;
}
