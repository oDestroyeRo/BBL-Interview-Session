package com.bbl.interview.api.repositories;
import com.bbl.interview.api.models.Key;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;

public interface KeyAuthRepository extends JpaRepository<Key, String>{

}
