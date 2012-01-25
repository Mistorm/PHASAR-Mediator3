package nl.ru.cs.phasar.mediator.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.ru.cs.phasar.mediator.documentsource.DocumentSource;
import nl.ru.cs.phasar.mediator.documentsource.Hit;
import nl.ru.cs.phasar.mediator.documentsource.ServerQueryCache;
import nl.ru.cs.phasar.mediator.documentsource.flatfile.FlatSource;
import nl.ru.cs.phasar.mediator.userquery.Metadata;
import nl.ru.cs.phasar.mediator.userquery.Query;
import nl.ru.cs.phasar.mediator.userquery.UserQueryCache;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public abstract class AbstractSuggestionResource {

    private DocumentSource documentSource;
    private UserQueryCache cache;

    public AbstractSuggestionResource() {
        documentSource = new ServerQueryCache(new FlatSource());
        //documentSource =  new ServerQueryCache(new ServerQueryResolver());
        cache = new UserQueryCache(documentSource);
    }

    protected Query getResult(String json){
    
        Date year = new Date();
        String matchingType = "";
        String browsingType = "sentence";

        Metadata metadata = new Metadata(year, matchingType, browsingType);
    
        Query query = cache.getQuery(metadata, json);
        
        return query;
        
    }
    
//    protected List<Hit> getNewHits(String json) throws JSONException {
//
//        JSONObject jsonObject = new JSONObject(json);
//        JSONObject baseJson = jsonObject.optJSONObject("baseQuery");
//        JSONObject extendedJson = jsonObject.optJSONObject("extendedQuery");
//
//        Date year = new Date();
//        String matchingType = "";
//        String browsingType = "sentence";
//
//        Metadata metadata = new Metadata(year, matchingType, browsingType);
//
//        //System.out.println(baseQuery.optJSONArray("boxes").toString());
//        Query baseQuery = cache.getQuery(metadata, baseJson.toString());
//        Query extendedQuery = cache.getQuery(metadata, extendedJson.toString());
//
//        System.out.println("baseQuery result: " + baseQuery.getJSONResult());
//        System.out.println("extendedQuery result: " + extendedQuery.getJSONResult());
//
//        List<Hit> baseHits = baseQuery.getHitlist();
//        List<Hit> extendedHits = extendedQuery.getHitlist();
//        List<Hit> newHits = new ArrayList<Hit>();
//
//        for (Hit h : extendedHits) {
//            if (!baseHits.contains(h)) {
//                newHits.add(h);
//            }
//        }
//
//        System.out.println("New hits: " + newHits.size());
//
//       return newHits;
//    }
}
