
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops.project.entities.Stock;
import tn.esprit.devops.project.repositories.StockRepository;
import tn.esprit.devops.project.services.StockServiceImpl;
import java.util.Optional;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private StockServiceImpl stockService;

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


}


