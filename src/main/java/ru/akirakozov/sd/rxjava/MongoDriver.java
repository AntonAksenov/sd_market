package ru.akirakozov.sd.rxjava;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.MongoCollection;
import com.mongodb.rx.client.MongoDatabase;
import org.bson.codecs.pojo.PojoCodecProvider;
import rx.Observable;

import static com.mongodb.rx.client.MongoClients.getDefaultCodecRegistry;

/**
 * @author akirakozov
 */
public class MongoDriver {
    private final MongoClient client = createMongoClient();
    private final org.bson.codecs.configuration.CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(getDefaultCodecRegistry(), org.bson.codecs.configuration.CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    private final MongoDatabase database = client.getDatabase("rxtest").withCodecRegistry(pojoCodecRegistry);
    private final MongoCollection<Account> accounts = database.getCollection("account").withDocumentClass(Account.class);
    private final MongoCollection<Product> products = database.getCollection("product").withDocumentClass(Product.class);


    public void addAccount(Account account) {
        accounts.insertOne(account);
    }

    public Observable<Account> getAccount(long id) {
        return accounts.find(Filters.eq("id", id)).toObservable();
    }

    public void addProduct(Product product) {
        products.insertOne(product);
    }

    public Observable<Product> getProducts() {
        return products.find().toObservable();
    }

    private MongoClient createMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }
}

