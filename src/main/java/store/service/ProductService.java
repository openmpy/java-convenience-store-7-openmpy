package store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import store.dto.ProductPurchaseDto;
import store.util.StringSplitter;

public class ProductService {

    private static final String SQUARE_BRACKETS_REGEX = "^\\[([^]-]+)-([^]]+)]$";

    public List<ProductPurchaseDto> addProductPurchase(final String info) {
        final List<ProductPurchaseDto> productPurchaseDtos = new ArrayList<>();
        final Pattern pattern = Pattern.compile(SQUARE_BRACKETS_REGEX);

        StringSplitter.splitComma(info).forEach(it -> {
            final Matcher matcher = pattern.matcher(it);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.");
            }

            final String productName = matcher.group(1).strip();
            final int purchaseQuantity = Integer.parseInt(matcher.group(2).strip());

            final ProductPurchaseDto productPurchaseDto = new ProductPurchaseDto(productName, purchaseQuantity);
            productPurchaseDtos.add(productPurchaseDto);
        });
        return productPurchaseDtos;
    }
}
