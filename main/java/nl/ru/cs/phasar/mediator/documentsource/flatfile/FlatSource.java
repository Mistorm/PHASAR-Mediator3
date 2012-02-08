package nl.ru.cs.phasar.mediator.documentsource.flatfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.ru.cs.phasar.mediator.documentsource.DocumentSource;
import nl.ru.cs.phasar.mediator.documentsource.Hit;
import nl.ru.cs.phasar.mediator.documentsource.Result;
import nl.ru.cs.phasar.mediator.documentsource.Triple;
import nl.ru.cs.phasar.mediator.userquery.Metadata;

/**
 *
 * @author bartvz <bvanzeeland at gmail dot com>
 */
public class FlatSource implements DocumentSource {

    private static final String FILEPATH = "/home/mistorm/workspaces/netbeans/Mediator3/src/main/resources/examplesents.txt";
    //private static final String FILEPATH = "/home/mistorm/workspaces/netbeans/Mediator3/src/main/resources/nrcsents.txt";
    private List<Result> resultList;
    private char newChar;

    public FlatSource() {

        this.resultList = new ArrayList<Result>();

        try {
            parseFile(FILEPATH);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(FlatSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void parseFile(String filePath) throws FileNotFoundException {

        String line = null;
        Result currentResult = null;
        String[] currentTriple = null;

        File file = new File(filePath);

        Scanner scan = new Scanner(file);

        while (scan.hasNext()) {
            line = scan.nextLine();
            //If a line starts with a [, it contains a ground triple.
            if (line.startsWith("[")) {
                currentTriple = line.split(",");

                //TODO: This is.. ugly. Should be easier?
                for (int i = 0; i < currentTriple.length; i++) {
                    currentTriple[i] = currentTriple[i].replace('[', newChar);
                    currentTriple[i] = currentTriple[i].replace(']', newChar);
                    currentTriple[i] = currentTriple[i].trim();
                }

                currentResult.addTriple(new Triple(currentTriple));
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
