
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import tn.esprit.devops.project.entities.Stock;
import tn.esprit.devops.project.repositories.StockRepository;
import tn.esprit.devops.project.services.StockServiceImpl;
import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
 class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;
    @Mock
    private Logger logger;

    @InjectMocks
    private StockServiceImpl stockService;


    private Stock stock;


    @Test
    void testDeleteStock() {
        Stock existingStock = new Stock();
        existingStock.setIdStock(1L);
        existingStock.setTitle("Existing Stock");

        when(stockRepository.findById(1L)).thenReturn(Optional.of(existingStock));
        doNothing().when(stockRepository).delete(existingStock); // Mock delete method to do nothing

        stockService.deleteStock(1L);

        verify(stockRepository).findById(1L);
        verify(stockRepository).delete(existingStock);
    }


    @BeforeEach
    void setUp() {
        stock = new Stock();
        stock.setIdStock(1L);
        stock.setTitle("Test Stock");
        stock.setRealStock(50);
        stock.setReserveStock(30);
    }

    @Test
    void testCheckStockLevels_RealStockApproachesReserveStock() {
        stock.setRealStock(30);

        when(stockRepository.findById(1L)).thenReturn(java.util.Optional.of(stock));

        stockService.checkStockLevels(1L);

        verify(stockRepository, times(1)).findById(1L);
        stockService.retrieveStock(1L);
    }

    @Test
    void testCheckStockLevels_ReserveStockLow() {
        stock.setReserveStock(5);

        when(stockRepository.findById(1L)).thenReturn(java.util.Optional.of(stock));

        stockService.checkStockLevels(1L);

        verify(stockRepository, times(1)).findById(1L);
        stockService.retrieveStock(1L);
    }


}




