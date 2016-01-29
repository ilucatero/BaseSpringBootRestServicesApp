package mongoConf;

import com.lucaterori.domains.Place;
import com.lucaterori.repositories.PlaceRepository;
import com.lucaterori.spring.cofig.mongo.DefaultMongoConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DefaultMongoConfig.class }, loader = SpringApplicationContextLoader.class)
public class MongoConfigTest {

    @Autowired
    PlaceRepository placeRepository;

    @Test
    public void test_basicOperations() throws Exception {
        placeRepository.deleteAll();
        // check if collection is empty
        assertEquals(0, placeRepository.count());
        // create new document
        Place foo = new Place();
        foo.setId(1L);
        foo.setName("Place 1");
        placeRepository.save(foo);
        // store new document
        placeRepository.save(foo);
        // check if document stored
        assertEquals(1, placeRepository.count());
        // check stored document
        assertEquals(foo, placeRepository.findOne(1L));
    }

}