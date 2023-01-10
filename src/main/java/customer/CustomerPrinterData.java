package customer;

import java.util.List;

public class CustomerPrinterData {
    private final CustomerDTOSize size;
    private final List<CustomerDTO> dto;

    public CustomerPrinterData(CustomerDTOSize size, List<CustomerDTO> dto) {
        this.size = size;
        this.dto = dto;
    }

    public CustomerDTOSize getSize() {
        return size;
    }

    public List<CustomerDTO> getDto() {
        return dto;
    }
}
