package tn.esprit.devops.project.repositories;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.devops.project.entities.Operator;

public interface OperatorRepository extends CrudRepository<Operator, Long> {

}
