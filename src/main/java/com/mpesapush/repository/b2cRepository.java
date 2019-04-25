/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpesapush.repository;

import com.mpesapush.model.B2C;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* */
 @Repository
public interface b2cRepository extends JpaRepository<B2C,Long> {
     
     
}