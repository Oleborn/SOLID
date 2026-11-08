package devforge.ocp.delivery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DeliveryMethodTest {

    @Test
    void holdsThreeFields() {
        DeliveryMethod method = new DeliveryMethod();
        method.setCode("COURIER");
        method.setName("Courier");
        method.setDeliveryDays(2);

        assertEquals("COURIER", method.getCode());
        assertEquals("Courier", method.getName());
        assertEquals(2, method.getDeliveryDays());
    }
}