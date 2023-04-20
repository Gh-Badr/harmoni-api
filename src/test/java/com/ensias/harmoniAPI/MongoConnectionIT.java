package com.ensias.harmoniAPI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.bson.Document;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



@SpringBootTest
public class MongoConnectionIT {

	@Autowired
    private MongoTemplate mongoTemplate;


	@Test
	public void AssertInsertSucceeds() {
		String name = "A";
		Document doc = new Document("name", name);
		
        // Insert document into MongoDB
        mongoTemplate.insert(doc,"items");
		
     // Query data
        Document result = mongoTemplate.findOne(
                new Query(Criteria.where("name").is("A")),
                Document.class, "items");
		
		
        System.out.println(result);

     // Check result
        assertNotNull(result);
        assertNotNull(result.get("_id"));
        assertEquals(result.getString("name"), "A");
	}
	


}
