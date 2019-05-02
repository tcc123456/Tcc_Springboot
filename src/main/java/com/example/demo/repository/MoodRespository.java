package com.example.demo.repository;

import com.example.demo.model.Mood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodRespository extends JpaRepository <Mood,String>{

}
