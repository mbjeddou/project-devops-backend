
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import tn.esprit.devops.project.entities.Product;
import tn.esprit.devops.project.entities.Stock;
import tn.esprit.devops.project.repositories.StockRepository;
import tn.esprit.devops.project.services.StockServiceImpl;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
 class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;
    @Mock
    private Logger logger;

    @Captor
    private ArgumentCaptor<String> stringCaptor;
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

    @Test
    void testGetMostUsedProduct() {
        Product product1 = new Product();
        product1.setIdProduct(1L);
        product1.setTitle("Product 1");
        product1.setQuantity(550); // quantity = 50

        Product product2 = new Product();
        product2.setIdProduct(2L);
        product2.setTitle("Product 2");
        product2.setQuantity(200); // quantity = 100

        stock.setProducts(Set.of(product1, product2));

        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        Product mostUsedProduct = stockService.getMostUsedProduct(1L);

        assertEquals(product1, mostUsedProduct);
        verify(stockRepository, times(1)).findById(1L);
    }

    @Test
    void testGetMostUsedProduct_NoProducts() {
        stock.setProducts(Collections.emptySet());

        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        Product mostUsedProduct = stockService.getMostUsedProduct(1L);

        assertNull(mostUsedProduct);
        verify(stockRepository, times(1)).findById(1L);
    }


    @Test
    public void testGetMostUsedProduct_StockNotFound() {
        when(stockRepository.findById(1L)).thenReturn(Optional.empty());

        Product mostUsedProduct = stockService.getMostUsedProduct(1L);

        assertNull(mostUsedProduct);
    }
    }









