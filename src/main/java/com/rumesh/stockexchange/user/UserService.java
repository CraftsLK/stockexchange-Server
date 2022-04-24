package com.rumesh.stockexchange.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo repository;

    public ResponseEntity<User> create(User item) {

        User savedItem = repository.save(item);
        return new ResponseEntity<>(savedItem,HttpStatus.CREATED);

    }
        public ResponseEntity<List<User>> getAll () {
            List<User> items = new ArrayList<>();
            items.addAll(repository.findAll());
            return new ResponseEntity<>(HttpStatus.OK);
        }


        public ResponseEntity<HttpStatus> deleteById ( int id){

            repository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

    public ResponseEntity<User> update(Integer id, User item) {
        return null;
    }

    public ResponseEntity<User> getById(Integer id) {return null;
    }

    public ResponseEntity<User> login(User item) {
        Optional<User> u = repository.findByNameAndPassword(item.getName(), item.getPassword());
        if (u.isPresent()) {
            return new ResponseEntity<>(u.get(),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null,HttpStatus.OK);
        }
    }
}

