package org.ifrs.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.ifrs.adapter.UserAdapter;
import org.ifrs.entity.User;
import org.ifrs.model.LoginModel;
import org.ifrs.model.UserModel;

import io.quarkus.panache.common.Parameters;

public class UserService {
    private void validateUserEmailCpf(String email, String cpf) {
        boolean isValidEmail = User.count("email = ?1", email) == 0;

        if (!isValidEmail) throw new BadRequestException("Email já cadastrado");

        boolean isValidCpf = User.count("cpf = ?1", cpf) == 0;

        if (!isValidCpf) throw new BadRequestException("Cpf já cadastrado");
    }

    public List<User> listAll() {
        return User.listAll();
    }

    public User getById(Long userId) {
        User findedUser = User.findById(userId);

        return findedUser;
    }

    public void update(UserModel user, Long id) {
        User findedUser = this.getById(id);

        UserAdapter adapter = new UserAdapter(findedUser);
        adapter.mapModelToEntity(user);
        
        User.persist(adapter.getUser());
    }

    public User create(UserModel user) {
        User newUser = new User();

        validateUserEmailCpf(user.email, user.cpf);

        UserAdapter adapter = new UserAdapter(newUser);
        adapter.mapModelToEntity(user);

        User.persist(adapter.getUser());

        return adapter.getUser();
    }

    public Long login(LoginModel login) {
        try {
            User loggedUser = User.find(
                "email = :email and password = :password",
                Parameters.with("email", login.email).and("password", login.password)
            ).singleResult();

            return loggedUser.getId();
        } catch (NoResultException e) {
            throw new BadRequestException("Email e/ou senha inválido");
        }
    }
}