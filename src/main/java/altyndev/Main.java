package altyndev;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {

        CrptApi api = new CrptApi(TimeUnit.SECONDS, 5);

        CrptApi.Document document = createTestDocument();

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 20; i++) {
            final int taskId = i;
            executorService.submit(() -> {
                try {
                    System.out.println("Task " + taskId + " started");
                    api.createDocument(document, "test_signature");
                    System.out.println("Task " + taskId + " completed");
                } catch (Exception e) {
                    System.err.println("Task " + taskId + " failed: " + e.getMessage());
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            System.err.println("Execution interrupted: " + e.getMessage());
        }

        System.out.println("Test completed");
    }

    private static CrptApi.Document createTestDocument() {
        CrptApi.Document document = new CrptApi.Document();
        document.description = new CrptApi.Description();
        document.description.participantInn = "1234567890";
        document.docId = "test_doc_id";
        document.docStatus = "test_status";
        document.docType = "LP_INTRODUCE_GOODS";
        document.importRequest = true;
        document.ownerInn = "0987654321";
        document.participantInn = "1234567890";
        document.producerInn = "5678901234";
        document.productionDate = "2023-01-01";
        document.productionType = "test_type";

        CrptApi.Product product = new CrptApi.Product();
        product.certificateDocument = "cert_doc";
        product.certificateDocumentDate = "2023-01-01";
        product.certificateDocumentNumber = "cert_num";
        product.ownerInn = "0987654321";
        product.producerInn = "5678901234";
        product.productionDate = "2023-01-01";
        product.tnvedCode = "test_code";
        product.uitCode = "test_uit";
        product.uituCode = "test_uitu";

        document.products = new ArrayList<>();
        document.products.add(product);

        document.regDate = "2023-01-01";
        document.regNumber = "test_reg_number";

        return document;
    }
}