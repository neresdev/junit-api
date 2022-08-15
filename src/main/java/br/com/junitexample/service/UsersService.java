package br.com.junitexample.service;

import br.com.junitexample.domain.DTO.UsersDTO;
import br.com.junitexample.domain.Users;

import java.util.List;

public interface UsersService {

    Users findById(Integer id);
    List<Users> findAll();
    Users create(UsersDTO obj);
    Users update(UsersDTO obj);
    void delete(Integer id);
}
