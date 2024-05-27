package tn.esprit.devops.project.services.Iservices;

import tn.esprit.devops.project.entities.Supplier;

import java.util.List;

public interface ISupplierService {

	List<Supplier> retrieveAllSuppliers();

	Supplier addSupplier(Supplier supplier);

	void deleteSupplier(Long id);

	Supplier updateSupplier(Supplier supplier);

	Supplier retrieveSupplier(Long id);

}
