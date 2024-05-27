package tn.esprit.devops.project.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.devops.project.entities.Stock;
import tn.esprit.devops.project.services.Iservices.IStockService;
import tn.esprit.devops.project.repositories.StockRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class StockServiceImpl implements IStockService {

   private final StockRepository stockRepository;

    @Override
    public Stock addStock(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public Stock retrieveStock(Long id) {
        return stockRepository.findById(id).orElseThrow(() -> new NullPointerException("Stock not found"));
    }

    @Override
    public List<Stock> retrieveAllStock() {
        return stockRepository.findAll();
    }

    @Override
    public void deleteStock(Long id) {
        Stock existingStock = stockRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Stock not found"));

        stockRepository.delete(existingStock);
    }


}
