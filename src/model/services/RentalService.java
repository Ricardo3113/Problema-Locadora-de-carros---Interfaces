package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {
	
	private Double pricePerDay;
	private Double pricePerHour;
	
	private TaxService taxService;
	
	public RentalService() {
	}

	public RentalService(Double pricePerDay, Double pricePerHour, TaxService taxService) {
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		long t1 = carRental.getStart().getTime();
		long t2 = carRental.getFinish().getTime();
		double hours = (double)(t2 - t1) / 1000 / 60 / 60;  
		
		double basicPayment;
		if (hours <= 12.0) {
			basicPayment = Math.ceil(hours) * pricePerHour;//fun��o math.ceil para arredondamento
		}
		else {
			basicPayment = Math.ceil(hours / 24.0) * pricePerDay;			
		}
		
		double tax = taxService.tax(basicPayment);
		
		carRental.setInvoice(new Invoice(basicPayment, tax));		
	}
}
