# java-convenience-store-precourse

## 구현할 기능 목록

1. 상품 목록과 행사 목록을 파일 입출력을 통해 불러온다.
    - 상품 목록
        - 형식: `name, price, quantity, promotion`
        - 경로: `src/main/resources/products.md`
    - 행사 목록
        - 형식: `name, buy, get, start_date, end_date`
        - 경로: `src/main/resources/promotions.md`
2. 환영 인사와 함께 `상품명`, `가격`, `프로모션 이름`, `재고`를 안내한다.
    - 만약 재고가 `0개`라면 `재고 없음`을 출력한다.
    - 출력값 예시:

```
안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 10개 탄산2+1
- 콜라 1,000원 10개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 5개
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개
```

3. 구매할 상품과 수량을 입력 받는 메세지를 출력하고 값을 입력 받는다.
    - 상품명, 수량은 `하이픈(-)`, 개별 상품은 `대괄호([])`로 묶어 `쉼표(,)`로 구분한다.
    - 출력 메세지: `구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])`
    - 입력값 예시: `[콜라-10],[사이다-3]`
4. 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 혜택에 대한 안내 메세지를 출력하고, 그 수량만큼 추가 여부를 입력 받는다.
    - 출력 메세지: `현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)`
    - Y: 증정 받을 수 있는 상품을 추가한다.
    - N: 증정 받을 수 있는 상품을 추가하지 않는다.
    - 입력값 예시: `Y`
5. 프로모션 재고가 부족하여 일부 수량을 포로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지 여부에 대한 안내 메세지를 출력하고, 입력 받는다.
    - 출력 메세지: `현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)`
    - Y: 일부 수량에 대해 정가로 결제한다.
    - N: 정가로 결제해야하는 수량만큼 제외한 후 결제를 진행한다.
    - 입력값 예시: `Y`
6. 멤버십 할인 적용 여부를 확인하기 위해 안내 문구를 출력하고, 입력 받는다.
    - 출력 메세지: `멤버십 할인을 받으시겠습니까? (Y/N)`
    - Y: 멤버십 할인을 적용한다.
    - N: 멤버십 할인을 적용하지 않는다.
    - 입력값 예시: `Y`
7. 구매 상품 내역, 증정 상품 내역, 금액 정보를 출력한다.
    - 예시:

```
===========W 편의점=============
상품명		수량	금액
콜라		3 	3,000
에너지바 		5 	10,000
===========증	정=============
콜라		1
==============================
총구매액		8	13,000
행사할인			-1,000
멤버십할인			-3,000
내실돈			 9,000
```

8. 추가 구매 여부를 확인하기 위해 안내 문구를 출력하고, 입력 받는다.
    - 출력 메세지: `감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)`
    - Y: 재고가 업데이트된 상품 목록을 확인 후 추가로 구매를 진행한다.
    - N: 구매를 종료한다.
    - 입력값 예시: `Y`

---

## 예외 처리

> 사용자가 잘못된 값을 입력할 경우 `IllegalArgumentException`를 발생시킨다.

- 사용자가 잘못된 값을 입력했을 때, `[ERROR]`로 시작하는 오류 메시지와 함께 상황에 맞는 안내를 출력한다.
    - 구매할 상품과 수량 형식이 올바르지 않은 경우: `[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.`
    - 존재하지 않는 상품을 입력한 경우: `[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.`
    - 구매 수량이 재고 수량을 초과한 경우: `[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.`
    - 기타 잘못된 입력의 경우: `[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.`

---

## 실행 결과

```
안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 10개 탄산2+1
- 콜라 1,000원 10개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 5개
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개

구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
[콜라-3],[에너지바-5]

멤버십 할인을 받으시겠습니까? (Y/N)
Y 

===========W 편의점=============
상품명		수량	금액
콜라		3 	3,000
에너지바 		5 	10,000
===========증	정=============
콜라		1
==============================
총구매액		8	13,000
행사할인			-1,000
멤버십할인			-3,000
내실돈			 9,000

감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
Y

안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 7개 탄산2+1
- 콜라 1,000원 10개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 재고 없음
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개

구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
[콜라-10]

현재 콜라 4개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)
Y

멤버십 할인을 받으시겠습니까? (Y/N)
N

===========W 편의점=============
상품명		수량	금액
콜라		10 	10,000
===========증	정=============
콜라		2
==============================
총구매액		10	10,000
행사할인			-2,000
멤버십할인			-0
내실돈			 8,000

감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
Y

안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 재고 없음 탄산2+1
- 콜라 1,000원 7개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 재고 없음
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개

구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
[오렌지주스-1]

현재 오렌지주스은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)
Y

멤버십 할인을 받으시겠습니까? (Y/N)
Y

===========W 편의점=============
상품명		수량	금액
오렌지주스		2 	3,600
===========증	정=============
오렌지주스		1
==============================
총구매액		2	3,600
행사할인			-1,800
멤버십할인			-0
내실돈			 1,800

감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
N
```

---

## 프로젝트 구조

```
├── main
│   ├── java
│   │   └── store
│   │       ├── Application.java
│   │       ├── controller
│   │       │   └── StoreController.java
│   │       ├── domain
│   │       │   ├── Cart.java
│   │       │   ├── Order.java
│   │       │   ├── Product.java
│   │       │   ├── Products.java
│   │       │   ├── Promotion.java
│   │       │   ├── Promotions.java
│   │       │   └── Receipt.java
│   │       ├── dto
│   │       │   └── ProductPurchaseDto.java
│   │       ├── exception
│   │       │   ├── InvalidInputException.java
│   │       │   ├── InvalidPurchaseException.java
│   │       │   ├── NotFoundProductException.java
│   │       │   ├── OverProductQuantityException.java
│   │       │   └── constants
│   │       │       └── ErrorConstants.java
│   │       ├── service
│   │       │   └── ProductService.java
│   │       ├── util
│   │       │   ├── FileLoader.java
│   │       │   ├── NumberFormatter.java
│   │       │   ├── StringParser.java
│   │       │   └── StringSplitter.java
│   │       └── view
│   │           ├── InputView.java
│   │           └── OutputView.java
│   └── resources
│       ├── products.md
│       └── promotions.md
└── test
    └── java
        └── store
            ├── ApplicationTest.java
            ├── domain
            │   ├── CartTest.java
            │   ├── OrderTest.java
            │   ├── ProductTest.java
            │   ├── ProductsTest.java
            │   ├── PromotionTest.java
            │   └── PromotionsTest.java
            ├── service
            │   └── ProductServiceTest.java
            └── util
                ├── FileLoaderTest.java
                ├── NumberFormatterTest.java
                ├── StringParserTest.java
                └── StringSplitterTest.java

```

---

## 테스트 커버리지

![test_coverage.png](docs/test_coverage.png)