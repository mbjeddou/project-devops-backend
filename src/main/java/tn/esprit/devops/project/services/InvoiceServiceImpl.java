package tn.esprit.devops.project.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.devops.project.entities.Invoice;
import tn.esprit.devops.project.repositories.InvoiceDetailRepository;
import tn.esprit.devops.project.repositories.InvoiceRepository;
import tn.esprit.devops.project.repositories.OperatorRepository;
import tn.esprit.devops.project.repositories.SupplierRepository;
import tn.esprit.devops.project.services.Iservices.IInvoiceService;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class InvoiceServiceImpl implements IInvoiceService {

	public static final String INVOICE_NOT_FOUND = "Invoice not found";
	public static final String SUPPLIER_NOT_FOUND = "Supplier not found";
	public static final String OPERATOR_NOT_FOUND = "Operator not found";
	final InvoiceRepository invoiceRepository;
	final OperatorRepository operatorRepository;
	final InvoiceDetailRepository invoiceDetailRepository;
	final SupplierRepository supplierRepository;
	
	@Override
	public List<Invoice> retrieveAllInvoices() {
		return invoiceRepository.findAll();
	}
	@Override
	public void cancelInvoice(Long invoiceId) {
		// method 01
		var invoice = invoiceRepository.findById(invoiceId).orElseThrow(() -> new NullPointerException(INVOICE_NOT_FOUND));
		invoice.setArchived(true);
		invoiceRepository.save(invoice);
		//method 02 (Avec JPQL)
		invoiceRepository.updateInvoice(invoiceId);
	}

	@Override
	public Invoice retrieveInvoice(Long invoiceId) {

		return invoiceRepository.findById(invoiceId).orElseThrow(() -> new NullPointerException(INVOICE_NOT_FOUND));
	}

	@Override
	public List<Invoice> getInvoicesBySupplier(Long idSupplier) {
		var supplier = supplierRepository.findById(idSupplier).orElseThrow(() -> new NullPointerException(SUPPLIER_NOT_FOUND));
		return (List<Invoice>) supplier.getInvoices();
	}

	@Override
	public void assignOperatorToInvoice(Long idOperator, Long idInvoice) {
		var invoice = invoiceRepository.findById(idInvoice).orElseThrow(() -> new NullPointerException(INVOICE_NOT_FOUND));
		var operator = operatorRepository.findById(idOperator).orElseThrow(() -> new NullPointerException(OPERATOR_NOT_FOUND));
		operator.getInvoices().add(invoice);
		operatorRepository.save(operator);
	}

	@Override
	public float getTotalAmountInvoiceBetweenDates(Date startDate, Date endDate) {
		return invoiceRepository.getTotalAmountInvoiceBetweenDates(startDate, endDate);
	}


}