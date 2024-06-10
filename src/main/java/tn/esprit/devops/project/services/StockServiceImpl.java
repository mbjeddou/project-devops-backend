package tn.esprit.devops.project.services;

import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import tn.esprit.devops.project.entities.Product;
import tn.esprit.devops.project.entities.Stock;
import tn.esprit.devops.project.services.Iservices.IStockService;
import tn.esprit.devops.project.repositories.StockRepository;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@AllArgsConstructor
public class StockServiceImpl implements IStockService {

    private final StockRepository stockRepository;
    private static  Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);



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

    @Override
    public void checkStockLevels(Long idStock) {
            Stock stock = retrieveStock(idStock);
            if (stock.getRealStock() <= stock.getReserveStock()) {
                logger.warn("Alerte: Le stock réel s'approche du stock de réserve!");
            }
            if (stock.getReserveStock() < 10) { // Par exemple, si le stock de réserve est inférieur à 10
                logger.warn("Alerte: Le stock de réserve diminue!");
            }
    }

    @Override
    public Product getMostUsedProduct(Long stockId) {
        Optional<Stock> optionalStock = stockRepository.findById(stockId);
        if (optionalStock.isPresent()) {
            Stock stock = optionalStock.get();
            return stock.getProducts().stream()
                    .max(Comparator.comparingInt(Product::getQuantity))
                    .orElse(null);
        } else {
            logger.warn("Stock with ID {} not found", stockId);
            return null;
        }
    }


}
