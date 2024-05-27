package tn.esprit.devops.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.devops.project.entities.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

}
