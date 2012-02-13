package nl.ru.cs.phasar.mediator.documentsource.flatfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.ru.cs.phasar.mediator.documentsource.DocumentSource;
import nl.ru.cs.phasar.mediator.documentsource.Result;
import nl.ru.cs.phasar.mediator.documentsource.Triple;
import nl.ru.cs.phasar.mediator.resource.MediatorResource;
import nl.ru.cs.phasar.mediator.userquery.Metadata;

/**
 * FlatSource parses a plain text file with sents and triples. Slow but allows 
 * the use of the PHASAR system without the server.
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class FlatSource implements DocumentSource {

    private List<Result> resultList;
    private static String PROPERTIES_FILE = "mediator.properties";

    public FlatSource() {

        URL url = MediatorResource.class.getProtectionDomain().getCodeSource().getLocation();

        Properties configFile = new Properties();
        try {
            configFile.load(new FileReader(url.getPath() + PROPERTIES_FILE));
        }
        catch (IOException ex) {
            Logger.getLogger(MediatorResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.resultList = new ArrayList<Result>();

        try {
            parseFile(configFile.getProperty("INTERNAL_DOCSOURCE_FILE"));
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(FlatSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void parseFile(String filePath) throws FileNotFoundException {

        String line = null;
        Result currentResult = null;
        String[] currentTriple = null;
        String[] words;
        String relator;

        File file = new File(filePath);

        Scanner scan = new Scanner(file);

        while (scan.hasNext()) {
            line = scan.nextLine();
            //If a line starts with a [, it contains a ground triple.
            if (line.startsWith("[")) {

                line = line.replace('[', ' ');
                line = line.replace(']', ' ');
                line.trim();

                words = line.split(",[A-Z]+[^,]*,");

                Matcher matcher = Pattern.compile(",[A-Z]+[^,]*,").matcher(line);
                if (( matcher.find() ) && ( words.length == 2 )) {
                    relator = matcher.group();

                    currentTriple = new String[3];

                    currentTriple[0] = words[0];
                    currentTriple[1] = relator;
                    currentTriple[2] = words[1];

                    currentResult.addTriple(new Triple(currentTriple));
                } else {
                    relator = null;
                }
            } //No [, so it must be a sentece.
            else {
                if (currentResult != null) {
                    resultList.add(currentResult);
                }
                currentResult = new Result(line);
            }
            line = null;
        }
    }

    @Override
    public List<Result> getDocuments(Metadata metadata, List<Triple> triples) {

        List<Result> resultList = new ArrayList<Result>();

        //Searching without the triple(s) to find is imposible
        if (triples.isEmpty()) {
            return resultList;
        }

        boolean match = true;

        for (Result r : this.resultList) {
            for (Triple t : triples) {
                if (r.matchTriple(t) == false) {
                    match = false;
                }
            }
            if (match == true) {
                resultList.add(r);
            }
            match = true;
        }

        System.out.println("Nr. of sents found: " + resultList.size());

        return resultList;
    }
}
