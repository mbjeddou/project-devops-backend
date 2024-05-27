package tn.esprit.devops.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.devops.project.entities.Stock;


public interface StockRepository extends JpaRepository<Stock, Long> {}

