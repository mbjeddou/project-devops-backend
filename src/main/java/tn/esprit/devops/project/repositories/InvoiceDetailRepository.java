package tn.esprit.devops.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.devops.project.entities.InvoiceDetail;

public interface InvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long> {

}
