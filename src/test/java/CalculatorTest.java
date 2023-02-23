import com.rest.app.Calculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    Calculator calculator;

     @Test
    public void testMultiply() {
         calculator = new Calculator();
         assertEquals( 20, calculator.multiply(4,5));
         assertEquals(45, calculator.multiply(5,9));
     }
    @Test
    public void testDivide() {
        calculator = new Calculator();
        assertEquals(2, calculator.divide(4, 2));
        assertEquals(3, calculator.divide(9,3));
    }
}
