package org.ifrs.adapter;

import org.ifrs.entity.User;
import org.ifrs.model.UserModel;
import org.ifrs.view.UserView;

public class UserAdapter {
    private User user;
    
    public UserAdapter(User user) {
        this.user = user;
    }

    public UserView mapEntityToView() {
        UserView userView = new UserView();

        userView.id = user.getId();
        userView.name = user.getName();
        userView.birthDay = user.getBirthDay();
        userView.phone = user.getPhone();
        userView.description = user.getDescription();

        return userView;
    }

    public void mapModelToEntity(UserModel userModel) {
        user.setBirthDay(userModel.birthDay);
        user.setCpf(userModel.cpf);
        user.setDescription(userModel.description);
        user.setEmail(userModel.email);
        user.setName(userModel.name);
        user.setPassword(userModel.password);
        user.setPhone(userModel.phone);
    }

    public User getUser() {
        return this.user;
    }
}
