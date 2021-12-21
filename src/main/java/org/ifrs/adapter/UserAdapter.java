package org.ifrs.adapter;

import org.ifrs.entity.User;
import org.ifrs.model.UserModel;
import org.ifrs.view.UserView;
import org.ifrs.view.UserInfoView;

public class UserAdapter {
    private User user;
    
    public UserAdapter(User user) {
        this.user = user;
    }

    public UserView mapEntityToView() {
        UserView userView = new UserView();

        userView.id = user.getId();
        userView.name = user.getName();
        userView.phone = user.getPhone();
        userView.description = user.getDescription();

        return userView;
    }

    public UserInfoView mapEntityToInfoView() {
        UserInfoView userInfoView = new UserInfoView();

        userInfoView.id = user.getId();
        userInfoView.name = user.getName();
        userInfoView.birthDay = user.getBirthDay();
        userInfoView.phone = user.getPhone();
        userInfoView.description = user.getDescription();
        userInfoView.email = user.getEmail();
        userInfoView.cpf = user.getCpf();

        return userInfoView;
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
