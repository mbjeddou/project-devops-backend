package tn.esprit.devops.project.controllers;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.devops.project.entities.Stock;
import tn.esprit.devops.project.services.Iservices.IStockService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class StockController {

    IStockService stockService;

    @PostMapping("/stock")
   public Stock addStock(@RequestBody Stock stock){
        return stockService.addStock(stock);
    }

    @GetMapping("/stock/{id}")
   public  Stock retrieveStock(@PathVariable Long id){
        return stockService.retrieveStock(id);
    }

    @GetMapping("/stock")
   public List<Stock> retrieveAllStock(){
        return stockService.retrieveAllStock();
    }


}
