package br.com.junitexample.service.impl;

import br.com.junitexample.domain.DTO.UsersDTO;
import br.com.junitexample.domain.Users;
import br.com.junitexample.repositories.UsersRepository;
import br.com.junitexample.service.UsersService;
import br.com.junitexample.service.exceptions.DataIntegratyViolationException;
import br.com.junitexample.service.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UsersService {

    public static final String NOT_FOUND_MESSAGE = "Objeto nao encontrado";

    @Autowired
    private UsersRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Users findById(Integer id) {
        Optional<Users> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(NOT_FOUND_MESSAGE));
    }

    public List<Users> findAll(){
        return repository.findAll();
    }

    @Override
    public Users create(UsersDTO obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj, Users.class));
    }

    @Override
    public Users update(UsersDTO obj) {
        findByEmail(obj);
        return repository.save(mapper.map(obj, Users.class));
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    private void findByEmail(UsersDTO obj){
        Optional<Users> user = repository.findByEmail(obj.getEmail());
        if(user.isPresent() && !user.get().getId().equals(obj.getId())){
            throw new DataIntegratyViolationException("E-mail ja cadastrado no sistema");
        }
    }
}
