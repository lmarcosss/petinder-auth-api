package org.ifrs.service;

import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.ifrs.entity.User;
import org.ifrs.model.LoginModel;
import org.ifrs.model.UserModel;

import io.quarkus.panache.common.Parameters;

public class UserService {
    public List<User> listAll() {
        return User.listAll();
    }

    public User getById(Long userId) {
        User findedUser = User.findById(userId);

        if (findedUser == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        return findedUser;
    }

    public void update(UserModel user, Long id) {
        User findedUser = this.getById(id);

        findedUser.mapFromEntity(user);

        User.persist(findedUser);
    }

    public User create(UserModel user) {
        User newUser = new User();

        newUser.mapFromEntity(user);
        User.persist(newUser);

        return newUser;
    }

    public Long login(LoginModel login) {
        try {
            User loggedUser = User.find(
                "email = :email and password = :password",
                Parameters.with("email", login.email).and("password", login.password)
            ).singleResult();

            return loggedUser.getId();
        } catch (NoResultException e) {
            throw new BadRequestException("Email e/ou senha inválido!");
        }
    }
}