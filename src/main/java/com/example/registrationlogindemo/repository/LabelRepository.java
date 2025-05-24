package com.example.registrationlogindemo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.registrationlogindemo.entity.Label;

public interface LabelRepository extends JpaRepository<Label, Long> {

	

}
