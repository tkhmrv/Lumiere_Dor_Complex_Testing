package org.lumiere_d_or;

class CommonTest {
    public static void main(String[] args) {
        // https://lumiere-d-or.tilda.ws/catalog/tproduct/624663382-353699490121-matheny
        // https://lumiere-d-or.tilda.ws/catalog/tproduct/624663382-798782139041-bronx
        // https://lumiere-d-or.tilda.ws/catalog/tproduct/624687507-351690719011-disc

        System.out.println("Unit testing of Lumière D'Or website:");

        // Получение URL у тестировщика
        String[] urls = new String[3];
        for (int i = 0; i < urls.length; i++) {
            urls[i] = MainMethods.getUrl(i);
        }

        System.out.println();

        // TEST 1 - main_page_to_catalog
        MainPageToCatalogTest mainPageToCatalogTest = new MainPageToCatalogTest();
        mainPageToCatalogTest.setup();
        mainPageToCatalogTest.MainPageToCatalogLogic();
        mainPageToCatalogTest.tearDown();

        // TEST 2 - elements_on_display
        DisplayElementTest displayElementTest = new DisplayElementTest();
        displayElementTest.setup();
        displayElementTest.pageSetup(urls[0]);
        displayElementTest.productPageDisplaysCorrectly();
        displayElementTest.tearDown();

        // TEST 3 - add_to_checkout
        AddToCheckoutTest addToCheckoutTest = new AddToCheckoutTest();
        addToCheckoutTest.setup();
        addToCheckoutTest.pageSetup(urls[1]);
        addToCheckoutTest.addToCheckoutLogic();
        addToCheckoutTest.tearDown();

        // TEST 4 - order_registration_and_checkout
        OrderRegistrationTest orderRegistrationTest = new OrderRegistrationTest();
        orderRegistrationTest.setup();
        orderRegistrationTest.pageSetup(urls[2]);
        orderRegistrationTest.orderRegistrationLogic();
        orderRegistrationTest.tearDown();

        // TEST 5 - visit_social_media
        VisitSocialMediaTest visitSocialMediaTest = new VisitSocialMediaTest();
        visitSocialMediaTest.setup();
        visitSocialMediaTest.VisitSocialMediaLogic();
        visitSocialMediaTest.tearDown();
    }
}