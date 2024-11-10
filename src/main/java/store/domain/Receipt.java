package store.domain;

import store.view.OutputView;

public class Receipt {

    private final Order order;

    public Receipt(final Order order) {
        this.order = order;
    }

    public void printResult() {
        OutputView.printStoreReceiptHeader();
        OutputView.printProductPurchaseInfo(order);
        OutputView.printBonusProductInfo(order);
        OutputView.printTotalCalculate(order);
    }
}
