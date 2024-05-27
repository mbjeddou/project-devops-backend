import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.devops.project.controllers.StockController;
import tn.esprit.devops.project.entities.Stock;
import tn.esprit.devops.project.services.Iservices.IStockService;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)

public class StockControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IStockService stockService;

    @InjectMocks
    private StockController stockController;

    @Captor
    ArgumentCaptor<Stock> stockCaptor;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(stockController).build();
    }

    @Test
    void testAddStock() throws Exception {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setTitle("New Stock");

        when(stockService.addStock(any(Stock.class))).thenReturn(stock);

        mockMvc.perform(post("/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Stock\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idStock").value(1L))
                .andExpect(jsonPath("$.title").value("New Stock"));

        verify(stockService, times(1)).addStock(stockCaptor.capture());
        Stock capturedStock = stockCaptor.getValue();
        assertEquals("New Stock", capturedStock.getTitle());
    }

    @Test
    void testAddStockValidation() throws Exception {
        mockMvc.perform(post("/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"\"}"))  // Empty title for validation test
                .andExpect(status().isBadRequest());

        verify(stockService, times(0)).addStock(any(Stock.class));
    }

    @Test
    void testRetrieveStock() throws Exception {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        stock.setTitle("Existing Stock");

        when(stockService.retrieveStock(1L)).thenReturn(stock);

        mockMvc.perform(get("/stock/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idStock").value(1L))
                .andExpect(jsonPath("$.title").value("Existing Stock"));

        verify(stockService, times(1)).retrieveStock(1L);
    }

    @Test
    void testRetrieveStockNotFound() throws Exception {
        when(stockService.retrieveStock(1L)).thenThrow(new NoSuchElementException("Stock not found"));

        mockMvc.perform(get("/stock/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Stock not found"));

        verify(stockService, times(1)).retrieveStock(1L);
    }

    @Test
    void testRetrieveAllStock() throws Exception {
        Stock stock1 = new Stock();
        stock1.setIdStock(1L);
        stock1.setTitle("Stock 1");

        Stock stock2 = new Stock();
        stock2.setIdStock(2L);
        stock2.setTitle("Stock 2");

        List<Stock> stockList = Arrays.asList(stock1, stock2);

        when(stockService.retrieveAllStock()).thenReturn(stockList);

        mockMvc.perform(get("/stock"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idStock").value(1L))
                .andExpect(jsonPath("$[0].title").value("Stock 1"))
                .andExpect(jsonPath("$[1].idStock").value(2L))
                .andExpect(jsonPath("$[1].title").value("Stock 2"));

        verify(stockService, times(1)).retrieveAllStock();
    }
}


