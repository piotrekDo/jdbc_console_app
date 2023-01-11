package customer;

import java.util.List;

public class CustomerPrinterPage {
    private final CustomerDTOSize size;
    private final List<CustomerDTO> dto;
    private final int offset;
    private final int elements;

    public CustomerPrinterPage(CustomerDTOSize size, List<CustomerDTO> dto, int offset, int elements) {
        this.size = size;
        this.dto = dto;
        this.offset = offset;
        this.elements = elements;
    }

    public int getOffset() {
        return offset;
    }

    public int getElements() {
        return elements;
    }

    public CustomerDTOSize getSize() {
        return size;
    }

    public List<CustomerDTO> getDto() {
        return dto;
    }
}
