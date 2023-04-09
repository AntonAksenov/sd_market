package ru.akirakozov.sd.rxjava;

import io.reactivex.netty.protocol.http.server.HttpServer;
import rx.Observable;

import java.util.List;
import java.util.Map;

/**
 * @author akirakozov
 */
public class Server {

    public static void main(final String[] args) {
        MongoDriver mDb = new MongoDriver();
        HttpServer
                .newServer(8080)
                .start((req, resp) -> {
                    try {
                        Map<String, List<String>> parameters = req.getQueryParameters();
                        switch (req.getDecodedPath()) {
                            case ("/addAccount") -> {
                                mDb.addAccount(new Account(Long.parseLong(parameters.get("id").get(0)), Currency.valueOf(parameters.get("currency").get(0))));
                                return resp.writeString(Observable.just("Success"));
                            }
                            case ("/addProduct") -> {
                                mDb.addProduct(new Product(parameters.get("name").get(0), Double.parseDouble(parameters.get("price").get(0))));
                                return resp.writeString(Observable.just("Success"));
                            }
                            case ("/getProducts") -> {
                                return resp.writeString(
                                        mDb.getAccount(Long.parseLong((parameters.get("id").get(0))))
                                                .flatMap(account -> {
                                                    System.out.println("!!!");
                                                    return mDb.getProducts()
                                                            .map(product -> product.getName() + " " + product.price(account.getCurrency()));
                                                })
                                );
                            }
                        }
                        throw new Error("not implemented");
                    } catch (Exception e) {
                        return resp.writeString(Observable.just("Error: " + e.getMessage()));
                    }
                })
                .awaitShutdown();
    }
}
