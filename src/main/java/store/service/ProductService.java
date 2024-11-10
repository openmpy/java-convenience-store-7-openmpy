package store.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.dto.ProductPurchaseDto;
import store.exception.InvalidInputException;
import store.util.StringSplitter;

public class ProductService {

    private static final String SQUARE_BRACKETS_REGEX = "^\\[([^]-]+)-([^]]+)]$";

    public List<ProductPurchaseDto> addProductPurchase(final String info) {
        return StringSplitter.splitComma(info).stream()
                .map(this::extractProductPurchase)
                .toList();
    }

    private ProductPurchaseDto extractProductPurchase(final String input) {
        final Matcher matcher = Pattern.compile(SQUARE_BRACKETS_REGEX).matcher(input);
        validateInput(matcher);

        final String productName = matcher.group(1).strip();
        final int purchaseQuantity = Integer.parseInt(matcher.group(2).strip());
        return new ProductPurchaseDto(productName, purchaseQuantity);
    }

    private void validateInput(final Matcher matcher) {
        if (!matcher.matches()) {
            throw new InvalidInputException();
        }
    }
}
