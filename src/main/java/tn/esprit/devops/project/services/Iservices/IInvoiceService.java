package tn.esprit.devops.project.services.Iservices;

import tn.esprit.devops.project.entities.Invoice;

import java.util.Date;
import java.util.List;

public interface IInvoiceService {
	List<Invoice> retrieveAllInvoices();

	List<Invoice> getInvoicesBySupplier(Long idSupplier);

	void cancelInvoice(Long id);

	Invoice retrieveInvoice(Long id);
	
	void assignOperatorToInvoice(Long idOperator, Long idInvoice);

	float getTotalAmountInvoiceBetweenDates(Date startDate, Date endDate);
}
