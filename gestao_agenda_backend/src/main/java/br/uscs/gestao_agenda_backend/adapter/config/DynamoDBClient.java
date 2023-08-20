//package br.uscs.gestao_agenda_backend.adapter.config;
//
//import com.amazonaws.services.dynamodbv2.model.AttributeValue;
//import com.amazonaws.services.dynamodbv2.model.ScanRequest;
//
//import java.util.Map;
//import java.util.Set;
//
//public class DynamoDBClient {
//
//    public static void scanItems( DynamoDbClient ddb,String tableName ) {
//
//        try {
//            ScanRequest scanRequest = ScanRequest.builder()
//                    .tableName(tableName)
//                    .build();
//
//            ScanResponse response = ddb.scan(scanRequest);
//            for (Map<String, AttributeValue> item : response.items()) {
//                Set<String> keys = item.keySet();
//                for (String key : keys) {
//                    System.out.println ("The key name is "+key +"\n" );
//                    System.out.println("The value is "+item.get(key).s());
//                }
//            }
//
//        } catch (DynamoDbException e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
//    }
//
//
//}
