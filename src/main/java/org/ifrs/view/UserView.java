package org.ifrs.view;

import org.ifrs.entity.User;

public class UserView extends BaseView<User> {
    public Long id;
    public String name;
    public String birthDay;
    public String cpf;
    public String phone;
    public String description;
    public String email;

    @Override
    public UserView mapFromEntity(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.birthDay = user.getBirthDay();
        this.cpf = user.getCpf();
        this.phone = user.getPhone();
        this.description = user.getDescription();
        this.email = user.getEmail();

        return this;
    }
}