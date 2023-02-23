import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rest.app.Book;
import com.rest.app.BookController;
import com.rest.app.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper= new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    Book RECORD_1 = new Book(1L,"Atomic Habits","how to build better","7");
    Book RECORD_2 = new Book(2L,"Sapiens","Brief history of mankind","8");
    Book RECORD_3 = new Book(2L,"History","Brief ","5");

    @Before
    public void setUp(){
        initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    public void getAllRecords_success() throws Exception{
        List<Book> records = new ArrayList<>(Arrays.asList(RECORD_1,RECORD_2,RECORD_3));
        Mockito.when(bookRepository.findAll()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect((ResultMatcher) jsonPath("$[2].name", is("History")))
                .andExpect((ResultMatcher) jsonPath("$[1]", is("Atomic Habits")));
    }

    @Test
    public void getBookId_success() throws Exception{
        Mockito.when(bookRepository.findById(RECORD_1.getBookId())).thenReturn(java.util.Optional.of(RECORD_1));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/book/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.name",is("Atomic Habits")));
    }

    @Test
    public void createRecord_success() throws Exception{

        Book record =Book.builder()
                .bookId(4L)
                .name("Introduction to c")
                .summary("The name but longer")
                .rating(String.valueOf(5))
                .build();

        Mockito.when(bookRepository.save(record)).thenReturn(record);

        String content = objectWriter.writeValueAsString(record);

        MockHttpServletRequestBuilder mockRequst = MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequst)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect((ResultMatcher) jsonPath("$.name", is("Introduction to C")));
    }

    @Test
    public void updateBookRecord_success() throws Exception{
        Book updatedRecord = Book.builder()
                .bookId(1L)
                .name("Updated Book Name")
                .summary("Updated Summary")
                .rating(String.valueOf(1)).build();

        Mockito.when(bookRepository.findById(RECORD_1.getBookId())).thenReturn(java.util.Optional.ofNullable(RECORD_1));
        Mockito.when(bookRepository.save(updatedRecord)).thenReturn(updatedRecord);

        String updatedContent = objectWriter.writeValueAsString(updatedRecord);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect((jsonPath("$", notNullValue())))
                .andExpect((ResultMatcher) jsonPath("$.name", is("Updated Book Name")));
    }

    @Test
    public void deleteBookById_success() throws Exception{
        Mockito.when(bookRepository.findById(RECORD_2.getBookId())).thenReturn((Optional.of(RECORD_2)));

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/book/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }
}
