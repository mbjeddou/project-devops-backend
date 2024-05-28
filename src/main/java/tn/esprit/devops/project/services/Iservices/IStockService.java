package tn.esprit.devops.project.services.Iservices;

import tn.esprit.devops.project.entities.Stock;

import java.util.List;

public interface IStockService {

    Stock addStock(Stock stock);
    Stock retrieveStock(Long id);
    List<Stock> retrieveAllStock();
    void deleteStock(Long id);
    void checkStockLevels(Long idStock);
}
